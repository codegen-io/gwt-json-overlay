package io.codegen.gwt.jsonoverlay.processor.builder;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleAnnotationValueVisitor8;
import javax.lang.model.util.SimpleTypeVisitor8;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import io.codegen.gwt.jsonoverlay.processor.ClassNames;
import io.codegen.gwt.jsonoverlay.processor.TypeMapper;
import io.codegen.gwt.jsonoverlay.processor.model.JavaType;
import io.codegen.gwt.jsonoverlay.processor.model.types.EnumType;
import io.codegen.gwt.jsonoverlay.processor.model.types.InheritedType;
import io.codegen.gwt.jsonoverlay.processor.model.types.ListType;
import io.codegen.gwt.jsonoverlay.processor.model.types.OptionalType;
import io.codegen.gwt.jsonoverlay.processor.model.types.OverlayType;
import io.codegen.gwt.jsonoverlay.processor.model.types.StringType;
import io.codegen.gwt.jsonoverlay.processor.model.types.SubType;

public class TypeResolver extends SimpleTypeVisitor8<JavaType, Void> {

    private final Consumer<TypeElement> consumer;

    public TypeResolver(Consumer<TypeElement> consumer) {
        this.consumer = consumer;
    }

    @Override
    protected JavaType defaultAction(TypeMirror e, Void p) {
        throw new IllegalStateException("Unable to handle type mirror " + e);
    }

    @Override
    public JavaType visitPrimitive(PrimitiveType primitive, Void p) {
        return io.codegen.gwt.jsonoverlay.processor.model.types.PrimitiveType.builder()
                .primitiveType(TypeName.get(primitive))
                .build();
    }

    @Override
    public JavaType visitDeclared(DeclaredType type, Void p) {
        ElementKind kind = type.asElement().getKind();

        if (ElementKind.ENUM.equals(kind)) {
            return EnumType.builder()
                    .enumType(type.asElement().accept(new ElementNameResolver(), null))
                    .build();
        }

        if (ElementKind.CLASS.equals(kind)) {
            ClassName name = type.asElement().accept(new ElementNameResolver(), null);
            if (ClassName.get(String.class).equals(name)) {
                return StringType.builder()
                        .stringType(name)
                        .build();
            }

            if (ClassName.get(Optional.class).equals(name)) {
                JavaType elementType = type.getTypeArguments().iterator().next().accept(new TypeResolver(consumer), null);
                return OptionalType.builder()
                        .elementType(elementType)
                        .build();
            }

            throw new IllegalArgumentException("Unknown class " + name);
        }

        if (ElementKind.INTERFACE.equals(kind)) {
            ClassName name = type.asElement().accept(new ElementNameResolver(), null);

            if (ClassName.get(List.class).equals(name)) {
                JavaType elementType = type.getTypeArguments().iterator().next().accept(new TypeResolver(consumer), null);
                return ListType.builder()
                        .elementType(elementType)
                        .build();
            }

            if (name.packageName().startsWith("java.") || name.packageName().startsWith("javax.")) {
                throw new IllegalStateException("Unable to handle java interface " + name);
            }

            Optional<String> property = type.asElement().getAnnotationMirrors().stream()
                .filter(mirror -> ClassNames.JACKSON_JSONTYPEINFO.equals(ClassName.get(mirror.getAnnotationType())))
                .flatMap(mirror -> mirror.getElementValues().entrySet().stream()
                        .filter(entry -> "property".equals(entry.getKey().getSimpleName().toString()))
                        .map(entry -> entry.getValue().getValue().toString()))
                .findFirst();

            if (property.isPresent()) {
                Map<String, JavaType> types = type.asElement().getAnnotationMirrors().stream()
                        .filter(mirror -> ClassNames.JACKSON_JSONSUBTYPES.equals(ClassName.get(mirror.getAnnotationType())))
                        .flatMap(mirror -> mirror.getElementValues().values().stream()
                                .flatMap(entry -> entry.accept(new JsonSubTypesVisitor(consumer), null).entrySet().stream()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

                String discriminatorMethodName = type.asElement().getEnclosedElements().stream()
                        .filter(element -> ElementKind.METHOD.equals(element.getKind()))
                        .map(TypeMapper::asExecutable)
                        .filter(method -> !method.isDefault())
                        .filter(method -> !TypeKind.VOID.equals(method.getReturnType().getKind()))
                        .filter(method -> property.get().equals(getPropertyName(method.getSimpleName().toString())))
                        .map(method -> method.getSimpleName().toString())
                        .findFirst()
                        .get();

                consumer.accept(TypeMapper.asType(type.asElement()));

                return InheritedType.builder()
                        .discriminatorMethodName(discriminatorMethodName)
                        .superType(name)
                        .putAllInheritedTypes(types)
                        .build();
            }

            consumer.accept(TypeMapper.asType(type.asElement()));

            Optional<ClassName> superInterface = TypeMapper.asType(type.asElement()).getInterfaces().stream()
                .filter(mirror -> hasSubTypeDefined(mirror, name))
                .findFirst()
                .map(mirror -> TypeMapper.asDeclaredType(mirror).asElement().accept(new ElementNameResolver(), null));

            if (superInterface.isPresent()) {
                return SubType.builder()
                        .superType(superInterface.get())
                        .subType(name)
                        .build();
            }

            return OverlayType.builder()
                    .overlayType(name)
                    .build();
        }

        throw new IllegalArgumentException("Unknown declared type " + kind + " " + type);
    }

    private boolean hasSubTypeDefined(TypeMirror mirror, ClassName name) {
        return TypeMapper.asDeclaredType(mirror).asElement().getAnnotationMirrors().stream()
                .filter(annotation -> ClassNames.JACKSON_JSONSUBTYPES.equals(ClassName.get(annotation.getAnnotationType())))
                .anyMatch(annotation -> annotation.getElementValues().values().stream()
                        .anyMatch(value -> value.accept(new SimpleAnnotationValueVisitor8<Boolean, Void>() {
                            @Override
                            public Boolean visitArray(List<? extends AnnotationValue> values, Void p) {
                                return values.stream()
                                        .anyMatch(value -> value.accept(new SimpleAnnotationValueVisitor8<Boolean, Void>() {
                                            public Boolean visitAnnotation(AnnotationMirror type, Void v) {
                                                if (ClassNames.JACKSON_JSONSUBTYPES_TYPE.equals(ClassName.get(type.getAnnotationType()))) {
                                                    return type.getElementValues().entrySet().stream()
                                                        .filter(entry -> "value".equals(entry.getKey().getSimpleName().toString()))
                                                        .anyMatch(entry -> entry.getValue().accept(new SimpleAnnotationValueVisitor8<Boolean, Void>() {
                                                            public Boolean visitType(TypeMirror type, Void v) {
                                                                return TypeName.get(type).equals(name);
                                                            }
                                                        }, null));
                                                }
                                                return Boolean.FALSE;
                                            }
                                        }, null));
                            }
                        }, null)));
    }

    private String getPropertyName(String methodName) {
        if (methodName.length() > 3 && methodName.startsWith("get")) {
            return NameConverter.convertUpperCamelCaseToLowerCamelCase(methodName.substring(3));
        } else if (methodName.length() > 2 && methodName.startsWith("is")) {
            return NameConverter.convertUpperCamelCaseToLowerCamelCase(methodName.substring(2));
        } else {
            return NameConverter.convertUpperCamelCaseToLowerCamelCase(methodName);
        }
    }

}

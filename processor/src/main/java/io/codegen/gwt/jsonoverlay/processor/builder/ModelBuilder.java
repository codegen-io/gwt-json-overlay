package io.codegen.gwt.jsonoverlay.processor.builder;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;

import com.squareup.javapoet.ClassName;

import io.codegen.gwt.jsonoverlay.processor.TypeMapper;
import io.codegen.gwt.jsonoverlay.processor.model.JavaGetter;
import io.codegen.gwt.jsonoverlay.processor.model.JavaInterface;

public class ModelBuilder {

    private final Set<ClassName> classes = new HashSet<>();

    private final Set<JavaInterface> interfaces = new HashSet<>();

    public void addToModel(TypeElement element) {
        ClassName className = ClassName.get(element);

        if (classes.add(className)) {
            JavaInterface javaInterface = JavaInterface.builder()
                    .type(element.asType().accept(new TypeResolver(this::addToModel), null))
                    .getters(buildGetters(element))
                    .build();

            interfaces.add(javaInterface);
        }
    }

    public Collection<JavaInterface> getJavaInterfaces() {
        return interfaces;
    }

    private List<JavaGetter> buildGetters(TypeElement element) {
        List<ExecutableElement> methods = getAllInterfaceMembers(element)
                .filter(type -> ElementKind.METHOD.equals(type.getKind()))
                .map(TypeMapper::asExecutable)
                .filter(method -> !method.isDefault())
                .filter(method -> !TypeKind.VOID.equals(method.getReturnType().getKind()))
                .collect(Collectors.toList());

        return IntStream.range(0, methods.size())
            .filter(index -> {
                Name name = methods.get(index).getSimpleName();
                return methods.stream()
                    .limit(index)
                    .noneMatch(method -> name.equals(method.getSimpleName()));
            })
            .mapToObj(index -> buildGetter(methods.get(index)))
            .collect(Collectors.toList());
    }

    private JavaGetter buildGetter(ExecutableElement element) {
        JavaGetter getter = JavaGetter.builder()
                .methodName(element.getSimpleName().toString())
                .propertyName(getPropertyName(element.getSimpleName().toString()))
                .propertyType(element.getReturnType().accept(new TypeResolver(this::addToModel), null))
                .build();

        return getter;
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

    private Stream<? extends Element> getAllInterfaceMembers(TypeElement type) {
        if (!ElementKind.INTERFACE.equals(type.getKind())) {
            throw new IllegalArgumentException("Unexpected type " + type);
        }

        Stream<? extends Element> inherited = type.getInterfaces().stream()
            .flatMap(mirror -> getAllInterfaceMembers(TypeMapper.asType(TypeMapper.asDeclaredType(mirror).asElement())));

        return Stream.concat(type.getEnclosedElements().stream(), inherited);
    }

}

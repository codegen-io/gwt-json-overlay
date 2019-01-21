package io.codegen.gwt.jsonoverlay.processor.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;

import io.codegen.gwt.jsonoverlay.processor.ClassNames;
import io.codegen.gwt.jsonoverlay.processor.model.JavaTypeVisitor;
import io.codegen.gwt.jsonoverlay.processor.model.types.BoxedType;
import io.codegen.gwt.jsonoverlay.processor.model.types.EnumType;
import io.codegen.gwt.jsonoverlay.processor.model.types.InheritedType;
import io.codegen.gwt.jsonoverlay.processor.model.types.JavaScriptObjectType;
import io.codegen.gwt.jsonoverlay.processor.model.types.ListType;
import io.codegen.gwt.jsonoverlay.processor.model.types.MapType;
import io.codegen.gwt.jsonoverlay.processor.model.types.OptionalType;
import io.codegen.gwt.jsonoverlay.processor.model.types.OverlayType;
import io.codegen.gwt.jsonoverlay.processor.model.types.PrimitiveType;
import io.codegen.gwt.jsonoverlay.processor.model.types.StringType;
import io.codegen.gwt.jsonoverlay.processor.model.types.SubType;

public class FieldGetterTranslatorGenerator implements JavaTypeVisitor<CodeBlock> {

    private final String packageName;

    private final String methodName;

    public FieldGetterTranslatorGenerator(String packageName, String methodName) {
        this.packageName = packageName;
        this.methodName = methodName;
    }

    @Override
    public CodeBlock visitPrimitiveType(PrimitiveType type) {
        if (TypeName.LONG.equals(type.getPrimitiveType())) {
            return CodeBlock.builder()
                    .addStatement("return ($T) object.$L", TypeName.LONG, methodName)
                    .build();
        }

        return CodeBlock.builder()
                .addStatement("return object.$L", methodName)
                .build();
    }

    @Override
    public CodeBlock visitBoxedType(BoxedType type) {
        String primitive = type.getBoxedType().unbox().toString();
        String typeName = Character.toTitleCase(primitive.charAt(0)) + primitive.substring(1);

        if (TypeName.BOOLEAN.box().equals(type.getBoxedType())) {
            return CodeBlock.builder()
                    .addStatement("return object.$L == $T.undefinedObject() ? null : $T.valueOf($T.as$L(object.$L))", methodName, ClassNames.JSON_HELPER, type.getBoxedType(), ClassNames.JSINTEROP_BASE_JS, typeName, methodName)
                    .build();
        }

        if (TypeName.CHAR.box().equals(type.getBoxedType())) {
            return CodeBlock.builder()
                    .addStatement("return object.$L == undefinedObject() ? null : $T.valueOf((char) object.$L.intValue())", methodName, type.getBoxedType(), methodName)
                    .build();

        }

        return CodeBlock.builder()
                .addStatement("return object.$L == undefinedObject() ? null : $T.valueOf(object.$L.$LValue())", methodName, type.getBoxedType(), methodName, primitive)
                .build();
    }

    @Override
    public CodeBlock visitStringType(StringType type) {
        return CodeBlock.builder()
                .addStatement("return object.$L", methodName)
                .build();
    }

    @Override
    public CodeBlock visitOptionalType(OptionalType type) {
        TypeName returnType = type.getElementType().accept(new ReturnTypeResolver());
        if (TypeName.INT.equals(returnType)) {
            return CodeBlock.of("return object.$L == null ? $T.empty() : $T.of(object.$L.intValue());\n", methodName, OptionalInt.class, OptionalInt.class, methodName);
        }

        if (TypeName.LONG.equals(returnType)) {
            return CodeBlock.of("return object.$L == null ? $T.empty() : $T.of(object.$L.longValue());\n", methodName, OptionalLong.class, OptionalLong.class, methodName);
        }

        if (TypeName.DOUBLE.equals(returnType)) {
            return CodeBlock.of("return object.$L == null ? $T.empty() : $T.of(object.$L.doubleValue());\n", methodName, OptionalDouble.class, OptionalDouble.class, methodName);
        }

        CodeBlock mapper = type.getElementType().accept(new FieldGetterMapperGenerator(packageName));

        return CodeBlock.builder()
                .addStatement(Stream.of(
                        CodeBlock.of("return $T.ofNullable(object.$L)", Optional.class, methodName),
                        mapper)
                        .filter(code -> !code.isEmpty())
                        .collect(CodeBlock.joining("\n")))
                .build();
    }

    @Override
    public CodeBlock visitListType(ListType type) {
        CodeBlock mapper = type.getElementType().accept(new FieldGetterMapperGenerator(packageName));

        return CodeBlock.builder()
                .beginControlFlow("if (object.$L == null)", methodName)
                    .addStatement("return $T.emptyList()", Collections.class)
                .endControlFlow()
                .addStatement(Stream.of(
                        CodeBlock.of("return $T.of(object.$L)", Stream.class, methodName),
                        mapper,
                        CodeBlock.of(".collect($T.toList())", Collectors.class))
                        .filter(code -> !code.isEmpty())
                        .collect(CodeBlock.joining("\n")))
                .build();
    }

    @Override
    public CodeBlock visitMapType(MapType type) {
        CodeBlock mapper = type.getValueType().accept(new FieldGetterMapperGenerator(packageName));

        return CodeBlock.builder()
                .beginControlFlow("if (object.$L == null)", methodName)
                .addStatement("return $T.emptyMap()", Collections.class)
                .endControlFlow()

                .addStatement("$T<$T> keys = new $T<>()", List.class, String.class, ArrayList.class)
                .addStatement("object.$L.forEach(keys::add)", methodName)
                .add("return keys.stream()\n")
                .addStatement(Stream.of(
                        CodeBlock.of(".collect($T.toMap($T.identity(),", Collectors.class, Function.class),
                        CodeBlock.of("key -> $T.ofNullable(object.$L.get(key))", Optional.class, methodName),
                        mapper,
                        CodeBlock.of(".orElse(null)))"))
                        .filter(code -> !code.isEmpty())
                        .collect(CodeBlock.joining("\n")))
                .build();
    }

    @Override
    public CodeBlock visitOverlayType(OverlayType type) {
        ClassName overlay = type.getOverlayType();
        return CodeBlock.builder()
                .addStatement("return $T.WRAPPER.apply(object.$L)", ClassName.get(packageName, overlay.simpleName() + "_JSONOverlay"), methodName)
                .build();
    }

    @Override
    public CodeBlock visitEnumType(EnumType type) {
        return CodeBlock.builder()
                .addStatement("return $T.valueOf(object.$L)", type.getEnumType(), methodName)
                .build();
    }

    @Override
    public CodeBlock visitInheritedType(InheritedType type) {
        ClassName superType = type.getSuperType();
        return CodeBlock.builder()
                .addStatement("return $T.WRAPPER.apply(object.$L)", ClassName.get(packageName, superType.simpleName() + "_JSONOverlay"), methodName)
                .build();
    }

    @Override
    public CodeBlock visitSubType(SubType type) {
        ClassName overlay = type.getSubType();
        return CodeBlock.builder()
                .addStatement("return $T.WRAPPER.apply(object.$L)", ClassName.get(packageName, overlay.simpleName() + "_JSONOverlay"), methodName)
                .build();
    }

    @Override
    public CodeBlock visitJavaScriptObjectType(JavaScriptObjectType type) {
        throw new UnsupportedOperationException();
    }

}

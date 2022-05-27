package io.codegen.gwt.jsonoverlay.processor.generator;

import java.util.stream.Stream;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;

import io.codegen.gwt.jsonoverlay.processor.ClassNames;
import io.codegen.gwt.jsonoverlay.processor.model.JavaTypeVisitor;
import io.codegen.gwt.jsonoverlay.processor.model.types.BoxedType;
import io.codegen.gwt.jsonoverlay.processor.model.types.DateType;
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

public class FieldSetterTranslatorGenerator implements JavaTypeVisitor<CodeBlock> {

    private final String packageName;

    private final String propertyName;

    public FieldSetterTranslatorGenerator(String packageName, String propertyName) {
        this.packageName = packageName;
        this.propertyName = propertyName;
    }

    @Override
    public CodeBlock visitPrimitiveType(PrimitiveType type) {
        return CodeBlock.builder()
                .addStatement("object.$L = value", propertyName)
                .build();
    }

    @Override
    public CodeBlock visitBoxedType(BoxedType type) {
        if (!TypeName.BOOLEAN.box().equals(type.getBoxedType())) {
            return CodeBlock.builder()
                    .addStatement("object.$L = value == null ? null : $T.valueOf(value.$LValue())", propertyName, TypeName.DOUBLE.box(), type.getBoxedType().unbox().toString())
                    .build();

        }
        return CodeBlock.builder()
                .addStatement("object.$L = value", propertyName)
                .build();
    }

    @Override
    public CodeBlock visitStringType(StringType type) {
        return CodeBlock.builder()
                .addStatement("object.$L = value", propertyName)
                .build();
    }

    @Override
    public CodeBlock visitOptionalType(OptionalType type) {
        TypeName fieldType = type.getElementType().accept(new ReturnTypeResolver());
        if (TypeName.INT.equals(fieldType) || TypeName.LONG.equals(fieldType) || TypeName.DOUBLE.equals(fieldType)) {
            String name = Character.toTitleCase(fieldType.toString().charAt(0)) + fieldType.toString().substring(1);
            return CodeBlock.builder()
                    .beginControlFlow("if (value.isPresent())")
                    .addStatement("object.$L = $T.valueOf(value.getAs$L())", propertyName, TypeName.DOUBLE.box(), name)
                    .nextControlFlow("else")
                    .addStatement("object.$L = null", propertyName)
                    .endControlFlow()
                    .build();
        }

        CodeBlock mapper = type.getElementType().accept(new FieldSetterMapperGenerator(packageName));
        return CodeBlock.builder()
                .addStatement(Stream.of(
                        CodeBlock.of("object.$L = value", propertyName),
                        mapper,
                        CodeBlock.of(".orElse(null)")
                        )
                        .filter(code -> !code.isEmpty())
                        .collect(CodeBlock.joining("\n")))
                .build();
    }

    @Override
    public CodeBlock visitListType(ListType type) {
        CodeBlock typeMapper = type.getElementType().accept(new FieldSetterMapperGenerator(packageName));
        CodeBlock arrayMapper = type.getElementType().accept(new FieldSetterListMapperGenerator(packageName));

        return CodeBlock.builder()
                .addStatement(Stream.of(
                        CodeBlock.of("object.$L = value.stream()", propertyName),
                        typeMapper,
                        arrayMapper)
                        .filter(code -> !code.isEmpty())
                        .collect(CodeBlock.joining("\n")))
                .build();
    }

    @Override
    public CodeBlock visitMapType(MapType type) {
        CodeBlock mapper = type.getValueType().accept(new FieldSetterMapperGenerator(packageName));

        return CodeBlock.builder()
                .addStatement("object.$L = $T.cast($T.of())", propertyName, ClassNames.JSINTEROP_BASE_JS, ClassNames.JSINTEROP_BASE_JSPROPERTYMAP)
                .addStatement(Stream.of(
                        CodeBlock.of("value.forEach((key, item) -> object.$L.set(key, Optional.ofNullable(item)", propertyName),
                        mapper,
                        CodeBlock.of(".orElse(null)))"))
                        .filter(code -> !code.isEmpty())
                        .collect(CodeBlock.joining("\n")))
                .build();
    }

    @Override
    public CodeBlock visitDateType(DateType type) {
        return CodeBlock.builder()
                .addStatement("object.$L = value.toString()", propertyName)
                .build();
    }

    @Override
    public CodeBlock visitOverlayType(OverlayType type) {
        ClassName overlay = type.getOverlayType();
        return CodeBlock.builder()
                .addStatement("object.$L = $T.UNWRAPPER.apply(value)", propertyName, ClassName.get(packageName, overlay.simpleName() + "_JSONOverlay"))
                .build();
    }

    @Override
    public CodeBlock visitEnumType(EnumType type) {
        return CodeBlock.builder()
                .addStatement("object.$L = value.name()", propertyName)
                .build();
    }

    @Override
    public CodeBlock visitInheritedType(InheritedType type) {
        ClassName superType = type.getSuperType();
        return CodeBlock.builder()
                .addStatement("object.$L = $T.WRAPPER.apply(value)", propertyName, ClassName.get(packageName, superType.simpleName() + "_JSONOverlay"))
                .build();
    }

    @Override
    public CodeBlock visitSubType(SubType type) {
        ClassName overlay = type.getSubType();
        return CodeBlock.builder()
                .addStatement("object.$L = $T.UNWRAPPER.apply(value)", propertyName, ClassName.get(packageName, overlay.simpleName() + "_JSONOverlay"))
                .build();
    }

    @Override
    public CodeBlock visitJavaScriptObjectType(JavaScriptObjectType type) {
        throw new UnsupportedOperationException();
    }

}

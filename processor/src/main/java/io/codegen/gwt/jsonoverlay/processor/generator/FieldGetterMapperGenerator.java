package io.codegen.gwt.jsonoverlay.processor.generator;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;

import io.codegen.gwt.jsonoverlay.processor.model.JavaTypeVisitor;
import io.codegen.gwt.jsonoverlay.processor.model.types.BoxedType;
import io.codegen.gwt.jsonoverlay.processor.model.types.EnumType;
import io.codegen.gwt.jsonoverlay.processor.model.types.InheritedType;
import io.codegen.gwt.jsonoverlay.processor.model.types.ListType;
import io.codegen.gwt.jsonoverlay.processor.model.types.MapType;
import io.codegen.gwt.jsonoverlay.processor.model.types.OptionalType;
import io.codegen.gwt.jsonoverlay.processor.model.types.OverlayType;
import io.codegen.gwt.jsonoverlay.processor.model.types.PrimitiveType;
import io.codegen.gwt.jsonoverlay.processor.model.types.StringType;
import io.codegen.gwt.jsonoverlay.processor.model.types.SubType;

public class FieldGetterMapperGenerator implements JavaTypeVisitor<CodeBlock> {

    private static final CodeBlock EMPTY_CODEBLOCK = CodeBlock.builder().build();

    private final String packageName;

    public FieldGetterMapperGenerator(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public CodeBlock visitPrimitiveType(PrimitiveType type) {
        return EMPTY_CODEBLOCK;
    }

    @Override
    public CodeBlock visitBoxedType(BoxedType type) {
        return EMPTY_CODEBLOCK;
    }

    @Override
    public CodeBlock visitStringType(StringType type) {
        return EMPTY_CODEBLOCK;
    }

    @Override
    public CodeBlock visitOptionalType(OptionalType type) {
        throw new UnsupportedOperationException("Mapper not supported for optional type " + type.getElementType());
    }

    @Override
    public CodeBlock visitListType(ListType type) {
        throw new UnsupportedOperationException("Mapper not supported for list type " + type.getElementType());
    }


    @Override
    public CodeBlock visitMapType(MapType type) {
        throw new UnsupportedOperationException("Mapper not supported for map type " + type.getValueType());
    }

    @Override
    public CodeBlock visitOverlayType(OverlayType type) {
        ClassName overlay = type.getOverlayType();
        return CodeBlock.builder()
                .add(".map($T.WRAPPER)", ClassName.get(packageName, overlay.simpleName() + "_JSONOverlay"))
                .build();
    }

    @Override
    public CodeBlock visitEnumType(EnumType type) {
        throw new UnsupportedOperationException();
    }

    @Override
    public CodeBlock visitInheritedType(InheritedType type) {
        ClassName overlay = type.getSuperType();
        return CodeBlock.builder()
                .add(".map($T.WRAPPER)", ClassName.get(packageName, overlay.simpleName() + "_JSONOverlay"))
                .build();
    }

    @Override
    public CodeBlock visitSubType(SubType type) {
        ClassName overlay = type.getSubType();
        return CodeBlock.builder()
                .add(".map($T.WRAPPER)", ClassName.get(packageName, overlay.simpleName() + "_JSONOverlay"))
                .build();
    }

}

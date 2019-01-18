package io.codegen.gwt.jsonoverlay.processor.generator;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;

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

public class InitializerStatementGenerator implements JavaTypeVisitor<CodeBlock> {

    @Override
    public CodeBlock visitPrimitiveType(PrimitiveType type) {
        if (TypeName.BOOLEAN.equals(type.getPrimitiveType())) {
            return CodeBlock.of("false");
        } else {
            return CodeBlock.of("0");
        }
    }

    @Override
    public CodeBlock visitBoxedType(BoxedType type) {
        return CodeBlock.of("null");
    }

    @Override
    public CodeBlock visitStringType(StringType type) {
        return CodeBlock.of("null");
    }

    @Override
    public CodeBlock visitOptionalType(OptionalType type) {
        TypeName returnType = type.getElementType().accept(new ReturnTypeResolver());
        if (TypeName.INT.equals(returnType)
                || TypeName.LONG.equals(returnType)
                || TypeName.DOUBLE.equals(returnType)) {
            return CodeBlock.of("undefinedInt()");
        }

        return CodeBlock.of("null");
    }

    @Override
    public CodeBlock visitListType(ListType type) {
        return CodeBlock.of("null");
    }

    @Override
    public CodeBlock visitMapType(MapType type) {
        return CodeBlock.of("null");
    }

    @Override
    public CodeBlock visitOverlayType(OverlayType type) {
        return CodeBlock.of("null");
    }

    @Override
    public CodeBlock visitEnumType(EnumType type) {
        return CodeBlock.of("null");
    }

    @Override
    public CodeBlock visitInheritedType(InheritedType type) {
        return CodeBlock.of("null");
    }

    @Override
    public CodeBlock visitSubType(SubType type) {
        return CodeBlock.of("null");
    }

    @Override
    public CodeBlock visitJavaScriptObjectType(JavaScriptObjectType type) {
        return CodeBlock.of("null");
    }

}

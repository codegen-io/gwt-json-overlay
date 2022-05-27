package io.codegen.gwt.jsonoverlay.processor.generator;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;

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

public class UnwrapStatementGenerator implements JavaTypeVisitor<CodeBlock> {

    private static final CodeBlock EMPTY_CODEBLOCK = CodeBlock.builder().build();

    private final String packageName;

    public UnwrapStatementGenerator(String packageName) {
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
        return EMPTY_CODEBLOCK;
    }

    @Override
    public CodeBlock visitListType(ListType type) {
        return EMPTY_CODEBLOCK;
    }

    @Override
    public CodeBlock visitMapType(MapType type) {
        return EMPTY_CODEBLOCK;
    }

    @Override
    public CodeBlock visitDateType(DateType type) {
        return EMPTY_CODEBLOCK;
    }

    @Override
    public CodeBlock visitOverlayType(OverlayType type) {
        return EMPTY_CODEBLOCK;
    }

    @Override
    public CodeBlock visitEnumType(EnumType type) {
        return EMPTY_CODEBLOCK;
    }

    @Override
    public CodeBlock visitInheritedType(InheritedType type) {
        return type.getInheritedTypes().entrySet().stream()
                .map(entry -> generateCaseStatement(entry.getValue().accept(new SuperTypeResolver()), entry.getValue().accept(new OverlayTypeResolver(packageName))))
                .collect(CodeBlock.joining(""));
    }

    private CodeBlock generateCaseStatement(ClassName superType, ClassName overlay) {
        return CodeBlock.builder()
                .beginControlFlow("if (wrapper instanceof $T)", superType)
                    .addStatement("return $T.UNWRAPPER.apply(($T) wrapper)", overlay, superType)
                .endControlFlow()
                .build();
    }

    @Override
    public CodeBlock visitSubType(SubType type) {
        return EMPTY_CODEBLOCK;
    }

    @Override
    public CodeBlock visitJavaScriptObjectType(JavaScriptObjectType type) {
        return EMPTY_CODEBLOCK;
    }

}

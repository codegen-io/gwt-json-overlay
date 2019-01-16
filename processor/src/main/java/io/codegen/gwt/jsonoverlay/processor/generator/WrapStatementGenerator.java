package io.codegen.gwt.jsonoverlay.processor.generator;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;

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

public class WrapStatementGenerator implements JavaTypeVisitor<CodeBlock> {

    private final String packageName;

    public WrapStatementGenerator(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public CodeBlock visitPrimitiveType(PrimitiveType type) {
        throw new UnsupportedOperationException();
    }

    @Override
    public CodeBlock visitBoxedType(BoxedType type) {
        throw new UnsupportedOperationException();
    }

    @Override
    public CodeBlock visitStringType(StringType type) {
        throw new UnsupportedOperationException();
    }

    @Override
    public CodeBlock visitOptionalType(OptionalType type) {
        throw new UnsupportedOperationException();
    }

    @Override
    public CodeBlock visitListType(ListType type) {
        throw new UnsupportedOperationException();
    }

    @Override
    public CodeBlock visitMapType(MapType type) {
        throw new UnsupportedOperationException();
    }

    @Override
    public CodeBlock visitOverlayType(OverlayType type) {
        ClassName overlay = ClassName.get(packageName, type.getOverlayType().simpleName() + OverlayGenerator.OVERLAY_SUFFIX);
        return CodeBlock.of("return new $T(($T) object);\n", overlay, overlay.nestedClass("JsObject"));
    }

    @Override
    public CodeBlock visitEnumType(EnumType type) {
        throw new UnsupportedOperationException();
    }

    @Override
    public CodeBlock visitInheritedType(InheritedType type) {
        ClassName overlay = type.accept(new OverlayTypeResolver(packageName));

        /*
         * switch (((JsObject) object).getKind) { case "a": return
         * InheritanceSubA_JSONOverlay.WRAPPER.apply((InheritanceSubA_JSONOverlay.JsObject) object); case "b": return
         * InheritanceSubB_JSONOverlay.WRAPPER.apply((InheritanceSubB_JSONOverlay.JsObject) object); default: throw new
         * IllegalArgumentException("Unknown discriminator '" + ((JsObject) object).getKind + "'"); }
         */
        CodeBlock caseStatements = type.getInheritedTypes().entrySet().stream()
                .map(entry -> generateCaseStatement(entry.getKey(), entry.getValue().accept(new OverlayTypeResolver(packageName))))
                .collect(CodeBlock.joining(""));

        return CodeBlock.builder()
                .beginControlFlow("switch ((($T) object).$L)", overlay.nestedClass("JsObject"), type.getDiscriminatorMethodName())
                .add(caseStatements)
                .add("default:\n")
                .indent()
                .addStatement("throw new $T(\"Unknown discriminator '\" + (($T) object).$L + \"'\")",
                        IllegalArgumentException.class, overlay.nestedClass("JsObject"), type.getDiscriminatorMethodName())
                .unindent()
                .endControlFlow()
                .build();
    }

    private CodeBlock generateCaseStatement(String name, ClassName overlay) {
        return CodeBlock.builder()
                .add("case $S:\n", name)
                .indent()
                .addStatement("return new $T(($T) object)", overlay, overlay.nestedClass("JsObject"))
                .unindent()
                .build();
    }

    @Override
    public CodeBlock visitSubType(SubType type) {
        ClassName overlay = ClassName.get(packageName, type.getSubType().simpleName() + OverlayGenerator.OVERLAY_SUFFIX);
        return CodeBlock.of("return new $T(($T) object);\n", overlay, overlay.nestedClass("JsObject"));
    }

    @Override
    public CodeBlock visitJavaScriptObjectType(JavaScriptObjectType type) {
        throw new UnsupportedOperationException();
    }

}

package io.codegen.gwt.jsonoverlay.processor.generator;

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

public class FieldTranslatorGenerator implements JavaTypeVisitor<CodeBlock> {

    private final String packageName;

    private final String methodName;

    private final String propertyName;

    public FieldTranslatorGenerator(String packageName, String methodName, String propertyName) {
        this.packageName = packageName;
        this.methodName = methodName;
        this.propertyName = propertyName;
    }

    @Override
    public CodeBlock visitPrimitiveType(PrimitiveType type) {
        return CodeBlock.builder()
                .addStatement("object.$L = wrapper.$L()", propertyName, methodName)
                .build();
    }

    @Override
    public CodeBlock visitBoxedType(BoxedType type) {
        return CodeBlock.builder()
                .addStatement("object.$L = wrapper.$L()", propertyName, methodName)
                .build();
    }

    @Override
    public CodeBlock visitStringType(StringType type) {
        return CodeBlock.builder()
                .addStatement("object.$L = wrapper.$L()", propertyName, methodName)
                .build();
    }

    @Override
    public CodeBlock visitOptionalType(OptionalType type) {
        TypeName returnType = type.getElementType().accept(new ReturnTypeResolver());
        if (TypeName.INT.equals(returnType)
                || TypeName.LONG.equals(returnType)
                || TypeName.DOUBLE.equals(returnType)) {
            return CodeBlock.builder()
                    .add("object.$L = wrapper.$L()\n", propertyName, methodName)
                    .add("\t.orElse(undefinedInt());\n")
                    .build();
        }

        CodeBlock mapper = type.getElementType().accept(new FieldSetterMapperGenerator(packageName));

        return CodeBlock.builder()
                .addStatement(Stream.of(
                        CodeBlock.of("object.$L = wrapper.$L()", propertyName, methodName),
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
                        CodeBlock.of("object.$L = wrapper.$L().stream()", propertyName, methodName),
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
                        CodeBlock.of("wrapper.$L().forEach((key, item) -> object.$L.set(key, Optional.ofNullable(item)", methodName, propertyName),
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
                .addStatement("object.$L = $T.UNWRAPPER.apply(wrapper.$L())", propertyName, ClassName.get(packageName, overlay.simpleName() + "_JSONOverlay"), methodName)
                .build();
    }

    @Override
    public CodeBlock visitEnumType(EnumType type) {
        return CodeBlock.builder()
                .addStatement("object.$L = wrapper.$L().name()", propertyName, methodName)
                .build();
    }

    @Override
    public CodeBlock visitInheritedType(InheritedType type) {
        ClassName superType = type.getSuperType();
        return CodeBlock.builder()
                .addStatement("object.$L = $T.UNWRAPPER.apply(wrapper.$L())", propertyName, ClassName.get(packageName, superType.simpleName() + "_JSONOverlay"), methodName)
                .build();
    }

    @Override
    public CodeBlock visitSubType(SubType type) {
        ClassName overlay = type.getSubType();
        return CodeBlock.builder()
                .addStatement("object.$L = $T.UNWRAPPER.apply(wrapper.$L())", propertyName, ClassName.get(packageName, overlay.simpleName() + "_JSONOverlay"), methodName)
                .build();
    }

    @Override
    public CodeBlock visitJavaScriptObjectType(JavaScriptObjectType type) {
        throw new UnsupportedOperationException();
    }

}

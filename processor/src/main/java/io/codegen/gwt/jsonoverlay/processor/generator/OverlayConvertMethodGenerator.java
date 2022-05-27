package io.codegen.gwt.jsonoverlay.processor.generator;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

import io.codegen.gwt.jsonoverlay.processor.ClassNames;
import io.codegen.gwt.jsonoverlay.processor.model.JavaType;
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

public class OverlayConvertMethodGenerator implements JavaTypeVisitor<MethodSpec> {

    private final String packageName;

    private final String name;

    private final JavaType argument;

    public OverlayConvertMethodGenerator(String packageName, String name, JavaType argument) {
        this.packageName = packageName;
        this.name = name;
        this.argument = argument;
    }


    private MethodSpec createConvertMethod(JavaType type, CodeBlock body) {
        TypeName returnType = type.accept(new ReturnTypeResolver());
        TypeName argumentType = argument.accept(new ReturnTypeResolver());

        return MethodSpec.methodBuilder(name)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(argumentType, "argument")
                .returns(returnType)
                .addCode(body)
                .build();
    }

    @Override
    public MethodSpec visitPrimitiveType(PrimitiveType type) {
        throw new UnsupportedOperationException();
    }



    @Override
    public MethodSpec visitBoxedType(BoxedType type) {
        throw new UnsupportedOperationException();
    }



    @Override
    public MethodSpec visitStringType(StringType type) {
        throw new UnsupportedOperationException();
    }



    @Override
    public MethodSpec visitOptionalType(OptionalType type) {
        throw new UnsupportedOperationException();
    }



    @Override
    public MethodSpec visitListType(ListType type) {
        TypeName returnType = ParameterizedTypeName.get(ClassName.get(List.class), type.getElementType().accept(new ReturnTypeResolver()));
        TypeName argumentType = argument.accept(new ReturnTypeResolver());

        CodeBlock mapper = type.getElementType().accept(new FieldGetterMapperGenerator(packageName));

        CodeBlock body = CodeBlock.builder()
                .addStatement("$T<$T> array = cast(argument)", ClassNames.JSINTEROP_BASE_JSARRAYLIKE, type.getElementType().accept(new FieldTypeResolver(packageName)))
                .addStatement(Stream.of(
                        CodeBlock.of("return array.asList().stream()"),
                        mapper,
                        CodeBlock.of(".collect($T.toList())", Collectors.class))
                        .filter(code -> !code.isEmpty())
                        .collect(CodeBlock.joining("\n")))
                .build();

        return MethodSpec.methodBuilder(name)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(argumentType, "argument")
                .returns(returnType)
                .addCode(body)
                .build();
    }



    @Override
    public MethodSpec visitMapType(MapType type) {
        throw new UnsupportedOperationException();
    }

    @Override
    public MethodSpec visitDateType(DateType type) {
        throw new UnsupportedOperationException();
    }

    @Override
    public MethodSpec visitOverlayType(OverlayType type) {
        return createConvertMethod(type, CodeBlock.builder()
                .addStatement("return $T.wrap(argument)", type.accept(new OverlayTypeResolver(packageName)))
                .build());
    }



    @Override
    public MethodSpec visitEnumType(EnumType type) {
        throw new UnsupportedOperationException();
    }



    @Override
    public MethodSpec visitInheritedType(InheritedType type) {
        throw new UnsupportedOperationException();
    }



    @Override
    public MethodSpec visitSubType(SubType type) {
        throw new UnsupportedOperationException();
    }



    @Override
    public MethodSpec visitJavaScriptObjectType(JavaScriptObjectType type) {
        return createConvertMethod(type, CodeBlock.builder()
                .addStatement("return cast($T.unwrap(argument))", argument.accept(new OverlayTypeResolver(packageName)))
                .build());
    }

}

package io.codegen.gwt.jsonoverlay.processor.generator;

import java.util.function.Function;
import java.util.stream.Collectors;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import io.codegen.gwt.jsonoverlay.processor.ClassNames;
import io.codegen.gwt.jsonoverlay.processor.model.JavaGetter;
import io.codegen.gwt.jsonoverlay.processor.model.JavaInterface;
import io.codegen.gwt.jsonoverlay.processor.model.types.ClassType;

public class OverlayGenerator {

    public static final String OVERLAY_SUFFIX = "_JSONOverlay";

    private final String packageName;

    public OverlayGenerator(String packageName) {
        this.packageName = packageName;
    }

    public TypeSpec generateOverlay(JavaInterface javaInterface) {
        ClassName superType = javaInterface.getType().accept(new SuperTypeResolver());

        ClassName overlayName = ClassName.get(packageName, superType.simpleName() + OVERLAY_SUFFIX);

        TypeSpec.Builder typeSpec = TypeSpec.classBuilder(overlayName)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL);

        if (javaInterface.getType() instanceof ClassType) {
            typeSpec.superclass(superType);
        } else {
            typeSpec.addSuperinterface(superType);
        }

        typeSpec.addField(FieldSpec.builder(ParameterizedTypeName.get(ClassName.get(Function.class),
                overlayName.nestedClass("JsObject"), superType), "WRAPPER")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .initializer("$T::wrap", overlayName)
                .build());

        typeSpec.addField(FieldSpec.builder(ParameterizedTypeName.get(ClassName.get(Function.class),
                superType, overlayName.nestedClass("JsObject")), "UNWRAPPER")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .initializer("$T::unwrap", overlayName)
                .build());

        ClassName jsSuperType = javaInterface.getType().accept(new JsObjectSuperTypeResolver(packageName));
        TypeSpec jsObject = TypeSpec.classBuilder(overlayName.nestedClass("JsObject"))
            .superclass(jsSuperType)
            .addModifiers(Modifier.STATIC)
            .addAnnotation(AnnotationSpec.builder(ClassNames.JSINTEROP_JSTYPE)
                    .addMember("isNative", "true")
                    .addMember("namespace", "$T.GLOBAL", ClassNames.JSINTEROP_JSPACKAGE)
                    .addMember("name", "$S", "Object")
                    .build())
            .addFields(javaInterface.getGetters().stream()
                    .map(this::generateProperty)
                    .collect(Collectors.toList()))
            .build();

        typeSpec.addType(jsObject);

        typeSpec.addField(overlayName.nestedClass("JsObject"), "object", Modifier.PRIVATE, Modifier.FINAL);
        typeSpec.addMethod(MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PROTECTED)
                .addCode(CodeBlock.builder()
                        .addStatement("this.object = new $T()", overlayName.nestedClass("JsObject"))
                        .build())
                .build());
        typeSpec.addMethod(MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PROTECTED)
                .addParameter(overlayName.nestedClass("JsObject"), "object")
                .addCode(CodeBlock.builder()
                        .addStatement("this.object = object")
                        .build())
                .build());

        typeSpec.addMethods(javaInterface.getGetters().stream()
                .map(this::generateGetMethod)
                .collect(Collectors.toList()));

        typeSpec.addMethods(javaInterface.getGetters().stream()
                .filter(JavaGetter::hasSetter)
                .map(this::generateSetMethod)
                .collect(Collectors.toList()));

        typeSpec.addMethod(MethodSpec.methodBuilder("parse")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addParameter(String.class, "json")
                .addParameter(ClassNames.JSON_FACTORY, "factory")
                .returns(superType)
                .addCode(CodeBlock.builder()
                        .addStatement("return factory.createParser($T.WRAPPER).fromJSON(json)", overlayName)
                        .build())
                .build());

        typeSpec.addMethod(MethodSpec.methodBuilder("serialize")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addParameter(superType, "object")
                .addParameter(ClassNames.JSON_FACTORY, "factory")
                .returns(String.class)
                .addCode(CodeBlock.builder()
                        .addStatement("return factory.createSerializer($T.UNWRAPPER).toJSON(object)", overlayName)
                        .build())
                .build());

        typeSpec.addMethod(MethodSpec.methodBuilder("create")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(superType)
                .addCode(CodeBlock.builder()
                        .addStatement("$T object = new $T()", overlayName.nestedClass("JsObject"), overlayName.nestedClass("JsObject"))
                        .add(CodeBlock.join(javaInterface.getGetters().stream()
                                .map(this::generateInitializer)
                                .collect(Collectors.toList()), ""))
                        .addStatement("return wrap(object)")
                        .build())
                .build());

        CodeBlock wrapper = javaInterface.getType().accept(new WrapStatementGenerator(packageName));

        typeSpec.addMethod(MethodSpec.methodBuilder("wrap")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addParameter(Object.class, "object")
                .returns(superType)
                .addCode(CodeBlock.builder()
                        .beginControlFlow("if (object instanceof $T)", overlayName.nestedClass("JsObject"))
                            .add(wrapper)
                        .endControlFlow()
                        .addStatement("throw new $T(\"Object '\" + object + \"' isn't of type $L\")",
                                IllegalArgumentException.class, overlayName.nestedClass("JsObject").toString())
                        .build())
                .build());

        CodeBlock unwrapper = javaInterface.getType().accept(new UnwrapStatementGenerator(packageName));

        typeSpec.addMethod(MethodSpec.methodBuilder("unwrap")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addParameter(superType, "wrapper")
                .returns(overlayName.nestedClass("JsObject"))
                .addCode(CodeBlock.builder()
                        .beginControlFlow("if (wrapper instanceof $T)", overlayName)
                            .addStatement("return (($T) wrapper).object", overlayName)
                        .endControlFlow()
                        .add(unwrapper)
                        .addStatement("$T object = new $T()", overlayName.nestedClass("JsObject"), overlayName.nestedClass("JsObject"))
                        .add(CodeBlock.join(javaInterface.getGetters().stream()
                                .map(getter -> getter.getPropertyType().accept(new FieldTranslatorGenerator(packageName, getter.getMethodName(), getter.getMethodName())))
                                .collect(Collectors.toList()), ""))
                        .addStatement("return object")
                        .build())
                .build());

        return typeSpec.build();
    }

    private FieldSpec generateProperty(JavaGetter getter) {
        FieldGenerator generator = new FieldGenerator(packageName, getter.getMethodName(), getter.getPropertyName());
        return getter.getPropertyType().accept(generator);
    }

    private MethodSpec generateGetMethod(JavaGetter getter) {
        CodeBlock translator = getter.getPropertyType().accept(new FieldGetterTranslatorGenerator(packageName, getter.getMethodName()));

        TypeName returnType = getter.getPropertyType().accept(new ReturnTypeResolver());

        return MethodSpec.methodBuilder(getter.getMethodName())
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(returnType)
                .addCode(translator)
                .build();
    }

    private MethodSpec generateSetMethod(JavaGetter getter) {
        CodeBlock translator = getter.getPropertyType().accept(new FieldSetterTranslatorGenerator(packageName, getter.getMethodName()));

        TypeName returnType = getter.getPropertyType().accept(new ReturnTypeResolver());

        return MethodSpec.methodBuilder("set" + getter.getMethodName().substring(3))
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(returnType, "value")
                .addCode(translator)
                .build();
    }

    private CodeBlock generateInitializer(JavaGetter getter) {
        return CodeBlock.builder()
                .add("object.$L = ", getter.getMethodName())
                .add(getter.getPropertyType().accept(new InitializerStatementGenerator()))
                .add(";\n")
                .build();
    }

}

package io.codegen.gwt.jsonoverlay.processor.generator;

import java.util.stream.Collectors;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import io.codegen.gwt.jsonoverlay.processor.model.JavaConvertMethod;
import io.codegen.gwt.jsonoverlay.processor.model.JavaCreateMethod;
import io.codegen.gwt.jsonoverlay.processor.model.JavaFactory;

public class OverlayFactoryGenerator {

    public static final String OVERLAY_SUFFIX = "_JSONOverlay";
    public static final String OVERLAY_FACTORY_SUFFIX = "_JSONOverlayFactory";

    private final String packageName;

    public OverlayFactoryGenerator(String packageName) {
        this.packageName = packageName;
    }

    public TypeSpec generateOverlay(JavaFactory javaFactory) {

        ClassName overlayName = ClassName.get(packageName, javaFactory.getType().simpleName() + OVERLAY_FACTORY_SUFFIX);

        TypeSpec.Builder typeSpec = TypeSpec.classBuilder(overlayName)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addSuperinterface(javaFactory.getType());

        typeSpec.addField(FieldSpec.builder(javaFactory.getType(), "INSTANCE")
                .addModifiers(Modifier.STATIC, Modifier.FINAL)
                .initializer("new $T()", overlayName)
                .build());

        typeSpec.addMethods(javaFactory.getConvertMethods().stream()
                .map(this::generateConvertMethod)
                .collect(Collectors.toList()));

        typeSpec.addMethods(javaFactory.getCreateMethods().stream()
                .map(this::generateCreateMethod)
                .collect(Collectors.toList()));

        typeSpec.addMethod(MethodSpec.methodBuilder("cast")
                .addAnnotation(AnnotationSpec.builder(SuppressWarnings.class)
                        .addMember("value", "$S", "unchecked")
                        .build())
                .addModifiers(Modifier.STATIC, Modifier.FINAL)
                .addParameter(Object.class, "object")
                .addTypeVariable(TypeVariableName.get("T"))
                .returns(TypeVariableName.get("T"))
                .addStatement("return (T) object")
                .build());

        return typeSpec.build();
    }

    private MethodSpec generateConvertMethod(JavaConvertMethod method) {
        OverlayConvertMethodGenerator generator =
                new OverlayConvertMethodGenerator(packageName, method.getMethodName(), method.getArgumentType());
        return method.getReturnType().accept(generator);
    }

    private MethodSpec generateCreateMethod(JavaCreateMethod method) {
        ClassName returnType = method.getReturnType();

        CodeBlock code = CodeBlock.builder()
                .addStatement("return $T.create()", ClassName.get(packageName, returnType.simpleName() + OVERLAY_SUFFIX))
                .build();

        return MethodSpec.methodBuilder(method.getMethodName())
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(returnType)
                .addCode(code)
                .build();
    }

}

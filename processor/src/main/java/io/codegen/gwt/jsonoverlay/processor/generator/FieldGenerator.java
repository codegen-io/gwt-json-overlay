package io.codegen.gwt.jsonoverlay.processor.generator;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.ParameterizedTypeName;
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

public class FieldGenerator implements JavaTypeVisitor<FieldSpec> {

    private final String packageName;

    private final String methodName;

    private final String propertyName;

    public FieldGenerator(String packageName, String methodName, String propertyName) {
        this.packageName = packageName;
        this.methodName = methodName;
        this.propertyName = propertyName;
    }

    @Override
    public FieldSpec visitPrimitiveType(PrimitiveType type) {
        return FieldSpec.builder(type.getPrimitiveType(), methodName)
                .addAnnotation(AnnotationSpec.builder(ClassNames.JSINTEROP_JSPROPERTY)
                    .addMember("name", "$S", propertyName)
                    .build())
                .build();
    }

    @Override
    public FieldSpec visitBoxedType(BoxedType type) {
        return FieldSpec.builder(type.getBoxedType(), methodName)
                .addAnnotation(AnnotationSpec.builder(ClassNames.JSINTEROP_JSPROPERTY)
                    .addMember("name", "$S", propertyName)
                    .build())
                .build();
    }

    @Override
    public FieldSpec visitStringType(StringType type) {
        return FieldSpec.builder(type.getStringType(), methodName)
                .addAnnotation(AnnotationSpec.builder(ClassNames.JSINTEROP_JSPROPERTY)
                    .addMember("name", "$S", propertyName)
                    .build())
                .build();
    }

    @Override
    public FieldSpec visitOptionalType(OptionalType type) {
        TypeName typeName = type.getElementType().accept(new FieldTypeResolver(packageName));
        return FieldSpec.builder(typeName, methodName)
                .addAnnotation(AnnotationSpec.builder(ClassNames.JSINTEROP_JSPROPERTY)
                    .addMember("name", "$S", propertyName)
                    .build())
                .build();
    }

    @Override
    public FieldSpec visitListType(ListType type) {
        TypeName typeName = type.getElementType().accept(new FieldTypeResolver(packageName));
        return FieldSpec.builder(typeName, methodName)
                .addAnnotation(AnnotationSpec.builder(ClassNames.JSINTEROP_JSPROPERTY)
                    .addMember("name", "$S", propertyName)
                    .build())
                .build();
    }

    @Override
    public FieldSpec visitMapType(MapType type) {
        TypeName typeName = type.getValueType().accept(new FieldTypeResolver(packageName));
        return FieldSpec.builder(ParameterizedTypeName.get(ClassNames.JSINTEROP_BASE_JSPROPERTYMAP, typeName), methodName)
                .addAnnotation(AnnotationSpec.builder(ClassNames.JSINTEROP_JSPROPERTY)
                    .addMember("name", "$S", propertyName)
                    .build())
                .build();
    }

    @Override
    public FieldSpec visitOverlayType(OverlayType type) {
        ClassName name = ClassName.get(packageName, type.getOverlayType().simpleName() + "_JSONOverlay").nestedClass("JsObject");
        return FieldSpec.builder(name, methodName)
                .addAnnotation(AnnotationSpec.builder(ClassNames.JSINTEROP_JSPROPERTY)
                    .addMember("name", "$S", propertyName)
                    .build())
                .build();
    }

    @Override
    public FieldSpec visitEnumType(EnumType type) {
        throw new UnsupportedOperationException();
    }

    @Override
    public FieldSpec visitInheritedType(InheritedType type) {
        throw new UnsupportedOperationException();
    }

    @Override
    public FieldSpec visitSubType(SubType type) {
        throw new UnsupportedOperationException();
    }

    @Override
    public FieldSpec visitJavaScriptObjectType(JavaScriptObjectType type) {
        throw new UnsupportedOperationException();
    }

}

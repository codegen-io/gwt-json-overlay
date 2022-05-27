package io.codegen.gwt.jsonoverlay.processor.generator;

import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
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

public class FieldTypeResolver implements JavaTypeVisitor<TypeName> {

    private final String packageName;

    public FieldTypeResolver(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public TypeName visitPrimitiveType(PrimitiveType type) {
        if (TypeName.LONG.equals(type.getPrimitiveType())) {
            return TypeName.DOUBLE;
        }
        return type.getPrimitiveType();
    }

    @Override
    public TypeName visitBoxedType(BoxedType type) {
        return type.getBoxedType();
    }

    @Override
    public TypeName visitStringType(StringType type) {
        return ClassName.get(String.class);
    }

    @Override
    public TypeName visitOptionalType(OptionalType type) {
        return type.getElementType().accept(new FieldTypeResolver(packageName));
    }

    @Override
    public TypeName visitListType(ListType type) {
        return ArrayTypeName.of(type.getElementType().accept(new FieldTypeResolver(packageName)));
    }

    @Override
    public TypeName visitMapType(MapType type) {
        return ParameterizedTypeName.get(ClassNames.JSINTEROP_BASE_JSPROPERTYMAP,
                type.getValueType().accept(new FieldTypeResolver(packageName)));
    }

    @Override
    public TypeName visitDateType(DateType type) {
        return type.getDateType();
    }

    @Override
    public TypeName visitOverlayType(OverlayType type) {
        return ClassName.get(packageName, type.getOverlayType().simpleName() + "_JSONOverlay").nestedClass("JsObject");
    }

    @Override
    public TypeName visitEnumType(EnumType type) {
        return ClassName.get(String.class);
    }

    @Override
    public TypeName visitInheritedType(InheritedType type) {
        return ClassName.get(packageName, type.getSuperType().simpleName() + "_JSONOverlay").nestedClass("JsObject");
    }

    @Override
    public TypeName visitSubType(SubType type) {
        return ClassName.get(packageName, type.getSubType().simpleName() + "_JSONOverlay").nestedClass("JsObject");
    }

    @Override
    public TypeName visitJavaScriptObjectType(JavaScriptObjectType type) {
        return type.getJavaScriptObjectType();
    }

}

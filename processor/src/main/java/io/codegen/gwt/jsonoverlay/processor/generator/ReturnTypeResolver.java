package io.codegen.gwt.jsonoverlay.processor.generator;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

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

public class ReturnTypeResolver implements JavaTypeVisitor<TypeName> {

    @Override
    public TypeName visitPrimitiveType(PrimitiveType type) {
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
        TypeName element = type.getElementType().accept(new ReturnTypeResolver());
        return ParameterizedTypeName.get(ClassName.get(Optional.class), element);
    }

    @Override
    public TypeName visitListType(ListType type) {
        TypeName element = type.getElementType().accept(new ReturnTypeResolver());
        return ParameterizedTypeName.get(ClassName.get(List.class), element);
    }

    @Override
    public TypeName visitMapType(MapType type) {
        TypeName element = type.getValueType().accept(new ReturnTypeResolver());
        return ParameterizedTypeName.get(ClassName.get(Map.class), ClassName.get(String.class), element);
    }

    @Override
    public TypeName visitOverlayType(OverlayType type) {
        return type.getOverlayType();
    }

    @Override
    public TypeName visitEnumType(EnumType type) {
        return type.getEnumType();
    }

    @Override
    public TypeName visitInheritedType(InheritedType type) {
        return type.getSuperType();
    }

    @Override
    public TypeName visitSubType(SubType type) {
        return type.getSubType();
    }

}

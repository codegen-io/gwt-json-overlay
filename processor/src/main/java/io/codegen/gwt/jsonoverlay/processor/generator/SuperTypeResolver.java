package io.codegen.gwt.jsonoverlay.processor.generator;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.squareup.javapoet.ClassName;

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

public class SuperTypeResolver implements JavaTypeVisitor<ClassName> {

    @Override
    public ClassName visitPrimitiveType(PrimitiveType type) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ClassName visitBoxedType(BoxedType type) {
        return type.getBoxedType();
    }

    @Override
    public ClassName visitStringType(StringType type) {
        return ClassName.get(String.class);
    }

    @Override
    public ClassName visitOptionalType(OptionalType type) {
        return ClassName.get(Optional.class);
    }

    @Override
    public ClassName visitListType(ListType type) {
        return ClassName.get(List.class);
    }

    @Override
    public ClassName visitMapType(MapType type) {
        return ClassName.get(Map.class);
    }

    @Override
    public ClassName visitOverlayType(OverlayType type) {
        return type.getOverlayType();
    }

    @Override
    public ClassName visitEnumType(EnumType type) {
        return type.getEnumType();
    }

    @Override
    public ClassName visitInheritedType(InheritedType type) {
        return type.getSuperType();
    }

    @Override
    public ClassName visitSubType(SubType type) {
        return type.getSubType();
    }

    @Override
    public ClassName visitJavaScriptObjectType(JavaScriptObjectType type) {
        throw new UnsupportedOperationException();
    }

}

package io.codegen.gwt.jsonoverlay.processor.generator;

import com.squareup.javapoet.ClassName;

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

public class JsObjectSuperTypeResolver implements JavaTypeVisitor<ClassName> {

    private final String packageName;

    public JsObjectSuperTypeResolver(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public ClassName visitPrimitiveType(PrimitiveType type) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ClassName visitBoxedType(BoxedType type) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ClassName visitStringType(StringType type) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ClassName visitOptionalType(OptionalType type) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ClassName visitListType(ListType type) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ClassName visitMapType(MapType type) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ClassName visitOverlayType(OverlayType type) {
        return ClassName.OBJECT;
    }

    @Override
    public ClassName visitEnumType(EnumType type) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ClassName visitInheritedType(InheritedType type) {
        return ClassName.OBJECT;
    }

    @Override
    public ClassName visitSubType(SubType type) {
        return ClassName.get(packageName, type.getSuperType().simpleName() + OverlayTypeResolver.OVERLAY_SUFFIX).nestedClass("JsObject");
    }

}

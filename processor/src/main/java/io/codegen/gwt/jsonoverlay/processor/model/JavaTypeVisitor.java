package io.codegen.gwt.jsonoverlay.processor.model;

import io.codegen.gwt.jsonoverlay.processor.model.types.BoxedType;
import io.codegen.gwt.jsonoverlay.processor.model.types.ClassType;
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

public interface JavaTypeVisitor<R> {

    R visitPrimitiveType(PrimitiveType type);

    R visitBoxedType(BoxedType type);

    R visitStringType(StringType type);

    R visitOptionalType(OptionalType type);

    R visitListType(ListType type);

    R visitMapType(MapType type);

    R visitOverlayType(OverlayType type);

    default R visitClassType(ClassType type) {
        return visitOverlayType(OverlayType.builder()
                .overlayType(type.getClassType())
                .build());
    }

    R visitDateType(DateType type);

    R visitEnumType(EnumType type);

    R visitInheritedType(InheritedType type);

    R visitSubType(SubType type);

    R visitJavaScriptObjectType(JavaScriptObjectType type);

}

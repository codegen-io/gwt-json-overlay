package io.codegen.gwt.jsonoverlay.processor.model;

public interface JavaType {

    enum Kind {
        PRIMITIVE,
        BOXED,
        STRING,
        OPTIONAL,
        LIST,
        MAP,
        OVERLAY,
        INHERITED,
        SUB,
        JAVASCRIPT_OBJECT,
    }

    Kind getKind();

    <T> T accept(JavaTypeVisitor<T> visitor);

}

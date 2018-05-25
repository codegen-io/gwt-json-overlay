package io.codegen.gwt.jsonoverlay.processor.model;

public interface JavaType {

    enum Kind {
        PRIMITIVE,
        BOXED,
        STRING,
        OPTIONAL,
        LIST,
        OVERLAY,
        INHERITED,
        SUB,
    }

    Kind getKind();

    <T> T accept(JavaTypeVisitor<T> visitor);

}

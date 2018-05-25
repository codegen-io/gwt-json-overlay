package io.codegen.gwt.jsonoverlay.processor;

import com.squareup.javapoet.ClassName;

public class ClassNames {

    private static final String JSINTEROP_PACKAGE = "jsinterop.annotations";
    private static final String JSONOVERLAY_ANNOTATIONS_PACKAGE = "io.codegen.gwt.jsonoverlay.annotations";
    private static final String JSONOVERLAY_RUNTIME_PACKAGE = "io.codegen.gwt.jsonoverlay.runtime";
    private static final String JACKSON_PACKAGE = "com.fasterxml.jackson.annotation";

    public static final ClassName JSINTEROP_JSTYPE = ClassName.get(JSINTEROP_PACKAGE, "JsType");

    public static final ClassName JSINTEROP_JSIGNORE = ClassName.get(JSINTEROP_PACKAGE, "JsIgnore");

    public static final ClassName JSINTEROP_JSMETHOD = ClassName.get(JSINTEROP_PACKAGE, "JsMethod");

    public static final ClassName JSINTEROP_JSFUNCTION = ClassName.get(JSINTEROP_PACKAGE, "JsFunction");

    public static final ClassName JSINTEROP_JSPROPERTY = ClassName.get(JSINTEROP_PACKAGE, "JsProperty");

    public static final ClassName JSINTEROP_JSOVERLAY = ClassName.get(JSINTEROP_PACKAGE, "JsOverlay");

    public static final ClassName JSINTEROP_JSPACKAGE = ClassName.get(JSINTEROP_PACKAGE, "JsPackage");

    public static final ClassName GWT_SHARED_HELPER = ClassName.get("com.google.gwt.core.shared", "GWT");

    public static final ClassName GWT_INCOMPATIBLE = ClassName.get("com.google.gwt.core.shared", "GwtIncompatible");

    public static final ClassName JSON_OVERLAY_ANNOTATION = ClassName.get(JSONOVERLAY_ANNOTATIONS_PACKAGE, "JsonOverlay");

    public static final ClassName JSON_FACTORY = ClassName.get(JSONOVERLAY_RUNTIME_PACKAGE, "JsonFactory");

    public static final ClassName JSON_PARSER = ClassName.get(JSONOVERLAY_RUNTIME_PACKAGE, "JsonParser");

    public static final ClassName JSON_SERIALIZER = ClassName.get(JSONOVERLAY_RUNTIME_PACKAGE, "JsonSerializer");

    public static final ClassName JACKSON_JSONSUBTYPES = ClassName.get(JACKSON_PACKAGE, "JsonSubTypes");

    public static final ClassName JACKSON_JSONSUBTYPES_TYPE = JACKSON_JSONSUBTYPES.nestedClass("Type");

    public static final ClassName JACKSON_JSONTYPEINFO = ClassName.get(JACKSON_PACKAGE, "JsonTypeInfo");

}

package io.codegen.gwt.jsonoverlay.runtime.gwt;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "JSON")
public final class Json {

    public static native String stringify(Object object);

    public static native <T> T parse(String json);

}

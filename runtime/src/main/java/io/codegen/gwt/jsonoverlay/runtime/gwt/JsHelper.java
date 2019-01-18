package io.codegen.gwt.jsonoverlay.runtime.gwt;

import jsinterop.annotations.JsProperty;

public class JsHelper {

    @JsProperty(namespace = "<window>", name = "undefined")
    public static native Object undefinedObject();

    @JsProperty(namespace = "<window>", name = "undefined")
    public static native boolean undefinedBoolean();

    @JsProperty(namespace = "<window>", name = "undefined")
    public static native int undefinedInt();

}

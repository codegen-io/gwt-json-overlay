package io.codegen.gwt.jsonoverlay.integration;

import com.google.gwt.core.client.JavaScriptObject;

import io.codegen.gwt.jsonoverlay.annotations.JsonOverlayFactory;

@JsonOverlayFactory
public interface MyFactory {
    public MyFactory INSTANCE = MyFactory_JSONOverlayFactory.INSTANCE;

    MyInterface fromJSO(JavaScriptObject jso);

}

package io.codegen.gwt.jsonoverlay.runtime;

public interface JsonParser<T> {

    T fromJSON(String json);

}

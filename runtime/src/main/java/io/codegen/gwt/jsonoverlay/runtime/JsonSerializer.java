package io.codegen.gwt.jsonoverlay.runtime;

public interface JsonSerializer<T> {

    String toJSON(T object);

}

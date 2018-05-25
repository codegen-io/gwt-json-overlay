package io.codegen.gwt.jsonoverlay.runtime.gwt;

import java.util.function.Function;

import io.codegen.gwt.jsonoverlay.runtime.JsonParser;

public class GwtJsonParser<T> implements JsonParser<T> {

    private final Function<?, T> wrapper;

    public GwtJsonParser(Function<?, T> wrapper) {
        this.wrapper = wrapper;
    }

    @Override
    public T fromJSON(String json) {
        return this.wrapper.apply(Json.parse(json));
    }

}

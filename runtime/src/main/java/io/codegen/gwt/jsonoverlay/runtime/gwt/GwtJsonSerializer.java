package io.codegen.gwt.jsonoverlay.runtime.gwt;

import java.util.function.Function;

import io.codegen.gwt.jsonoverlay.runtime.JsonSerializer;

public class GwtJsonSerializer<T> implements JsonSerializer<T> {

    private final Function<T, ?> unwrapper;

    public GwtJsonSerializer(Function<T, ?> unwrapper) {
        this.unwrapper = unwrapper;
    }

    @Override
    public String toJSON(T object) {
        return Json.stringify(unwrapper.apply(object));
    }

}

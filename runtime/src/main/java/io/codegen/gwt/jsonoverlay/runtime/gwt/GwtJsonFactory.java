package io.codegen.gwt.jsonoverlay.runtime.gwt;

import java.util.List;
import java.util.function.Function;

import io.codegen.gwt.jsonoverlay.runtime.JsonFactory;
import io.codegen.gwt.jsonoverlay.runtime.JsonParser;
import io.codegen.gwt.jsonoverlay.runtime.JsonSerializer;

public class GwtJsonFactory implements JsonFactory {

    @Override
    public <T> JsonSerializer<T> createSerializer(Function<T, ?> unwrapper) {
        return new GwtJsonSerializer<>(unwrapper);
    }

    @Override
    public <T> JsonParser<T> createParser(Function<?, T> wrapper) {
        return new GwtJsonParser<>(wrapper);
    }

    @Override
    public <T> List<T> wrapArray(T[] values) {
        return new JsArrayList<>(values);
    }

}

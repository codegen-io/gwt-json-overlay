package io.codegen.gwt.jsonoverlay.runtime.jre;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.codegen.gwt.jsonoverlay.runtime.JsonFactory;
import io.codegen.gwt.jsonoverlay.runtime.JsonParser;
import io.codegen.gwt.jsonoverlay.runtime.JsonSerializer;

@GwtIncompatible
public class JreJsonFactory implements JsonFactory {

    @Override
    public <T> JsonSerializer<T> createSerializer(Function<T, ?> unwrapper) {
        return new JreJsonSerializer<>(unwrapper);
    }

    @Override
    public <T> JsonParser<T> createParser(Function<?, T> wrapper) {
        return new JreJsonParser<>(wrapper);
    }

    @Override
    public <T> List<T> wrapArray(T[] values) {
        return Stream.of(values).collect(Collectors.toList());
    }

}

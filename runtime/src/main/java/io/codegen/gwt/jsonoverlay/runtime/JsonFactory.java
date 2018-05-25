package io.codegen.gwt.jsonoverlay.runtime;

import java.util.List;
import java.util.function.Function;

public interface JsonFactory {

    <T> JsonSerializer<T> createSerializer(Function<T, ?> unwrapper);

    <T> JsonParser<T> createParser(Function<?, T> wrapper);

    <T> List<T> wrapArray(T[] values);

}

package io.codegen.gwt.jsonoverlay.runtime.jre;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.json.JSONObject;

import io.codegen.gwt.jsonoverlay.runtime.JsonParser;
import jsinterop.annotations.JsProperty;

@GwtIncompatible
public class JreJsonParser<T> implements JsonParser<T> {

    private static final Pattern ENCLOSING_TYPE_PATTERN = Pattern.compile("(.*)\\$\\$Lambda\\$.*?");

    private final Supplier<?> jsTypeSupplier;

    private final Function<?, T> wrapper;

    public JreJsonParser(Function<?, T> wrapper) {
        this.wrapper = wrapper;
        this.jsTypeSupplier = createJsTypeSupplier(wrapper);
    }

    @Override
    public T fromJSON(String json) {
        return wrapper.apply(parse(json));
    }

    @SuppressWarnings("unchecked")
    private <V> V parse(String json) {
        Object jsObject = jsTypeSupplier.get();

        JSONObject jsonObject = new JSONObject(json);
        for (String key : jsonObject.keySet()) {
            setProperty(jsObject, key, jsonObject.get(key));
        }

        return (V) jsObject;
    }

    private static Supplier<?> createJsTypeSupplier(Function<?, ?> wrapper) {
        Matcher matcher = ENCLOSING_TYPE_PATTERN.matcher(wrapper.getClass().getCanonicalName());
        if (matcher.matches()) {
            Class<?> wrapperType;
            try {
                wrapperType = wrapper.getClass().getClassLoader().loadClass(matcher.group(1));
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException(e);
            }

            Constructor<?> wrapperConstructor = Stream.of(wrapperType.getDeclaredConstructors())
                    .filter(constructor -> constructor.getParameterCount() == 1)
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Unable to find single argument constructor of " + wrapperType));

            Constructor<?> parameterConstructor = Stream
                    .of(wrapperConstructor.getParameters()[0].getType().getDeclaredConstructors())
                    .filter(constructor -> constructor.getParameterCount() == 0)
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Unable to find no-argument constructor of " + wrapperType));

            parameterConstructor.setAccessible(true);

            return () -> {
                try {
                    return parameterConstructor.newInstance(new Object[0]);
                } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    throw new IllegalStateException(e);
                }
            };
        } else {
            throw new IllegalStateException("Unable to determine enclosing class for lambda " + wrapper);
        }
    }

    private static void setProperty(Object object, String name, Object value) {
        Field property = Stream.of(object.getClass().getDeclaredFields())
                .filter(field -> name.equals(field.getAnnotation(JsProperty.class).name()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        "Unable to find property '" + name + "' in type '" + object.getClass().getCanonicalName() + "'"));

        try {
            property.setAccessible(true);
            property.set(object, convert(property.getType(), value));
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new IllegalStateException(
                    "Unable to set property '" + name + "' in type '" + object.getClass().getCanonicalName() + "'", e);
        }
    }

    private static Object convert(Class<?> type, Object value) {
        if (value == null) {
            return null;
        }

        if (Integer.class.equals(type) || Integer.TYPE.equals(type)) {
            return Integer.valueOf(value.toString());
        }

        return value;
    }
}

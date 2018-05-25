package io.codegen.gwt.jsonoverlay.runtime.jre;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Function;

import io.codegen.gwt.jsonoverlay.runtime.JsonSerializer;
import jsinterop.annotations.JsIgnore;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@GwtIncompatible
public class JreJsonSerializer<T> implements JsonSerializer<T> {

    private final Function<T, ?> unwrapper;

    public JreJsonSerializer(Function<T, ?> unwrapper) {
        this.unwrapper = unwrapper;
    }

    @Override
    public final String toJSON(T object) {
        Object value = unwrapper.apply(object);

        if (!isJsObject(value.getClass())) {
            throw new IllegalStateException("Class " + value.getClass() + " isn't a JavaScript object");
        }
        StringWriter writer = new StringWriter();
        try {
            writeJSON(writer, value);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return writer.toString();
    }

    private final boolean isJsObject(Class<?> type) {
        JsType jsType = type.getAnnotation(JsType.class);
        if (jsType == null) {
            return false;
        }
        if (!jsType.isNative()) {
            return false;
        }
        if (!JsPackage.GLOBAL.equals(jsType.namespace())) {
            return false;
        }
        if (!"Object".equals(jsType.name())) {
            return false;
        }
        return true;
    }

    private final void writeJSON(Writer writer, Object value) throws IOException {
        if (value == null) {
            writer.append("null");
            return;
        }

        Class<?> type = value.getClass();
        if (type.isPrimitive()) {
            writer.append(String.valueOf(value));
        } else if (type.isArray()) {
            writer.append('[');
            int arraySize = Array.getLength(value);
            for (int arrayIndex = 0; arrayIndex < arraySize; arrayIndex++) {
                writeJSON(writer, Array.get(value, arrayIndex));

                if (arrayIndex < (arraySize - 1)) {
                    writer.append(',');
                }
            }
            writer.append(']');
        } else if (Number.class.isAssignableFrom(type)) {
            writer.append(String.valueOf(value));
        } else if (Boolean.class.isAssignableFrom(type)) {
            writer.append(String.valueOf(value));
        } else if (CharSequence.class.isAssignableFrom(type)) {
            writer.append('"');
            writeValue(writer, String.valueOf(value));
            writer.append('"');
        } else if (isJsObject(type)) {
            writer.append('{');

            Field[] fields = type.getDeclaredFields();

            SortedMap<String, Field> fieldByName = new TreeMap<>();

            for (int fieldIndex = 0; fieldIndex < fields.length; fieldIndex++) {
                Field field = fields[fieldIndex];

                if (field.getAnnotation(JsIgnore.class) != null) {
                    continue;
                }
                if (field.getAnnotation(JsOverlay.class) != null) {
                    continue;
                }

                String name;
                JsProperty property = field.getAnnotation(JsProperty.class);
                if (property != null && !"<auto>".equals(property.name())) {
                    name = property.name();
                } else {
                    name = field.getName();
                }

                fieldByName.put(name, field);
            }

            boolean firstProperty = true;
            for (Map.Entry<String, Field> entry : fieldByName.entrySet()) {
                if (!firstProperty) {
                    writer.append(',');
                }

                writer.append('"');
                writeValue(writer, entry.getKey());
                writer.append('"');
                writer.append(':');

                try {
                    writeJSON(writer, entry.getValue().get(value));
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }

                firstProperty = false;
            }

            writer.append('}');
        } else {
            throw new IllegalArgumentException("Unknown type " + type);
        }
    }

    private static final void writeValue(Writer writer, CharSequence value) throws IOException {
        for (int index = 0; index < value.length(); index++) {
            char currentCharacter = value.charAt(index);
            switch (currentCharacter) {
                case '\\':
                    writer.write("\\\\");
                    break;
                case '\"':
                    writer.write("\\\"");
                    break;
                case '\b':
                    writer.write("\\b");
                    break;
                case '\t':
                    writer.write("\\t");
                    break;
                case '\n':
                    writer.write("\\n");
                    break;
                case '\f':
                    writer.write("\\f");
                    break;
                case '\r':
                    writer.write("\\r");
                    break;
                default:
                    if ((currentCharacter >= '\u0000' && currentCharacter <= '\u001f')
                            || (currentCharacter >= '\u007f' && currentCharacter <= '\u009f')
                            || (currentCharacter >= '\u2028' && currentCharacter <= '\u2029')) {
                        writer.write("\\u");
                        String hexValue = Integer.toHexString(currentCharacter);
                        writer.write("0000", 0, 4 - hexValue.length());
                        writer.write(hexValue);
                    } else {
                        writer.write(currentCharacter);
                    }
            }
        }
    }

}

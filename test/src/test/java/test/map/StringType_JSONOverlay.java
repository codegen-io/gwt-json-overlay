package test.map;

import static io.codegen.gwt.jsonoverlay.runtime.gwt.JsHelper.*;

import io.codegen.gwt.jsonoverlay.runtime.JsonFactory;

import java.util.Objects;
import java.util.function.Function;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import test.StringType;

public final class StringType_JSONOverlay implements StringType {
  public static final Function<JsObject, StringType> WRAPPER = StringType_JSONOverlay::wrap;

  public static final Function<StringType, JsObject> UNWRAPPER = StringType_JSONOverlay::unwrap;

  private final JsObject object;

  protected StringType_JSONOverlay() {
    this.object = new JsObject();
  }

  protected StringType_JSONOverlay(JsObject object) {
    this.object = object;
  }

  @Override
  public String getString() {
    return object.getString;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof StringType_JSONOverlay)) return false;
    StringType_JSONOverlay that = (StringType_JSONOverlay) o;
    return object.equals(that.object);
  }

  @Override
  public int hashCode() {
    return Objects.hash(object);
  }

  public static StringType parse(String json, JsonFactory factory) {
    return factory.createParser(StringType_JSONOverlay.WRAPPER).fromJSON(json);
  }

  public static String serialize(StringType object, JsonFactory factory) {
    return factory.createSerializer(StringType_JSONOverlay.UNWRAPPER).toJSON(object);
  }

  public static StringType create() {
    JsObject object = new JsObject();
    object.getString = null;
    return wrap(object);
  }

  public static StringType wrap(Object object) {
    if (object instanceof JsObject) {
      return new StringType_JSONOverlay((JsObject) object);
    }
    throw new IllegalArgumentException("Object '" + object + "' isn't of type test.map.StringType_JSONOverlay.JsObject");
  }

  public static JsObject unwrap(StringType wrapper) {
    if (wrapper instanceof StringType_JSONOverlay) {
      return ((StringType_JSONOverlay) wrapper).object;
    }
    JsObject object = new JsObject();
    object.getString = wrapper.getString();
    return object;
  }

  @JsType(
      isNative = true,
      namespace = JsPackage.GLOBAL,
      name = "Object"
  )
  static class JsObject {
    @JsProperty(
        name = "string"
    )
    String getString;
  }
}

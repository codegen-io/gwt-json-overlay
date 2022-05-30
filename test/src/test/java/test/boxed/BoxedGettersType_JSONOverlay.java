package test.boxed;

import static io.codegen.gwt.jsonoverlay.runtime.gwt.JsHelper.*;

import io.codegen.gwt.jsonoverlay.runtime.JsonFactory;

import java.util.Objects;
import java.util.function.Function;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
import test.BoxedGettersType;

public final class BoxedGettersType_JSONOverlay implements BoxedGettersType {
  public static final Function<JsObject, BoxedGettersType> WRAPPER = BoxedGettersType_JSONOverlay::wrap;

  public static final Function<BoxedGettersType, JsObject> UNWRAPPER = BoxedGettersType_JSONOverlay::unwrap;

  private final JsObject object;

  protected BoxedGettersType_JSONOverlay() {
    this.object = new JsObject();
  }

  protected BoxedGettersType_JSONOverlay(JsObject object) {
    this.object = object;
  }

  @Override
  public Boolean getBoolean() {
    return object.getBoolean == undefinedObject() ? null : Boolean.valueOf(Js.asBoolean(object.getBoolean));
  }

  @Override
  public Byte getByte() {
    return object.getByte == undefinedObject() ? null : Byte.valueOf(object.getByte.byteValue());
  }

  @Override
  public Short getShort() {
    return object.getShort == undefinedObject() ? null : Short.valueOf(object.getShort.shortValue());
  }

  @Override
  public Integer getInt() {
    return object.getInt == undefinedObject() ? null : Integer.valueOf(object.getInt.intValue());
  }

  @Override
  public Long getLong() {
    return object.getLong == undefinedObject() ? null : Long.valueOf(object.getLong.longValue());
  }

  @Override
  public Character getChar() {
    return object.getChar == undefinedObject() ? null : Character.valueOf((char) object.getChar.intValue());
  }

  @Override
  public Float getFloat() {
    return object.getFloat == undefinedObject() ? null : Float.valueOf(object.getFloat.floatValue());
  }

  @Override
  public Double getDouble() {
    return object.getDouble == undefinedObject() ? null : Double.valueOf(object.getDouble.doubleValue());
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof BoxedGettersType_JSONOverlay)) return false;
    BoxedGettersType_JSONOverlay that = (BoxedGettersType_JSONOverlay) o;
    return object.equals(that.object);
  }

  @Override
  public int hashCode() {
    return Objects.hash(object);
  }

  public static BoxedGettersType parse(String json, JsonFactory factory) {
    return factory.createParser(BoxedGettersType_JSONOverlay.WRAPPER).fromJSON(json);
  }

  public static String serialize(BoxedGettersType object, JsonFactory factory) {
    return factory.createSerializer(BoxedGettersType_JSONOverlay.UNWRAPPER).toJSON(object);
  }

  public static BoxedGettersType create() {
    JsObject object = new JsObject();
    object.getBoolean = null;
    object.getByte = null;
    object.getShort = null;
    object.getInt = null;
    object.getLong = null;
    object.getChar = null;
    object.getFloat = null;
    object.getDouble = null;
    return wrap(object);
  }

  public static BoxedGettersType wrap(Object object) {
    if (object instanceof JsObject) {
      return new BoxedGettersType_JSONOverlay((JsObject) object);
    }
    throw new IllegalArgumentException("Object '" + object + "' isn't of type test.boxed.BoxedGettersType_JSONOverlay.JsObject");
  }

  public static JsObject unwrap(BoxedGettersType wrapper) {
    if (wrapper instanceof BoxedGettersType_JSONOverlay) {
      return ((BoxedGettersType_JSONOverlay) wrapper).object;
    }
    JsObject object = new JsObject();
    object.getBoolean = wrapper.getBoolean();
    object.getByte = wrapper.getByte() == null ? null : Double.valueOf(wrapper.getByte().byteValue());
    object.getShort = wrapper.getShort() == null ? null : Double.valueOf(wrapper.getShort().shortValue());
    object.getInt = wrapper.getInt() == null ? null : Double.valueOf(wrapper.getInt().intValue());
    object.getLong = wrapper.getLong() == null ? null : Double.valueOf(wrapper.getLong().longValue());
    object.getChar = wrapper.getChar() == null ? null : Double.valueOf(wrapper.getChar().charValue());
    object.getFloat = wrapper.getFloat() == null ? null : Double.valueOf(wrapper.getFloat().floatValue());
    object.getDouble = wrapper.getDouble() == null ? null : Double.valueOf(wrapper.getDouble().doubleValue());
    return object;
  }

  @JsType(
      isNative = true,
      namespace = JsPackage.GLOBAL,
      name = "Object"
  )
  static class JsObject {
    @JsProperty(
        name = "boolean"
    )
    Boolean getBoolean;

    @JsProperty(
        name = "byte"
    )
    Double getByte;

    @JsProperty(
        name = "short"
    )
    Double getShort;

    @JsProperty(
        name = "int"
    )
    Double getInt;

    @JsProperty(
        name = "long"
    )
    Double getLong;

    @JsProperty(
        name = "char"
    )
    Double getChar;

    @JsProperty(
        name = "float"
    )
    Double getFloat;

    @JsProperty(
        name = "double"
    )
    Double getDouble;
  }
}

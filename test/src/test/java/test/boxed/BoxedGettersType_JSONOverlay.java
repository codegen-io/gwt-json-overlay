package test.boxed;

import io.codegen.gwt.jsonoverlay.runtime.JsonFactory;
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
    return object.getBoolean == Js.undefined() ? null : Boolean.valueOf(Js.asBoolean(object.getBoolean));
  }

  @Override
  public Byte getByte() {
    return object.getByte == Js.undefined() ? null : Byte.valueOf(Js.asByte(object.getByte));
  }

  @Override
  public Short getShort() {
    return object.getShort == Js.undefined() ? null : Short.valueOf(Js.asShort(object.getShort));
  }

  @Override
  public Integer getInt() {
    return object.getInt == Js.undefined() ? null : Integer.valueOf(Js.asInt(object.getInt));
  }

  @Override
  public Long getLong() {
    return object.getLong == Js.undefined() ? null : Long.valueOf(Js.asLong(object.getLong));
  }

  @Override
  public Character getChar() {
    return object.getChar == Js.undefined() ? null : Character.valueOf(Js.asChar(object.getChar));
  }

  @Override
  public Float getFloat() {
    return object.getFloat == Js.undefined() ? null : Float.valueOf(Js.asFloat(object.getFloat));
  }

  @Override
  public Double getDouble() {
    return object.getDouble == Js.undefined() ? null : Double.valueOf(Js.asDouble(object.getDouble));
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
    object.getByte = wrapper.getByte();
    object.getShort = wrapper.getShort();
    object.getInt = wrapper.getInt();
    object.getLong = wrapper.getLong();
    object.getChar = wrapper.getChar();
    object.getFloat = wrapper.getFloat();
    object.getDouble = wrapper.getDouble();
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
    Byte getByte;

    @JsProperty(
        name = "short"
    )
    Short getShort;

    @JsProperty(
        name = "int"
    )
    Integer getInt;

    @JsProperty(
        name = "long"
    )
    Long getLong;

    @JsProperty(
        name = "char"
    )
    Character getChar;

    @JsProperty(
        name = "float"
    )
    Float getFloat;

    @JsProperty(
        name = "double"
    )
    Double getDouble;
  }
}

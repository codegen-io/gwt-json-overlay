package test.boxed;

import static io.codegen.gwt.jsonoverlay.runtime.gwt.JsHelper.*;

import io.codegen.gwt.jsonoverlay.runtime.JsonFactory;
import java.util.function.Function;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
import test.BoxedSettersType;

public final class BoxedSettersType_JSONOverlay implements BoxedSettersType {
  public static final Function<JsObject, BoxedSettersType> WRAPPER = BoxedSettersType_JSONOverlay::wrap;

  public static final Function<BoxedSettersType, JsObject> UNWRAPPER = BoxedSettersType_JSONOverlay::unwrap;

  private final JsObject object;

  protected BoxedSettersType_JSONOverlay() {
    this.object = new JsObject();
  }

  protected BoxedSettersType_JSONOverlay(JsObject object) {
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
  public void setBoolean(Boolean value) {
    object.getBoolean = value;
  }

  @Override
  public void setByte(Byte value) {
    object.getByte = value == null ? null : Double.valueOf(value.byteValue());
  }

  @Override
  public void setShort(Short value) {
    object.getShort = value == null ? null : Double.valueOf(value.shortValue());
  }

  @Override
  public void setInt(Integer value) {
    object.getInt = value == null ? null : Double.valueOf(value.intValue());
  }

  @Override
  public void setLong(Long value) {
    object.getLong = value == null ? null : Double.valueOf(value.longValue());
  }

  @Override
  public void setChar(Character value) {
    object.getChar = value == null ? null : Double.valueOf(value.charValue());
  }

  @Override
  public void setFloat(Float value) {
    object.getFloat = value == null ? null : Double.valueOf(value.floatValue());
  }

  @Override
  public void setDouble(Double value) {
    object.getDouble = value == null ? null : Double.valueOf(value.doubleValue());
  }

  public static BoxedSettersType parse(String json, JsonFactory factory) {
    return factory.createParser(BoxedSettersType_JSONOverlay.WRAPPER).fromJSON(json);
  }

  public static String serialize(BoxedSettersType object, JsonFactory factory) {
    return factory.createSerializer(BoxedSettersType_JSONOverlay.UNWRAPPER).toJSON(object);
  }

  public static BoxedSettersType create() {
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

  public static BoxedSettersType wrap(Object object) {
    if (object instanceof JsObject) {
      return new BoxedSettersType_JSONOverlay((JsObject) object);
    }
    throw new IllegalArgumentException("Object '" + object + "' isn't of type test.boxed.BoxedSettersType_JSONOverlay.JsObject");
  }

  public static JsObject unwrap(BoxedSettersType wrapper) {
    if (wrapper instanceof BoxedSettersType_JSONOverlay) {
      return ((BoxedSettersType_JSONOverlay) wrapper).object;
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

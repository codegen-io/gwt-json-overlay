package test.primitives;

import io.codegen.gwt.jsonoverlay.runtime.JsonFactory;
import java.util.function.Function;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import test.PrimitiveGettersType;

public final class PrimitiveGettersType_JSONOverlay implements PrimitiveGettersType {
  public static final Function<JsObject, PrimitiveGettersType> WRAPPER = PrimitiveGettersType_JSONOverlay::wrap;

  public static final Function<PrimitiveGettersType, JsObject> UNWRAPPER = PrimitiveGettersType_JSONOverlay::unwrap;

  private final JsObject object;

  protected PrimitiveGettersType_JSONOverlay() {
    this.object = new JsObject();
  }

  protected PrimitiveGettersType_JSONOverlay(JsObject object) {
    this.object = object;
  }

  @Override
  public boolean getBoolean() {
    return object.getBoolean;
  }

  @Override
  public byte getByte() {
    return object.getByte;
  }

  @Override
  public short getShort() {
    return object.getShort;
  }

  @Override
  public int getInt() {
    return object.getInt;
  }

  @Override
  public long getLong() {
    return object.getLong;
  }

  @Override
  public char getChar() {
    return object.getChar;
  }

  @Override
  public float getFloat() {
    return object.getFloat;
  }

  @Override
  public double getDouble() {
    return object.getDouble;
  }

  public static PrimitiveGettersType parse(String json, JsonFactory factory) {
    return factory.createParser(PrimitiveGettersType_JSONOverlay.WRAPPER).fromJSON(json);
  }

  public static String serialize(PrimitiveGettersType object, JsonFactory factory) {
    return factory.createSerializer(PrimitiveGettersType_JSONOverlay.UNWRAPPER).toJSON(object);
  }

  public static PrimitiveGettersType create() {
    JsObject object = new JsObject();
    object.getBoolean = false;
    object.getByte = 0;
    object.getShort = 0;
    object.getInt = 0;
    object.getLong = 0;
    object.getChar = 0;
    object.getFloat = 0;
    object.getDouble = 0;
    return wrap(object);
  }

  public static PrimitiveGettersType wrap(Object object) {
    if (object instanceof JsObject) {
      return new PrimitiveGettersType_JSONOverlay((JsObject) object);
    }
    throw new IllegalArgumentException("Object '" + object + "' isn't of type test.primitives.PrimitiveGettersType_JSONOverlay.JsObject");
  }

  public static JsObject unwrap(PrimitiveGettersType wrapper) {
    if (wrapper instanceof PrimitiveGettersType_JSONOverlay) {
      return ((PrimitiveGettersType_JSONOverlay) wrapper).object;
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
    boolean getBoolean;

    @JsProperty(
        name = "byte"
    )
    byte getByte;

    @JsProperty(
        name = "short"
    )
    short getShort;

    @JsProperty(
        name = "int"
    )
    int getInt;

    @JsProperty(
        name = "long"
    )
    long getLong;

    @JsProperty(
        name = "char"
    )
    char getChar;

    @JsProperty(
        name = "float"
    )
    float getFloat;

    @JsProperty(
        name = "double"
    )
    double getDouble;
  }
}

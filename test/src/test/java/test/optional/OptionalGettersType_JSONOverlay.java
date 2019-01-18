package test.optional;

import static io.codegen.gwt.jsonoverlay.runtime.gwt.JsHelper.*;

import io.codegen.gwt.jsonoverlay.runtime.JsonFactory;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.function.Function;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import test.OptionalGettersType;

public final class OptionalGettersType_JSONOverlay implements OptionalGettersType {
  public static final Function<JsObject, OptionalGettersType> WRAPPER = OptionalGettersType_JSONOverlay::wrap;

  public static final Function<OptionalGettersType, JsObject> UNWRAPPER = OptionalGettersType_JSONOverlay::unwrap;

  private final JsObject object;

  protected OptionalGettersType_JSONOverlay() {
    this.object = new JsObject();
  }

  protected OptionalGettersType_JSONOverlay(JsObject object) {
    this.object = object;
  }

  @Override
  public Optional<String> getOptionalString() {
    return Optional.ofNullable(object.getOptionalString);
  }

  @Override
  public OptionalInt getOptionalInt() {
    return object.getOptionalInt == undefinedInt() ? OptionalInt.empty() : OptionalInt.of(object.getOptionalInt);
  }

  @Override
  public OptionalLong getOptionalLong() {
    return object.getOptionalLong == undefinedInt() ? OptionalLong.empty() : OptionalLong.of(object.getOptionalLong);
  }

  @Override
  public OptionalDouble getOptionalDouble() {
    return object.getOptionalDouble == undefinedInt() ? OptionalDouble.empty() : OptionalDouble.of(object.getOptionalDouble);
  }

  public static OptionalGettersType parse(String json, JsonFactory factory) {
    return factory.createParser(OptionalGettersType_JSONOverlay.WRAPPER).fromJSON(json);
  }

  public static String serialize(OptionalGettersType object, JsonFactory factory) {
    return factory.createSerializer(OptionalGettersType_JSONOverlay.UNWRAPPER).toJSON(object);
  }

  public static OptionalGettersType create() {
    JsObject object = new JsObject();
    object.getOptionalString = null;
    object.getOptionalInt = undefinedInt();
    object.getOptionalLong = undefinedInt();
    object.getOptionalDouble = undefinedInt();
    return wrap(object);
  }

  public static OptionalGettersType wrap(Object object) {
    if (object instanceof JsObject) {
      return new OptionalGettersType_JSONOverlay((JsObject) object);
    }
    throw new IllegalArgumentException("Object '" + object + "' isn't of type test.optional.OptionalGettersType_JSONOverlay.JsObject");
  }

  public static JsObject unwrap(OptionalGettersType wrapper) {
    if (wrapper instanceof OptionalGettersType_JSONOverlay) {
      return ((OptionalGettersType_JSONOverlay) wrapper).object;
    }
    JsObject object = new JsObject();
    object.getOptionalString = wrapper.getOptionalString()
        .orElse(null);
    object.getOptionalInt = wrapper.getOptionalInt()
        .orElse(undefinedInt());
    object.getOptionalLong = wrapper.getOptionalLong()
        .orElse(undefinedInt());
    object.getOptionalDouble = wrapper.getOptionalDouble()
        .orElse(undefinedInt());
    return object;
  }

  @JsType(
      isNative = true,
      namespace = JsPackage.GLOBAL,
      name = "Object"
  )
  static class JsObject {
    @JsProperty(
        name = "optionalString"
    )
    String getOptionalString;

    @JsProperty(
        name = "optionalInt"
    )
    int getOptionalInt;

    @JsProperty(
        name = "optionalLong"
    )
    long getOptionalLong;

    @JsProperty(
        name = "optionalDouble"
    )
    double getOptionalDouble;
  }
}

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
    return object.getOptionalInt == null ? OptionalInt.empty() : OptionalInt.of(object.getOptionalInt.intValue());
  }

  @Override
  public OptionalLong getOptionalLong() {
    return object.getOptionalLong == null ? OptionalLong.empty() : OptionalLong.of(object.getOptionalLong.longValue());
  }

  @Override
  public OptionalDouble getOptionalDouble() {
    return object.getOptionalDouble == null ? OptionalDouble.empty() : OptionalDouble.of(object.getOptionalDouble.doubleValue());
  }

  @Override
  public void setOptionalString(Optional<String> value) {
    object.getOptionalString = value
        .orElse(null);
  }

  @Override
  public void setOptionalInt(OptionalInt value) {
    if (value.isPresent()) {
      object.getOptionalInt = Double.valueOf(value.getAsInt());
    } else {
      object.getOptionalInt = null;
    }
  }

  @Override
  public void setOptionalLong(OptionalLong value) {
    if (value.isPresent()) {
      object.getOptionalLong = Double.valueOf(value.getAsLong());
    } else {
      object.getOptionalLong = null;
    }
  }

  @Override
  public void setOptionalDouble(OptionalDouble value) {
    if (value.isPresent()) {
      object.getOptionalDouble = Double.valueOf(value.getAsDouble());
    } else {
      object.getOptionalDouble = null;
    }
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
    object.getOptionalInt = null;
    object.getOptionalLong = null;
    object.getOptionalDouble = null;
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
    if (wrapper.getOptionalInt().isPresent()) {
      object.getOptionalInt = Double.valueOf(wrapper.getOptionalInt().getAsInt());
    } else {
      object.getOptionalInt = null;
    }
    if (wrapper.getOptionalLong().isPresent()) {
      object.getOptionalLong = Double.valueOf(wrapper.getOptionalLong().getAsLong());
    } else {
      object.getOptionalLong = null;
    }
    if (wrapper.getOptionalDouble().isPresent()) {
      object.getOptionalDouble = Double.valueOf(wrapper.getOptionalDouble().getAsDouble());
    } else {
      object.getOptionalDouble = null;
    }
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
    Double getOptionalInt;

    @JsProperty(
        name = "optionalLong"
    )
    Double getOptionalLong;

    @JsProperty(
        name = "optionalDouble"
    )
    Double getOptionalDouble;
  }
}

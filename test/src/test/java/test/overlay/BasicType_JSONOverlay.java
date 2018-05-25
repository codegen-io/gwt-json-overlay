package test.overlay;

import io.codegen.gwt.jsonoverlay.runtime.JsonFactory;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import test.BasicType;
import test.SomeEnum;
import test.StringType;

public final class BasicType_JSONOverlay implements BasicType {
  public static final Function<JsObject, BasicType> WRAPPER = BasicType_JSONOverlay::wrap;

  public static final Function<BasicType, JsObject> UNWRAPPER = BasicType_JSONOverlay::unwrap;

  private final JsObject object;

  protected BasicType_JSONOverlay() {
    this.object = new JsObject();
  }

  protected BasicType_JSONOverlay(JsObject object) {
    this.object = object;
  }

  @Override
  public String getString() {
    return object.getString;
  }

  @Override
  public int getInt() {
    return object.getInt;
  }

  @Override
  public StringType getStringType() {
    return StringType_JSONOverlay.WRAPPER.apply(object.getStringType);
  }

  @Override
  public List<String> getStringList() {
    if (object.getStringList == null) {
      return Collections.emptyList();
    }
    return Stream.of(object.getStringList)
        .collect(Collectors.toList());
  }

  @Override
  public List<StringType> getStringTypeList() {
    if (object.getStringTypeList == null) {
      return Collections.emptyList();
    }
    return Stream.of(object.getStringTypeList)
        .map(StringType_JSONOverlay.WRAPPER)
        .collect(Collectors.toList());
  }

  @Override
  public Optional<String> getOptionalString() {
    return Optional.ofNullable(object.getOptionalString);
  }

  @Override
  public Optional<StringType> getOptionalStringType() {
    return Optional.ofNullable(object.getOptionalStringType)
        .map(StringType_JSONOverlay.WRAPPER);
  }

  @Override
  public SomeEnum getEnumType() {
    return SomeEnum.valueOf(object.getEnumType);
  }

  public static BasicType parse(String json, JsonFactory factory) {
    return factory.createParser(BasicType_JSONOverlay.WRAPPER).fromJSON(json);
  }

  public static String serialize(BasicType object, JsonFactory factory) {
    return factory.createSerializer(BasicType_JSONOverlay.UNWRAPPER).toJSON(object);
  }

  public static BasicType wrap(Object object) {
    if (object instanceof JsObject) {
      return new BasicType_JSONOverlay((JsObject) object);
    }
    throw new IllegalArgumentException("Object '" + object + "' isn't of type test.overlay.BasicType_JSONOverlay.JsObject");
  }

  public static JsObject unwrap(Object object) {
    if (object instanceof BasicType_JSONOverlay) {
      return ((BasicType_JSONOverlay) object).object;
    }
    throw new IllegalArgumentException("Object '" + object + "' isn't of type test.overlay.BasicType_JSONOverlay");
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

    @JsProperty(
        name = "int"
    )
    int getInt;

    @JsProperty(
        name = "stringType"
    )
    StringType_JSONOverlay.JsObject getStringType;

    @JsProperty(
        name = "stringList"
    )
    String[] getStringList;

    @JsProperty(
        name = "stringTypeList"
    )
    StringType_JSONOverlay.JsObject[] getStringTypeList;

    @JsProperty(
        name = "optionalString"
    )
    String getOptionalString;

    @JsProperty(
        name = "optionalStringType"
    )
    StringType_JSONOverlay.JsObject getOptionalStringType;

    @JsProperty(
        name = "enumType"
    )
    String getEnumType;
  }
}

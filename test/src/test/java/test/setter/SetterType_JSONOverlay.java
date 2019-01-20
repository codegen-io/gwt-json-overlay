package test.setter;

import static io.codegen.gwt.jsonoverlay.runtime.gwt.JsHelper.*;

import io.codegen.gwt.jsonoverlay.runtime.JsonFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;
import test.SetterType;
import test.SomeEnum;
import test.StringType;

public final class SetterType_JSONOverlay implements SetterType {
  public static final Function<JsObject, SetterType> WRAPPER = SetterType_JSONOverlay::wrap;

  public static final Function<SetterType, JsObject> UNWRAPPER = SetterType_JSONOverlay::unwrap;

  private final JsObject object;

  protected SetterType_JSONOverlay() {
    this.object = new JsObject();
  }

  protected SetterType_JSONOverlay(JsObject object) {
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
  public Long getBoxedLong() {
    return object.getBoxedLong == undefinedObject() ? null : Long.valueOf(object.getBoxedLong.longValue());
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

  @Override
  public Map<String, StringType> getStringMap() {
    if (object.getStringMap == null) {
      return Collections.emptyMap();
    }
    List<String> keys = new ArrayList<>();
    object.getStringMap.forEach(keys::add);
    return keys.stream()
    .collect(Collectors.toMap(Function.identity(),
        key -> Optional.ofNullable(object.getStringMap.get(key))
        .map(StringType_JSONOverlay.WRAPPER)
        .orElse(null)));
  }

  @Override
  public void setString(String value) {
    object.getString = value;
  }

  @Override
  public void setInt(int value) {
    object.getInt = value;
  }

  @Override
  public void setBoxedLong(Long value) {
    object.getBoxedLong = value == null ? null : Double.valueOf(value.longValue());
  }

  @Override
  public void setStringType(StringType value) {
    object.getStringType = StringType_JSONOverlay.UNWRAPPER.apply(value);
  }

  @Override
  public void setStringList(List<String> value) {
    object.getStringList = value.stream()
        .toArray(String[]::new);
  }

  @Override
  public void setStringTypeList(List<StringType> value) {
    object.getStringTypeList = value.stream()
        .map(StringType_JSONOverlay.UNWRAPPER)
        .toArray(StringType_JSONOverlay.JsObject[]::new);
  }

  @Override
  public void setOptionalString(Optional<String> value) {
    object.getOptionalString = value
        .orElse(null);
  }

  @Override
  public void setOptionalStringType(Optional<StringType> value) {
    object.getOptionalStringType = value
        .map(StringType_JSONOverlay.UNWRAPPER)
        .orElse(null);
  }

  @Override
  public void setEnumType(SomeEnum value) {
    object.getEnumType = value.name();
  }

  @Override
  public void setStringMap(Map<String, StringType> value) {
    object.getStringMap = Js.cast(JsPropertyMap.of());
    value.forEach((key, item) -> object.getStringMap.set(key, Optional.ofNullable(item)
        .map(StringType_JSONOverlay.UNWRAPPER)
        .orElse(null)));
  }

  public static SetterType parse(String json, JsonFactory factory) {
    return factory.createParser(SetterType_JSONOverlay.WRAPPER).fromJSON(json);
  }

  public static String serialize(SetterType object, JsonFactory factory) {
    return factory.createSerializer(SetterType_JSONOverlay.UNWRAPPER).toJSON(object);
  }

  public static SetterType create() {
    JsObject object = new JsObject();
    object.getString = null;
    object.getInt = 0;
    object.getBoxedLong = null;
    object.getStringType = null;
    object.getStringList = null;
    object.getStringTypeList = null;
    object.getOptionalString = null;
    object.getOptionalStringType = null;
    object.getEnumType = null;
    object.getStringMap = null;
    return wrap(object);
  }

  public static SetterType wrap(Object object) {
    if (object instanceof JsObject) {
      return new SetterType_JSONOverlay((JsObject) object);
    }
    throw new IllegalArgumentException("Object '" + object + "' isn't of type test.setter.SetterType_JSONOverlay.JsObject");
  }

  public static JsObject unwrap(SetterType wrapper) {
    if (wrapper instanceof SetterType_JSONOverlay) {
      return ((SetterType_JSONOverlay) wrapper).object;
    }
    JsObject object = new JsObject();
    object.getString = wrapper.getString();
    object.getInt = wrapper.getInt();
    object.getBoxedLong = wrapper.getBoxedLong() == null ? null : Double.valueOf(wrapper.getBoxedLong().longValue());
    object.getStringType = StringType_JSONOverlay.UNWRAPPER.apply(wrapper.getStringType());
    object.getStringList = wrapper.getStringList().stream()
        .toArray(String[]::new);
    object.getStringTypeList = wrapper.getStringTypeList().stream()
        .map(StringType_JSONOverlay.UNWRAPPER)
        .toArray(StringType_JSONOverlay.JsObject[]::new);
    object.getOptionalString = wrapper.getOptionalString()
        .orElse(null);
    object.getOptionalStringType = wrapper.getOptionalStringType()
        .map(StringType_JSONOverlay.UNWRAPPER)
        .orElse(null);
    object.getEnumType = wrapper.getEnumType().name();
    object.getStringMap = Js.cast(JsPropertyMap.of());
    wrapper.getStringMap().forEach((key, item) -> object.getStringMap.set(key, Optional.ofNullable(item)
        .map(StringType_JSONOverlay.UNWRAPPER)
        .orElse(null)));
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

    @JsProperty(
        name = "int"
    )
    int getInt;

    @JsProperty(
        name = "boxedLong"
    )
    Double getBoxedLong;

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

    @JsProperty(
        name = "stringMap"
    )
    JsPropertyMap<StringType_JSONOverlay.JsObject> getStringMap;
  }
}

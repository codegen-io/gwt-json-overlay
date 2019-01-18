package test.map;

import io.codegen.gwt.jsonoverlay.runtime.JsonFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import jsinterop.base.Js;
import jsinterop.base.JsPropertyMap;
import test.MapType;
import test.StringType;

public final class MapType_JSONOverlay implements MapType {
  public static final Function<JsObject, MapType> WRAPPER = MapType_JSONOverlay::wrap;

  public static final Function<MapType, JsObject> UNWRAPPER = MapType_JSONOverlay::unwrap;

  private final JsObject object;

  protected MapType_JSONOverlay() {
    this.object = new JsObject();
  }

  protected MapType_JSONOverlay(JsObject object) {
    this.object = object;
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

  public static MapType parse(String json, JsonFactory factory) {
    return factory.createParser(MapType_JSONOverlay.WRAPPER).fromJSON(json);
  }

  public static String serialize(MapType object, JsonFactory factory) {
    return factory.createSerializer(MapType_JSONOverlay.UNWRAPPER).toJSON(object);
  }

  public static MapType create() {
    JsObject object = new JsObject();
    object.getStringMap = null;
    return wrap(object);
  }

  public static MapType wrap(Object object) {
    if (object instanceof JsObject) {
      return new MapType_JSONOverlay((JsObject) object);
    }
    throw new IllegalArgumentException("Object '" + object + "' isn't of type test.map.MapType_JSONOverlay.JsObject");
  }

  public static JsObject unwrap(MapType wrapper) {
    if (wrapper instanceof MapType_JSONOverlay) {
      return ((MapType_JSONOverlay) wrapper).object;
    }
    JsObject object = new JsObject();
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
        name = "stringMap"
    )
    JsPropertyMap<StringType_JSONOverlay.JsObject> getStringMap;
  }
}

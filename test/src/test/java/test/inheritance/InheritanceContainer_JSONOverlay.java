package test.inheritance;

import static io.codegen.gwt.jsonoverlay.runtime.gwt.JsHelper.*;

import io.codegen.gwt.jsonoverlay.runtime.JsonFactory;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import test.InheritanceContainer;
import test.InheritanceSuper;

public final class InheritanceContainer_JSONOverlay implements InheritanceContainer {
  public static final Function<JsObject, InheritanceContainer> WRAPPER = InheritanceContainer_JSONOverlay::wrap;

  public static final Function<InheritanceContainer, JsObject> UNWRAPPER = InheritanceContainer_JSONOverlay::unwrap;

  private final JsObject object;

  protected InheritanceContainer_JSONOverlay() {
    this.object = new JsObject();
  }

  protected InheritanceContainer_JSONOverlay(JsObject object) {
    this.object = object;
  }

  @Override
  public InheritanceSuper getSomeKindOfInstances() {
    return InheritanceSuper_JSONOverlay.WRAPPER.apply(object.getSomeKindOfInstances);
  }

  @Override
  public Optional<InheritanceSuper> getPossibleKindOfInstances() {
    return Optional.ofNullable(object.getPossibleKindOfInstances)
        .map(InheritanceSuper_JSONOverlay.WRAPPER);
  }

  @Override
  public List<InheritanceSuper> getAllKindsOfInstances() {
    if (object.getAllKindsOfInstances == null) {
      return Collections.emptyList();
    }
    return Stream.of(object.getAllKindsOfInstances)
        .map(InheritanceSuper_JSONOverlay.WRAPPER)
        .collect(Collectors.toList());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof InheritanceContainer_JSONOverlay)) return false;
    InheritanceContainer_JSONOverlay that = (InheritanceContainer_JSONOverlay) o;
    return object.equals(that.object);
  }

  @Override
  public int hashCode() {
    return Objects.hash(object);
  }

  public static InheritanceContainer parse(String json, JsonFactory factory) {
    return factory.createParser(InheritanceContainer_JSONOverlay.WRAPPER).fromJSON(json);
  }

  public static String serialize(InheritanceContainer object, JsonFactory factory) {
    return factory.createSerializer(InheritanceContainer_JSONOverlay.UNWRAPPER).toJSON(object);
  }

  public static InheritanceContainer create() {
    JsObject object = new JsObject();
    object.getSomeKindOfInstances = null;
    object.getPossibleKindOfInstances = null;
    object.getAllKindsOfInstances = null;
    return wrap(object);
  }

  public static InheritanceContainer wrap(Object object) {
    if (object instanceof JsObject) {
      return new InheritanceContainer_JSONOverlay((JsObject) object);
    }
    throw new IllegalArgumentException("Object '" + object + "' isn't of type test.inheritance.InheritanceContainer_JSONOverlay.JsObject");
  }

  public static JsObject unwrap(InheritanceContainer wrapper) {
    if (wrapper instanceof InheritanceContainer_JSONOverlay) {
      return ((InheritanceContainer_JSONOverlay) wrapper).object;
    }
    JsObject object = new JsObject();
    object.getSomeKindOfInstances = InheritanceSuper_JSONOverlay.UNWRAPPER.apply(wrapper.getSomeKindOfInstances());
    object.getPossibleKindOfInstances = wrapper.getPossibleKindOfInstances()
        .map(InheritanceSuper_JSONOverlay.UNWRAPPER)
        .orElse(null);
    object.getAllKindsOfInstances = wrapper.getAllKindsOfInstances().stream()
        .map(InheritanceSuper_JSONOverlay.UNWRAPPER)
        .toArray(InheritanceSuper_JSONOverlay.JsObject[]::new);
    return object;
  }

  @JsType(
      isNative = true,
      namespace = JsPackage.GLOBAL,
      name = "Object"
  )
  static class JsObject {
    @JsProperty(
        name = "someKindOfInstances"
    )
    InheritanceSuper_JSONOverlay.JsObject getSomeKindOfInstances;

    @JsProperty(
        name = "possibleKindOfInstances"
    )
    InheritanceSuper_JSONOverlay.JsObject getPossibleKindOfInstances;

    @JsProperty(
        name = "allKindsOfInstances"
    )
    InheritanceSuper_JSONOverlay.JsObject[] getAllKindsOfInstances;
  }
}

package test.inheritance;

import io.codegen.gwt.jsonoverlay.runtime.JsonFactory;
import java.util.function.Function;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import test.InheritanceSubA;

public final class InheritanceSubA_JSONOverlay implements InheritanceSubA {
  public static final Function<JsObject, InheritanceSubA> WRAPPER = InheritanceSubA_JSONOverlay::wrap;

  public static final Function<InheritanceSubA, JsObject> UNWRAPPER = InheritanceSubA_JSONOverlay::unwrap;

  private final JsObject object;

  protected InheritanceSubA_JSONOverlay() {
    this.object = new JsObject();
  }

  protected InheritanceSubA_JSONOverlay(JsObject object) {
    this.object = object;
  }

  @Override
  public String getA() {
    return object.getA;
  }

  @Override
  public String getKind() {
    return object.getKind;
  }

  public static InheritanceSubA parse(String json, JsonFactory factory) {
    return factory.createParser(InheritanceSubA_JSONOverlay.WRAPPER).fromJSON(json);
  }

  public static String serialize(InheritanceSubA object, JsonFactory factory) {
    return factory.createSerializer(InheritanceSubA_JSONOverlay.UNWRAPPER).toJSON(object);
  }

  public static InheritanceSubA wrap(Object object) {
    if (object instanceof JsObject) {
      return new InheritanceSubA_JSONOverlay((JsObject) object);
    }
    throw new IllegalArgumentException("Object '" + object + "' isn't of type test.inheritance.InheritanceSubA_JSONOverlay.JsObject");
  }

  public static JsObject unwrap(Object object) {
    if (object instanceof InheritanceSubA_JSONOverlay) {
      return ((InheritanceSubA_JSONOverlay) object).object;
    }
    throw new IllegalArgumentException("Object '" + object + "' isn't of type test.inheritance.InheritanceSubA_JSONOverlay");
  }

  @JsType(
      isNative = true,
      namespace = JsPackage.GLOBAL,
      name = "Object"
  )
  static class JsObject extends InheritanceSuper_JSONOverlay.JsObject {
    @JsProperty(
        name = "a"
    )
    String getA;

    @JsProperty(
        name = "kind"
    )
    String getKind;
  }
}

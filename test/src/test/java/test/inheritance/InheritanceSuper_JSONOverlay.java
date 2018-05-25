package test.inheritance;

import io.codegen.gwt.jsonoverlay.runtime.JsonFactory;
import java.util.function.Function;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import test.InheritanceSuper;

public final class InheritanceSuper_JSONOverlay implements InheritanceSuper {
  public static final Function<JsObject, InheritanceSuper> WRAPPER = InheritanceSuper_JSONOverlay::wrap;

  public static final Function<InheritanceSuper, JsObject> UNWRAPPER = InheritanceSuper_JSONOverlay::unwrap;

  private final JsObject object;

  protected InheritanceSuper_JSONOverlay() {
    this.object = new JsObject();
  }

  protected InheritanceSuper_JSONOverlay(JsObject object) {
    this.object = object;
  }

  @Override
  public String getKind() {
    return object.getKind;
  }

  public static InheritanceSuper parse(String json, JsonFactory factory) {
    return factory.createParser(InheritanceSuper_JSONOverlay.WRAPPER).fromJSON(json);
  }

  public static String serialize(InheritanceSuper object, JsonFactory factory) {
    return factory.createSerializer(InheritanceSuper_JSONOverlay.UNWRAPPER).toJSON(object);
  }

  public static InheritanceSuper wrap(Object object) {
    if (object instanceof JsObject) {
      switch (((JsObject) object).getKind) {
        case "a":
          return new InheritanceSubA_JSONOverlay((InheritanceSubA_JSONOverlay.JsObject) object);
        case "b":
          return new InheritanceSubB_JSONOverlay((InheritanceSubB_JSONOverlay.JsObject) object);
        default:
          throw new IllegalArgumentException("Unknown discriminator '" + ((JsObject) object).getKind + "'");
      }
    }
    throw new IllegalArgumentException("Object '" + object + "' isn't of type test.inheritance.InheritanceSuper_JSONOverlay.JsObject");
  }

  public static JsObject unwrap(Object object) {
    if (object instanceof InheritanceSuper_JSONOverlay) {
      return ((InheritanceSuper_JSONOverlay) object).object;
    }
    throw new IllegalArgumentException("Object '" + object + "' isn't of type test.inheritance.InheritanceSuper_JSONOverlay");
  }

  @JsType(
      isNative = true,
      namespace = JsPackage.GLOBAL,
      name = "Object"
  )
  static class JsObject {
    @JsProperty(
        name = "kind"
    )
    String getKind;
  }

}

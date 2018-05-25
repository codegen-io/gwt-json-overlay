package test.inheritance;

import io.codegen.gwt.jsonoverlay.runtime.JsonFactory;
import java.util.function.Function;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import test.InheritanceSubB;

public final class InheritanceSubB_JSONOverlay implements InheritanceSubB {
  public static final Function<JsObject, InheritanceSubB> WRAPPER = InheritanceSubB_JSONOverlay::wrap;

  public static final Function<InheritanceSubB, JsObject> UNWRAPPER = InheritanceSubB_JSONOverlay::unwrap;

  private final JsObject object;

  protected InheritanceSubB_JSONOverlay() {
    this.object = new JsObject();
  }

  protected InheritanceSubB_JSONOverlay(JsObject object) {
    this.object = object;
  }

  @Override
  public String getB() {
    return object.getB;
  }

  @Override
  public String getKind() {
    return object.getKind;
  }

  public static InheritanceSubB parse(String json, JsonFactory factory) {
    return factory.createParser(InheritanceSubB_JSONOverlay.WRAPPER).fromJSON(json);
  }

  public static String serialize(InheritanceSubB object, JsonFactory factory) {
    return factory.createSerializer(InheritanceSubB_JSONOverlay.UNWRAPPER).toJSON(object);
  }

  public static InheritanceSubB wrap(Object object) {
    if (object instanceof JsObject) {
      return new InheritanceSubB_JSONOverlay((JsObject) object);
    }
    throw new IllegalArgumentException("Object '" + object + "' isn't of type test.inheritance.InheritanceSubB_JSONOverlay.JsObject");
  }

  public static JsObject unwrap(Object object) {
    if (object instanceof InheritanceSubB_JSONOverlay) {
      return ((InheritanceSubB_JSONOverlay) object).object;
    }
    throw new IllegalArgumentException("Object '" + object + "' isn't of type test.inheritance.InheritanceSubB_JSONOverlay");
  }

  @JsType(
      isNative = true,
      namespace = JsPackage.GLOBAL,
      name = "Object"
  )
  static class JsObject extends InheritanceSuper_JSONOverlay.JsObject {
    @JsProperty(
        name = "b"
    )
    String getB;

    @JsProperty(
        name = "kind"
    )
    String getKind;
  }
}

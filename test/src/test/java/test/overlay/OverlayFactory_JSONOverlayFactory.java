package test.overlay;

import com.google.gwt.core.client.JavaScriptObject;
import test.BasicType;
import test.StringType;

public final class OverlayFactory_JSONOverlayFactory implements OverlayFactory {
  static final OverlayFactory INSTANCE = new OverlayFactory_JSONOverlayFactory();

  @Override
  public BasicType asBasicType(JavaScriptObject argument) {
    return BasicType_JSONOverlay.wrap(argument);
  }

  @Override
  public JavaScriptObject asJavascripObject(BasicType argument) {
    return cast(BasicType_JSONOverlay.unwrap(argument));
  }

  @Override
  public StringType asStringType(JavaScriptObject argument) {
    return StringType_JSONOverlay.wrap(argument);
  }

  @SuppressWarnings("unchecked")
  static final <T> T cast(Object object) {
    return (T) object;
  }

}

package test.overlay;

import test.BasicType;

public interface OverlayFactory {

    BasicType parseBasicType(String json);

    String serializeBasicType(BasicType json);

}

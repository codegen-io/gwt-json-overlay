package test.overlay;

import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;

import io.codegen.gwt.jsonoverlay.annotations.JsonOverlayFactory;
import test.BasicType;
import test.StringType;

/**
 * Factory interface for creating JSON overlays for various layout model components.
 */
@JsonOverlayFactory
public interface OverlayFactory {
    static final OverlayFactory INSTANCE = OverlayFactory_JSONOverlayFactory.INSTANCE;

    /**
     * Create a new instance of a {@link BasicType}, which acts as an overlay for a new {@link JavaScriptObject}.
     *
     * @return a BasicType instance
     */
    BasicType createBasicType();

    /**
     * Create an instance of a {@link BasicType} which overlays the given {@link JavaScriptObject}.
     *
     * @param object a JavaScript object which corresponds to the structure of BasicType
     * @return a BasicType overlay over the JavaScript object
     */
    BasicType asBasicType(JavaScriptObject object);

    /**
     * Get the {@link JavaScriptObject} for the given {@link BasicType}.
     *
     * @param overlay a BasicType which overlays a JavaScript object
     * @return a JavaScript object which represents the type
     */
    JavaScriptObject asJavascripObject(BasicType overlay);

    /**
     * Create an instance of a {@link StringType} which overlays the given {@link JavaScriptObject}.
     *
     * @param object a JavaScript object which corresponds to the structure of StringType
     * @return a StringType overlay over the JavaScript object
     */
    StringType asStringType(JavaScriptObject object);

    /**
     * Create a list of {@link StringType} instances which overlays the given {@link JavaScriptObject}.
     *
     * @param object a JavaScript object which corresponds to the structure of a StringType array
     * @return a list of StringType overlays over the JavaScript object
     */
    List<StringType> asStringTypeList(JavaScriptObject object);

    static OverlayFactory getInstance() {
        return INSTANCE;
    }

}

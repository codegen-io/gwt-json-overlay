package io.codegen.gwt.jsonoverlay.processor.model;

import org.immutables.value.Value;

/**
 * Representation of a Java convert method, which is able to convert a JavaScript object to an overlay or vice versa.
 */
@Value.Immutable
public interface JavaConvertMethod {

    /**
     * @return the name of the method
     */
    String getMethodName();

    /**
     * @return the type of the returned instance
     */
    JavaType getReturnType();

    /**
     * @return the type of the method argument
     */
    JavaType getArgumentType();

    public static Builder builder() {
        return new Builder();
    }

    public class Builder extends ImmutableJavaConvertMethod.Builder {}

}

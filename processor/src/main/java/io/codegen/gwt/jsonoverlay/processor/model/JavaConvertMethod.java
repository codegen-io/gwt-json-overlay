package io.codegen.gwt.jsonoverlay.processor.model;

import org.immutables.value.Value;

import com.squareup.javapoet.ClassName;

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
    ClassName getReturnType();

    /**
     * @return the type of the method argument
     */
    ClassName getArgumentType();

    public static Builder builder() {
        return new Builder();
    }

    public class Builder extends ImmutableJavaConvertMethod.Builder {}

}

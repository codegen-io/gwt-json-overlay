package io.codegen.gwt.jsonoverlay.processor.model;

import org.immutables.value.Value;

import com.squareup.javapoet.ClassName;

/**
 * Representation of a Java convert method, which is able to create an overlay for a new javascript object.
 */
@Value.Immutable
public interface JavaCreateMethod {

    /**
     * @return the name of the method
     */
    String getMethodName();

    /**
     * @return the type of the returned instance
     */
    ClassName getReturnType();

    public static Builder builder() {
        return new Builder();
    }

    public class Builder extends ImmutableJavaCreateMethod.Builder {}

}

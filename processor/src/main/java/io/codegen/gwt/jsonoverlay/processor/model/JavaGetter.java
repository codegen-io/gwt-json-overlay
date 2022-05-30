package io.codegen.gwt.jsonoverlay.processor.model;

import org.immutables.value.Value;

/**
 * Representation of a Java get method, encapsulating is name, the property it represents and the type of the property.
 */
@Value.Immutable
public interface JavaGetter {

    /**
     * @return the name of the property
     */
    String getPropertyName();

    /**
     * @return the name of the method
     */
    String getMethodName();

    /**
     * @return the type of the property
     */
    JavaType getPropertyType();

    boolean hasSetter();

    boolean hasFluentSetter();

    public static Builder builder() {
        return new Builder();
    }

    public class Builder extends ImmutableJavaGetter.Builder {}

}

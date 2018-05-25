package io.codegen.gwt.jsonoverlay.processor.model;

import java.util.List;

import org.immutables.value.Value;

@Value.Immutable
public interface JavaInterface {

    JavaType getType();

    List<JavaGetter> getGetters();

    public static Builder builder() {
        return new Builder();
    }

    public class Builder extends ImmutableJavaInterface.Builder {}

}

package io.codegen.gwt.jsonoverlay.processor.model;

import java.util.List;

import org.immutables.value.Value;

import com.squareup.javapoet.ClassName;

@Value.Immutable
public interface JavaFactory {

    ClassName getType();

    List<JavaConvertMethod> getConvertMethods();

    List<JavaCreateMethod> getCreateMethods();

    public static Builder builder() {
        return new Builder();
    }

    public class Builder extends ImmutableJavaFactory.Builder {}

}

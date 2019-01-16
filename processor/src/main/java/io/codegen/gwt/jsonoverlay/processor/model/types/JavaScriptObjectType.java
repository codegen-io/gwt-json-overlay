package io.codegen.gwt.jsonoverlay.processor.model.types;

import org.immutables.value.Value;

import com.squareup.javapoet.TypeName;

import io.codegen.gwt.jsonoverlay.processor.model.JavaType;
import io.codegen.gwt.jsonoverlay.processor.model.JavaTypeVisitor;

@Value.Immutable
public interface JavaScriptObjectType extends JavaType {

    /**
     * @return the type of the JavaScriptObject, usually com.google.gwt.core.client.JavaScriptObject
     */
    TypeName getJavaScriptObjectType();

    @Override
    default <T> T accept(JavaTypeVisitor<T> visitor) {
        return visitor.visitJavaScriptObjectType(this);
    }

    public static Builder builder() {
        return new Builder()
                .kind(Kind.JAVASCRIPT_OBJECT);
    }

    public class Builder extends ImmutableJavaScriptObjectType.Builder {}

}

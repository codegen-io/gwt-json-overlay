package io.codegen.gwt.jsonoverlay.processor.model.types;

import org.immutables.value.Value;

import io.codegen.gwt.jsonoverlay.processor.model.JavaType;
import io.codegen.gwt.jsonoverlay.processor.model.JavaTypeVisitor;

@Value.Immutable
public interface MapType extends JavaType {

    JavaType getValueType();

    @Override
    default <T> T accept(JavaTypeVisitor<T> visitor) {
        return visitor.visitMapType(this);
    }

    public static Builder builder() {
        return new Builder()
                .kind(Kind.MAP);
    }

    public class Builder extends ImmutableMapType.Builder {}

}

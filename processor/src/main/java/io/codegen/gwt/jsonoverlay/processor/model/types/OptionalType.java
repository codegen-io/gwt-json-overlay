package io.codegen.gwt.jsonoverlay.processor.model.types;

import org.immutables.value.Value;

import io.codegen.gwt.jsonoverlay.processor.model.JavaType;
import io.codegen.gwt.jsonoverlay.processor.model.JavaTypeVisitor;

@Value.Immutable
public interface OptionalType extends JavaType {

    JavaType getElementType();

    @Override
    default <T> T accept(JavaTypeVisitor<T> visitor) {
        return visitor.visitOptionalType(this);
    }

    public static Builder builder() {
        return new Builder()
                .kind(Kind.OPTIONAL);
    }

    public class Builder extends ImmutableOptionalType.Builder {}

}

package io.codegen.gwt.jsonoverlay.processor.model.types;

import org.immutables.value.Value;

import io.codegen.gwt.jsonoverlay.processor.model.JavaType;
import io.codegen.gwt.jsonoverlay.processor.model.JavaTypeVisitor;

@Value.Immutable
public interface ListType extends JavaType {

    JavaType getElementType();

    @Override
    default <T> T accept(JavaTypeVisitor<T> visitor) {
        return visitor.visitListType(this);
    }

    public static Builder builder() {
        return new Builder()
                .kind(Kind.LIST);
    }

    public class Builder extends ImmutableListType.Builder {}

}

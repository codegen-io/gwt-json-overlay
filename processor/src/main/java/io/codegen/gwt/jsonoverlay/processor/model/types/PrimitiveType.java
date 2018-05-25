package io.codegen.gwt.jsonoverlay.processor.model.types;

import org.immutables.value.Value;

import com.squareup.javapoet.TypeName;

import io.codegen.gwt.jsonoverlay.processor.model.JavaType;
import io.codegen.gwt.jsonoverlay.processor.model.JavaTypeVisitor;

@Value.Immutable
public interface PrimitiveType extends JavaType {

    TypeName getPrimitiveType();

    @Override
    default <T> T accept(JavaTypeVisitor<T> visitor) {
        return visitor.visitPrimitiveType(this);
    }

    public static Builder builder() {
        return new Builder()
                .kind(Kind.PRIMITIVE);
    }

    public class Builder extends ImmutablePrimitiveType.Builder {}

}

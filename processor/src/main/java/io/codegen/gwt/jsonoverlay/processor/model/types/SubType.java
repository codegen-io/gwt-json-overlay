package io.codegen.gwt.jsonoverlay.processor.model.types;

import org.immutables.value.Value;

import com.squareup.javapoet.ClassName;

import io.codegen.gwt.jsonoverlay.processor.model.JavaType;
import io.codegen.gwt.jsonoverlay.processor.model.JavaTypeVisitor;

@Value.Immutable
public interface SubType extends JavaType {

    ClassName getSuperType();

    ClassName getSubType();

    @Override
    default <T> T accept(JavaTypeVisitor<T> visitor) {
        return visitor.visitSubType(this);
    }

    public static Builder builder() {
        return new Builder()
                .kind(Kind.SUB);
    }

    public class Builder extends ImmutableSubType.Builder {}

}

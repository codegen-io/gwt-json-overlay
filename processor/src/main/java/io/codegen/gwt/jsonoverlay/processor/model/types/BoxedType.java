package io.codegen.gwt.jsonoverlay.processor.model.types;

import org.immutables.value.Value;

import com.squareup.javapoet.ClassName;

import io.codegen.gwt.jsonoverlay.processor.model.JavaType;
import io.codegen.gwt.jsonoverlay.processor.model.JavaTypeVisitor;

@Value.Immutable
public interface BoxedType extends JavaType{

    ClassName getBoxedType();

    @Override
    default <T> T accept(JavaTypeVisitor<T> visitor) {
        return visitor.visitBoxedType(this);
    }

    public static Builder builder() {
        return new Builder()
                .kind(Kind.BOXED);
    }

    public class Builder extends ImmutableBoxedType.Builder {}

}

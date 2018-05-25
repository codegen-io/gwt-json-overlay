package io.codegen.gwt.jsonoverlay.processor.model.types;

import org.immutables.value.Value;

import com.squareup.javapoet.ClassName;

import io.codegen.gwt.jsonoverlay.processor.model.JavaType;
import io.codegen.gwt.jsonoverlay.processor.model.JavaTypeVisitor;

@Value.Immutable
public interface OverlayType extends JavaType {

    ClassName getOverlayType();

    @Override
    default <T> T accept(JavaTypeVisitor<T> visitor) {
        return visitor.visitOverlayType(this);
    }

    public static Builder builder() {
        return new Builder()
                .kind(Kind.OVERLAY);
    }

    public class Builder extends ImmutableOverlayType.Builder {}

}

package io.codegen.gwt.jsonoverlay.processor.model.types;

import org.immutables.value.Value;

import com.squareup.javapoet.TypeName;

import io.codegen.gwt.jsonoverlay.processor.model.JavaType;
import io.codegen.gwt.jsonoverlay.processor.model.JavaTypeVisitor;

@Value.Immutable
public interface StringType extends JavaType {

    /**
     * @return the type of the string, usually java.lang.String
     */
    TypeName getStringType();

    @Override
    default <T> T accept(JavaTypeVisitor<T> visitor) {
        return visitor.visitStringType(this);
    }

    public static Builder builder() {
        return new Builder()
                .kind(Kind.STRING);
    }

    public class Builder extends ImmutableStringType.Builder {}

}

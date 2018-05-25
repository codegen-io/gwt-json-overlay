package io.codegen.gwt.jsonoverlay.processor.model.types;

import java.util.Map;

import org.immutables.value.Value;

import com.squareup.javapoet.ClassName;

import io.codegen.gwt.jsonoverlay.processor.model.JavaType;
import io.codegen.gwt.jsonoverlay.processor.model.JavaTypeVisitor;

@Value.Immutable
public interface InheritedType extends JavaType {

    /**
     * @return the name of the discriminator method
     */
    String getDiscriminatorMethodName();

    /**
     * @return the classname of the super type
     */
    ClassName getSuperType();

    /**
     * @return map of the inherited {@link JavaType types} by their discriminator
     */
    Map<String, JavaType> getInheritedTypes();

    @Override
    default <T> T accept(JavaTypeVisitor<T> visitor) {
        return visitor.visitInheritedType(this);
    }

    public static Builder builder() {
        return new Builder()
                .kind(Kind.INHERITED);
    }

    public class Builder extends ImmutableInheritedType.Builder {}

}

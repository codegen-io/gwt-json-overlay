package io.codegen.gwt.jsonoverlay.processor.model.types;

import com.squareup.javapoet.ClassName;
import io.codegen.gwt.jsonoverlay.processor.model.JavaType;
import io.codegen.gwt.jsonoverlay.processor.model.JavaTypeVisitor;
import org.immutables.value.Value;

@Value.Immutable
public interface DateType extends JavaType {

    ClassName getDateType();

    @Override
    default <T> T accept(JavaTypeVisitor<T> visitor) {
        return visitor.visitDateType(this);
    }

    public static Builder builder() {
        return new Builder()
                .kind(Kind.OVERLAY);
    }

    public class Builder extends ImmutableDateType.Builder {}

}

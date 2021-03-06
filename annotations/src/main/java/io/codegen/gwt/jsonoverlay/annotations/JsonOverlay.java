package io.codegen.gwt.jsonoverlay.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * JsonOverlay annotation which triggers the generation of the overlay classes.
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.TYPE, ElementType.PACKAGE})
public @interface JsonOverlay {

    Class<?>[] value();

}

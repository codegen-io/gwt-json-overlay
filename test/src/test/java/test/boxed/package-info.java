/**
 * Unit tests for the JSON overlay processor using boxed primitives.
 */
@JsonOverlay(value = {BoxedGettersType.class, BoxedSettersType.class})
package test.boxed;

import io.codegen.gwt.jsonoverlay.annotations.JsonOverlay;
import test.BoxedGettersType;
import test.BoxedSettersType;

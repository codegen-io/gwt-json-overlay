package io.codegen.gwt.jsonoverlay.processor.builder;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.SimpleElementVisitor8;

import com.squareup.javapoet.ClassName;

public class ElementNameResolver extends SimpleElementVisitor8<ClassName, Void> {

    @Override
    protected ClassName defaultAction(Element e, Void p) {
        throw new IllegalStateException("Unable to resolve name for element " + e);
    }

    @Override
    public ClassName visitType(TypeElement element, Void p) {
        return ClassName.get(element);
    }

}

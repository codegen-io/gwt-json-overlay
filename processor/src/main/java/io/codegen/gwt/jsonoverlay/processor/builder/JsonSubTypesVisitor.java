package io.codegen.gwt.jsonoverlay.processor.builder;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.SimpleAnnotationValueVisitor8;

import io.codegen.gwt.jsonoverlay.processor.model.JavaType;

public class JsonSubTypesVisitor extends SimpleAnnotationValueVisitor8<Map<String, JavaType>, Void> {

    private final Consumer<TypeElement> consumer;

    public JsonSubTypesVisitor(Consumer<TypeElement> consumer) {
        this.consumer = consumer;
    }

    @Override
    protected Map<String, JavaType> defaultAction(Object o, Void p) {
        throw new UnsupportedOperationException();
    }

    public Map<String, JavaType> visitArray(List<? extends AnnotationValue> values, Void v) {
        return values.stream()
                .map(value -> value.accept(new JsonSubTypeVisitor(consumer), null))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}

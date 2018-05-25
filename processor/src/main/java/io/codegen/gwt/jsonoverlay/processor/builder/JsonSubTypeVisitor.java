package io.codegen.gwt.jsonoverlay.processor.builder;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleAnnotationValueVisitor8;

import io.codegen.gwt.jsonoverlay.processor.model.JavaType;

public class JsonSubTypeVisitor extends SimpleAnnotationValueVisitor8<Map.Entry<String, JavaType>, Void> {

    private final Consumer<TypeElement> consumer;

    public JsonSubTypeVisitor(Consumer<TypeElement> consumer) {
        this.consumer = consumer;
    }

    @Override
    protected Map.Entry<String, JavaType> defaultAction(Object o, Void p) {
        throw new UnsupportedOperationException();
    }

    public Map.Entry<String, JavaType> visitAnnotation(AnnotationMirror mirror, Void v) {
        Optional<String> name = mirror.getElementValues().entrySet().stream()
                .filter(entry -> "name".equals(entry.getKey().getSimpleName().toString()))
                .map(entry -> entry.getValue().getValue().toString())
                .findFirst();

        Optional<JavaType> value = mirror.getElementValues().entrySet().stream()
                .filter(entry -> "value".equals(entry.getKey().getSimpleName().toString()))
                .map(entry -> entry.getValue().accept(new SimpleAnnotationValueVisitor8<JavaType, Void>() {
                    public JavaType visitType(TypeMirror type, Void v) {
                        return type.accept(new TypeResolver(consumer), null);
                    }
                }, null))
                .findFirst();

        return Collections.singletonMap(name.get(), value.get()).entrySet().iterator().next();
    }

}

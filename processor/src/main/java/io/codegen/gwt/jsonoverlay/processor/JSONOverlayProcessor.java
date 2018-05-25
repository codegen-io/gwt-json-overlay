package io.codegen.gwt.jsonoverlay.processor;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic.Kind;

import org.immutables.metainf.Metainf;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import io.codegen.gwt.jsonoverlay.processor.builder.ModelBuilder;
import io.codegen.gwt.jsonoverlay.processor.generator.OverlayGenerator;
import io.codegen.gwt.jsonoverlay.processor.model.JavaInterface;

@Metainf.Service(Processor.class)
public class JSONOverlayProcessor extends AbstractProcessor {

    private final Set<String> processedClasses = new HashSet<>();

    private Elements elementUtils = null;
    private Messager messager = null;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        this.elementUtils = processingEnv.getElementUtils();
        this.messager = processingEnv.getMessager();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Arrays.asList(
                ClassNames.JSON_OVERLAY_ANNOTATION).stream()
                .map(ClassName::toString)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        Map<Element, Set<String>> classesToProcess = collectClassNames(annotations, roundEnv);

        classesToProcess.entrySet().stream()
            .sorted(Comparator.comparing(entry -> entry.getKey().toString()))
            .collect(Collectors.toMap(Map.Entry::getKey, entry -> {
                ModelBuilder builder = new ModelBuilder();
                entry.getValue().stream()
                    .map(elementUtils::getTypeElement)
                    .forEach(builder::addToModel);
                return builder;
            }))
            .forEach(this::generateOverlays);


        if (roundEnv.processingOver()) {
            processedClasses.clear();
        }

        return false;
    }

    private Map<Element, Set<String>> collectClassNames(Set<? extends TypeElement> annotations, RoundEnvironment environment) {
        return annotations.stream()
            .flatMap(annotation -> environment.getElementsAnnotatedWith(annotation).stream())
            .collect(Collectors.toMap(Function.identity(), this::getClassNames));
    }

    private Set<String> getClassNames(Element element) {
        switch (element.getKind()) {
            case PACKAGE:
                return element.getAnnotationMirrors().stream()
                        .filter(mirror -> ClassNames.JSON_OVERLAY_ANNOTATION.equals(AnnotationSpec.get(mirror).type))
                        .map(this::getClasses)
                        .flatMap(Collection::stream)
                        .collect(Collectors.toSet());
            default:
                emitError("Unkown element kind " + element.getKind(), element);
                return Collections.emptySet();
        }
    }

    private void emitError(String message, Element element) {
        messager.printMessage(Kind.ERROR, message, element);
    }

    private Set<String> getClasses(AnnotationMirror annotation) {
        return annotation.getElementValues().entrySet().stream()
                .filter(entry -> "value".equals(entry.getKey().getSimpleName().toString()))
                .map(Map.Entry::getValue)
                .map(AnnotationValue::getValue)
                .map(entry -> (List<?>)entry)
                .flatMap(Collection::stream)
                .map(AnnotationValue.class::cast)
                .map(AnnotationValue::getValue)
                .map(DeclaredType.class::cast)
                .map(DeclaredType::asElement)
                .map(Element::asType)
                .map(TypeMirror::toString)
                .collect(Collectors.toSet());
    }

    private void generateOverlays(Element parent, ModelBuilder builder) {

        for (JavaInterface javaInterface : builder.getJavaInterfaces()) {
            TypeSpec overlay = new OverlayGenerator(parent.toString()).generateOverlay(javaInterface);

            JavaFile javaFile = JavaFile.builder(parent.toString(), overlay)
                    .skipJavaLangImports(true)
                    .build();
            try {
                javaFile.writeTo(this.processingEnv.getFiler());
            } catch (IOException e) {
                emitError("Unable to write file: " + e.getMessage(), parent);
            }
        }
    }

}

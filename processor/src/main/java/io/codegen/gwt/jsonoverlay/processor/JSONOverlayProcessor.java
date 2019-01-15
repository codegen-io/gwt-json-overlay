package io.codegen.gwt.jsonoverlay.processor;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic.Kind;

import org.immutables.metainf.Metainf;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import io.codegen.gwt.jsonoverlay.processor.builder.ModelBuilder;
import io.codegen.gwt.jsonoverlay.processor.generator.OverlayFactoryGenerator;
import io.codegen.gwt.jsonoverlay.processor.generator.OverlayGenerator;
import io.codegen.gwt.jsonoverlay.processor.model.JavaConvertMethod;
import io.codegen.gwt.jsonoverlay.processor.model.JavaFactory;
import io.codegen.gwt.jsonoverlay.processor.model.JavaInterface;

@SupportedSourceVersion(SourceVersion.RELEASE_8)
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
        return Stream.of(
                ClassNames.JSON_OVERLAY_ANNOTATION,
                ClassNames.JSON_OVERLAY_FACTORY_ANNOTATION
                )
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
            case INTERFACE:
                if (element.getAnnotationMirrors().stream()
                        .anyMatch(mirror -> ClassNames.JSON_OVERLAY_FACTORY_ANNOTATION.equals(AnnotationSpec.get(mirror).type))) {
                    return element.getEnclosedElements().stream()
                        .filter(method -> ElementKind.METHOD.equals(method.getKind()))
                        .map(TypeMapper::asExecutable)
                        .filter(method -> !method.getModifiers().contains(Modifier.STATIC))
                        .map(this::getClasses)
                        .flatMap(Collection::stream)
                        .collect(Collectors.toSet());
                }
            default:
                emitError("Unkown element kind " + element.getKind(), element);
                return Collections.emptySet();
        }
    }

    private void emitError(String message, Element element) {
        messager.printMessage(Kind.ERROR, message, element);
        throw new IllegalStateException(message);
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

    private Set<String> getClasses(ExecutableElement method) {
        if (method.getParameters().size() == 1) {
            TypeName returnType = TypeName.get(method.getReturnType());
            if (!ClassName.OBJECT.equals(returnType) && !ClassNames.GWT_JAVASCRIPTOBJECT.equals(returnType)) {
                return Collections.singleton(returnType.toString());
            }

            TypeName parameter = TypeName.get(method.getParameters().iterator().next().asType());
            if (!ClassName.OBJECT.equals(parameter) && !ClassNames.GWT_JAVASCRIPTOBJECT.equals(parameter)) {
                return Collections.singleton(parameter.toString());
            }
        }
        return Collections.emptySet();
    }

    private void generateOverlays(Element parent, ModelBuilder builder) {

        String packageName;
        if (ElementKind.INTERFACE.equals(parent.getKind())) {
            JavaFactory factory = createFactory(TypeMapper.asType(parent));
            packageName = factory.getType().packageName();
            TypeSpec overlay = new OverlayFactoryGenerator(packageName).generateOverlay(factory);
            JavaFile javaFile = JavaFile.builder(packageName, overlay)
                    .skipJavaLangImports(true)
                    .build();
            try {
                javaFile.writeTo(this.processingEnv.getFiler());
            } catch (IOException e) {
                emitError("Unable to write file: " + e.getMessage(), parent);
            }
        } else {
            packageName = parent.toString();
        }

        for (JavaInterface javaInterface : builder.getJavaInterfaces()) {
            TypeSpec overlay = new OverlayGenerator(packageName).generateOverlay(javaInterface);

            JavaFile javaFile = JavaFile.builder(packageName, overlay)
                    .skipJavaLangImports(true)
                    .build();
            try {
                javaFile.writeTo(this.processingEnv.getFiler());
            } catch (IOException e) {
                emitError("Unable to write file: " + e.getMessage(), parent);
            }
        }
    }

    private JavaFactory createFactory(TypeElement type) {
        return JavaFactory.builder()
                .type(ClassName.get(type))
                .addAllConvertMethods(type.getEnclosedElements().stream()
                        .filter(method -> ElementKind.METHOD.equals(method.getKind()))
                        .map(TypeMapper::asExecutable)
                        .filter(method -> !method.getModifiers().contains(Modifier.STATIC))
                        .filter(method -> method.getParameters().size() == 1)
                        .map(this::createConvertMethod)
                        .collect(Collectors.toList()))
                .build();
    }

    private JavaConvertMethod createConvertMethod(ExecutableElement method) {
        TypeName returnType = TypeName.get(method.getReturnType());
        TypeName parameter = TypeName.get(method.getParameters().iterator().next().asType());
        return JavaConvertMethod.builder()
                .methodName(method.getSimpleName().toString())
                .returnType((ClassName) returnType)
                .argumentType((ClassName) parameter)
                .build();
    }

}

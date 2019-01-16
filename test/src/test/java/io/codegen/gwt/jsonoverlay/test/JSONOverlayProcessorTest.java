package io.codegen.gwt.jsonoverlay.test;

import static com.google.testing.compile.CompilationSubject.*;
import static com.google.testing.compile.Compiler.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.Compiler;
import com.google.testing.compile.JavaFileObjects;

import io.codegen.gwt.jsonoverlay.processor.JSONOverlayProcessor;

public class JSONOverlayProcessorTest {

    Compiler compiler;

    @Before
    public void setUp() {
        compiler = javac().withProcessors(new JSONOverlayProcessor());
    }

    @Test
    public void testProcessor() throws IOException {
        Compilation compilation = compiler.compile(
                JavaFileObjects.forResource("test/overlay/package-info.java"),
                JavaFileObjects.forResource("test/BasicType.java"));

        assertThat(compilation).succeeded();

        assertThat(compilation).generatedSourceFile("test/overlay/BasicType_JSONOverlay")
            .hasSourceEquivalentTo(JavaFileObjects.forResource("test/overlay/BasicType_JSONOverlay.java"));

        assertThat(compilation).generatedSourceFile("test/overlay/StringType_JSONOverlay")
            .hasSourceEquivalentTo(JavaFileObjects.forResource("test/overlay/StringType_JSONOverlay.java"));
    }

    @Test
    public void testInheritance() throws IOException {
        Compilation compilation = compiler.compile(
                JavaFileObjects.forResource("test/inheritance/package-info.java"),
                JavaFileObjects.forResource("test/InheritanceContainer.java"),
                JavaFileObjects.forResource("test/InheritanceSubA.java"),
                JavaFileObjects.forResource("test/InheritanceSubB.java"),
                JavaFileObjects.forResource("test/InheritanceSuper.java"));

        assertThat(compilation).succeeded();

        assertThat(compilation).generatedSourceFile("test/inheritance/InheritanceContainer_JSONOverlay")
            .hasSourceEquivalentTo(JavaFileObjects.forResource("test/inheritance/InheritanceContainer_JSONOverlay.java"));

        assertThat(compilation).generatedSourceFile("test/inheritance/InheritanceSuper_JSONOverlay")
            .hasSourceEquivalentTo(JavaFileObjects.forResource("test/inheritance/InheritanceSuper_JSONOverlay.java"));

        assertThat(compilation).generatedSourceFile("test/inheritance/InheritanceSubA_JSONOverlay")
            .hasSourceEquivalentTo(JavaFileObjects.forResource("test/inheritance/InheritanceSubA_JSONOverlay.java"));

        assertThat(compilation).generatedSourceFile("test/inheritance/InheritanceSubB_JSONOverlay")
            .hasSourceEquivalentTo(JavaFileObjects.forResource("test/inheritance/InheritanceSubB_JSONOverlay.java"));

    }

    @Test
    public void testMap() throws IOException {
        Compilation compilation = compiler.compile(
                JavaFileObjects.forResource("test/map/package-info.java"),
                JavaFileObjects.forResource("test/MapType.java"),
                JavaFileObjects.forResource("test/StringType.java"));

        assertThat(compilation).succeeded();

        assertThat(compilation).generatedSourceFile("test/map/MapType_JSONOverlay")
            .hasSourceEquivalentTo(JavaFileObjects.forResource("test/map/MapType_JSONOverlay.java"));

        assertThat(compilation).generatedSourceFile("test/map/StringType_JSONOverlay")
            .hasSourceEquivalentTo(JavaFileObjects.forResource("test/map/StringType_JSONOverlay.java"));
    }

    @Test
    public void testFactory() throws IOException {
        Compilation compilation = compiler.compile(
                JavaFileObjects.forResource("test/overlay/OverlayFactory.java"),
                JavaFileObjects.forResource("test/BasicType.java"));

        assertThat(compilation).succeeded();

        assertThat(compilation).generatedSourceFile("test/overlay/BasicType_JSONOverlay")
            .hasSourceEquivalentTo(JavaFileObjects.forResource("test/overlay/BasicType_JSONOverlay.java"));

        assertThat(compilation).generatedSourceFile("test/overlay/StringType_JSONOverlay")
            .hasSourceEquivalentTo(JavaFileObjects.forResource("test/overlay/StringType_JSONOverlay.java"));

        assertThat(compilation).generatedSourceFile("test/overlay/OverlayFactory_JSONOverlayFactory")
            .hasSourceEquivalentTo(JavaFileObjects.forResource("test/overlay/OverlayFactory_JSONOverlayFactory.java"));
    }

    @Test
    public void testSetter() throws IOException {
        Compilation compilation = compiler.compile(
                JavaFileObjects.forResource("test/setter/package-info.java"),
                JavaFileObjects.forResource("test/SetterType.java")
                );

        assertThat(compilation).succeeded();

        assertThat(compilation).generatedSourceFile("test/setter/SetterType_JSONOverlay")
            .hasSourceEquivalentTo(JavaFileObjects.forResource("test/setter/SetterType_JSONOverlay.java"));

        assertThat(compilation).generatedSourceFile("test/setter/StringType_JSONOverlay")
            .hasSourceEquivalentTo(JavaFileObjects.forResource("test/setter/StringType_JSONOverlay.java"));
    }

    @Test
    public void testPrimitiveGetters() throws IOException {
        Compilation compilation = compiler.compile(
                JavaFileObjects.forResource("test/primitives/package-info.java"),
                JavaFileObjects.forResource("test/PrimitiveGettersType.java")
                );

        assertThat(compilation).succeeded();

        assertThat(compilation).generatedSourceFile("test/primitives/PrimitiveGettersType_JSONOverlay")
            .hasSourceEquivalentTo(JavaFileObjects.forResource("test/primitives/PrimitiveGettersType_JSONOverlay.java"));

    }


    @Test
    public void testBoxedGetters() throws IOException {
        Compilation compilation = compiler.compile(
                JavaFileObjects.forResource("test/boxed/package-info.java"),
                JavaFileObjects.forResource("test/BoxedGettersType.java")
                );

        assertThat(compilation).succeeded();

        assertThat(compilation).generatedSourceFile("test/boxed/BoxedGettersType_JSONOverlay")
            .hasSourceEquivalentTo(JavaFileObjects.forResource("test/boxed/BoxedGettersType_JSONOverlay.java"));

    }

}

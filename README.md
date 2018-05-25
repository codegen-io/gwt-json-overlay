# GWT JSON overlay generator

[![Maven Central](https://img.shields.io/maven-central/v/io.codegen.gwt-json-overlay/gwt-json-overlay-processor.svg)](https://mvnrepository.com/artifact/io.codegen.gwt-json-overlay/gwt-json-overlay-processor)
[![Travis](https://img.shields.io/travis/codegen-io/gwt-json-overlay.svg)](https://travis-ci.org/codegen-io/gwt-json-overlay)

The GWT JSON overlay generator project enables one to automatically generate overlay classes which wrap JavaScript
objects in a Java model. The overlay classes are compatible with [GWT](http://www.gwtproject.org) and allow the
creation of unit tests in a JVM environment.

## Installation

You will need to include `gwt-json-overlay-annotations-x.y.z.jar` in your build classpath at compile time,
`gwt-json-overlay-runtime-x.y.z.jar` to the classpath at runtime and add
`gwt-json-overlay--processor-x.y.z.jar` to the annotation path in order to activate the annotation processor and
generate the overlay implementations.

### Maven

In a Maven project, include the `gwt-json-overlay-annotations` and `gwt-json-overlay-runtime` artifacts in the
dependencies section of your `pom.xml` and the `gwt-json-overlay-processor` artifact as an `annotationProcessorPaths`
value of the `maven-compiler-plugin`:

```xml
<dependencies>
  <dependency>
    <groupId>io.codegen.gwt-json-overlay</groupId>
    <artifactId>gwt-json-overlay-annotations</artifactId>
    <version>x.y.z</version>
    <scope>compile</scope>
  </dependency>
  <dependency>
    <groupId>io.codegen.gwt-json-overlay</groupId>
    <artifactId>gwt-json-overlay-runtime</artifactId>
    <version>x.y.z</version>
  </dependency>
</dependencies>
<build>
  <plugins>
    <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-compiler-plugin</artifactId>
      <version>3.6.1</version>
      <configuration>
        <annotationProcessorPaths>
          <path>
            <groupId>io.codegen.gwt-json-overlay</groupId>
            <artifactId>gwt-json-overlay-processor</artifactId>
            <version>x.y.z</version>
          </path>
        </annotationProcessorPaths>
      </configuration>
    </plugin>
  </plugins>
</build>
```

package test;

import java.util.List;
import java.util.Optional;

public interface BasicType {

    String getString();

    int getInt();

    boolean isBoolean();

    Long getBoxedLong();

    StringType getStringType();

    List<String> getStringList();

    List<StringType> getStringTypeList();

    Optional<String> getOptionalString();

    Optional<StringType> getOptionalStringType();

    SomeEnum getEnumType();

    // Static methods should be ignored
    static String getStaticString() {
        return "static string";
    }

    // Default methods should be ignored
    default String getDefaultString() {
        return "default string";
    }

    // Nested classes should be ignored
    class NestedClass {}

}

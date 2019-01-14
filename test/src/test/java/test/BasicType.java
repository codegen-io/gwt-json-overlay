package test;

import java.util.List;
import java.util.Optional;

public interface BasicType {

    String getString();

    int getInt();

    Long getBoxedLong();

    StringType getStringType();

    List<String> getStringList();

    List<StringType> getStringTypeList();

    Optional<String> getOptionalString();

    Optional<StringType> getOptionalStringType();

    SomeEnum getEnumType();

}

package test;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface SetterType {

    String getString();

    int getInt();

    Long getBoxedLong();

    StringType getStringType();

    List<String> getStringList();

    List<StringType> getStringTypeList();

    Optional<String> getOptionalString();

    Optional<StringType> getOptionalStringType();

    SomeEnum getEnumType();

    Map<String, StringType> getStringMap();

    void setString(String value);

    void setInt(int value);

    void setBoxedLong(Long value);

    void setStringType(StringType value);

    void setStringList(List<String> value);

    void setStringTypeList(List<StringType> value);

    void setOptionalString(Optional<String> value);

    void setOptionalStringType(Optional<StringType> value);

    void setEnumType(SomeEnum value);

    void setStringMap(Map<String, StringType> value);

    SetterType string(String value);
}

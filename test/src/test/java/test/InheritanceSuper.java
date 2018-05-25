package test;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "kind")
@JsonSubTypes({
    @JsonSubTypes.Type(value = InheritanceSubA.class, name = "a"),
    @JsonSubTypes.Type(value = InheritanceSubB.class, name = "b"),
})
public interface InheritanceSuper {

    String getKind();

}

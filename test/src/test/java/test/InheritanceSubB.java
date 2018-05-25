package test;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("b")
public interface InheritanceSubB extends InheritanceSuper {

    String getB();

    String getKind();

}

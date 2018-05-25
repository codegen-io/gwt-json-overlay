package test;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("a")
public interface InheritanceSubA extends InheritanceSuper {

    String getA();

}

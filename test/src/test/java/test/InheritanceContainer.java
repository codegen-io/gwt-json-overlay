package test;

import java.util.List;
import java.util.Optional;

public interface InheritanceContainer {

    InheritanceSuper getSomeKindOfInstances();

    Optional<InheritanceSuper> getPossibleKindOfInstances();

    List<InheritanceSuper> getAllKindsOfInstances();

}

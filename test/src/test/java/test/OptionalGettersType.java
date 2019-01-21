package test;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;

public interface OptionalGettersType {

    Optional<String> getOptionalString();

    OptionalInt getOptionalInt();

    OptionalLong getOptionalLong();

    OptionalDouble getOptionalDouble();

    void setOptionalString(Optional<String> value);

    void setOptionalInt(OptionalInt value);

    void setOptionalLong(OptionalLong value);

    void setOptionalDouble(OptionalDouble value);

}

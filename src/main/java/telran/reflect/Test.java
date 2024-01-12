package telran.reflect;

import java.lang.annotation.*;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME) // Specify the retention policy
@Target(METHOD)
public @interface Test {
}

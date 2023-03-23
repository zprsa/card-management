package hr.task.cardmanagement.ex;

import java.util.function.Supplier;

public class ExceptionUtils {

    public static Supplier<PersonNotFoundException> personNotFound(String oib) {
        return () -> new PersonNotFoundException(String.format("Person with oib of %s does not exist", oib));
    }
}

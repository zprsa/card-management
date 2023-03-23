package hr.task.cardmanagement.ex;

public class PersonAlreadyExistsException extends RuntimeException {

    public PersonAlreadyExistsException(String message) {
        super(message);
    }
}

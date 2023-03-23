package hr.task.cardmanagement.ex;

public class CardFileHandlingException extends RuntimeException {

    public CardFileHandlingException(String message, Throwable cause) {
        super(message, cause);
    }

    public CardFileHandlingException(String message) {
        super(message);
    }
}


package hr.task.cardmanagement.ex;

public class CardFileAlreadyExistsException extends RuntimeException{

    public CardFileAlreadyExistsException(String message) {
        super(message);
    }
}

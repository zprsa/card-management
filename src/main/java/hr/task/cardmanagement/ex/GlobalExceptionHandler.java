package hr.task.cardmanagement.ex;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final String GLOBAL_EXCEPTION = "Exception: ";
    private static final String GLOBAL_EXCEPTION_WITH_MESSAGE = "Exception: {}, message: {}";

    @ExceptionHandler(PersonNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handlePersonNotFoundException(PersonNotFoundException e) {
        logBasicExceptionInformation(e);
    }

    @ExceptionHandler(PersonAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public void handlePersonAlreadyExistsException(PersonAlreadyExistsException e) {
        logBasicExceptionInformation(e);
    }

    @ExceptionHandler(InvalidPersonException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleInvalidPersonException(InvalidPersonException e) {
        logBasicExceptionInformation(e);
    }

    @ExceptionHandler(CardFileAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public void handleCardFileAlreadyExistsException(CardFileAlreadyExistsException e) {
        logBasicExceptionInformation(e);
    }

    @ExceptionHandler(CardFileHandlingException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleCardFileHandlingException(CardFileHandlingException e) {
        logStackTrace(e);
    }

    private void logBasicExceptionInformation(Exception e) {
        logger.info(GLOBAL_EXCEPTION_WITH_MESSAGE, e.toString(), e.getMessage());
    }

    private void logStackTrace(Exception e) {
        logger.error(GLOBAL_EXCEPTION + e.getMessage() + ". " + e.toString(), e);
    }
}

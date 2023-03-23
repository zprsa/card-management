package hr.task.cardmanagement.controllers.util;

import hr.task.cardmanagement.controllers.pojo.PersonRequest;
import hr.task.cardmanagement.enums.CardStatus;
import hr.task.cardmanagement.ex.InvalidPersonException;
import org.springframework.util.StringUtils;

public class PersonRequestValidator {

    private PersonRequestValidator() {
        //util
    }

    public static void validatePersonRequest(PersonRequest personRequest) {

        if (StringUtils.isEmpty(personRequest)) {
            throw new InvalidPersonException("Invalid person data sent for person creation");
        }

        if (StringUtils.isEmpty(personRequest.getOib()) ||
                StringUtils.isEmpty(personRequest.getName()) ||
                StringUtils.isEmpty(personRequest.getSurname()) ||
                !CardStatus.NEW.name().equals(personRequest.getCardStatus())) {

            throw new InvalidPersonException(String.format("Invalid person data sent for person creation %s", personRequest.toString()));
        }
    }
}

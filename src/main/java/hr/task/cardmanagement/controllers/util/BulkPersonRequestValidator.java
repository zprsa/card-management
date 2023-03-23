package hr.task.cardmanagement.controllers.util;

import hr.task.cardmanagement.controllers.pojo.BulkPersonRequest;
import hr.task.cardmanagement.ex.InvalidPersonException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class BulkPersonRequestValidator {

    private BulkPersonRequestValidator() {
        //util
    }

    public static void validateBulkPersonRequest(BulkPersonRequest bulkPersonRequest) {

        if (StringUtils.isEmpty(bulkPersonRequest) ||
                CollectionUtils.isEmpty(bulkPersonRequest.getPersons()) ||
                bulkPersonRequest.getNumberOfPersons() != bulkPersonRequest.getPersons().size()) {

            throw new InvalidPersonException(String.format("Invalid numberOfRequests in bulk person data"));
        }

        bulkPersonRequest.getPersons().stream().forEach(PersonRequestValidator::validatePersonRequest);
    }
}

package hr.task.cardmanagement.controllers.util;

import hr.task.cardmanagement.controllers.pojo.PersonResponse;
import hr.task.cardmanagement.model.Person;

public class PersonResponseMapper {

    private PersonResponseMapper() {
        //util
    }

    public static PersonResponse mapToPersonResponse(Person person) {
        return PersonResponse.newBuilder()
                .withOib(person.getOib())
                .withName(person.getName())
                .withSurname(person.getSurname())
                .withCardStatus(person.getStatus().name())
                .build();
    }
}

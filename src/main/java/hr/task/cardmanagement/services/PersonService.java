package hr.task.cardmanagement.services;

import hr.task.cardmanagement.controllers.pojo.BulkPersonRequest;
import hr.task.cardmanagement.controllers.pojo.PersonRequest;
import hr.task.cardmanagement.model.Person;

public interface PersonService {

    Person getPersonByOib(String oib);
    void createPerson(PersonRequest personRequest);
    void deletePerson(String oib);
    void createBulkPerson(BulkPersonRequest bulkPersonRequest);
}

package hr.task.cardmanagement.controllers;

import hr.task.cardmanagement.controllers.pojo.BulkPersonRequest;
import hr.task.cardmanagement.controllers.pojo.PersonRequest;
import hr.task.cardmanagement.controllers.pojo.PersonResponse;
import hr.task.cardmanagement.controllers.util.BulkPersonRequestValidator;
import hr.task.cardmanagement.controllers.util.PersonRequestValidator;
import hr.task.cardmanagement.controllers.util.PersonResponseMapper;
import hr.task.cardmanagement.model.Person;
import hr.task.cardmanagement.services.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@Controller
public class PersonController {

    private static final Logger logger = LoggerFactory.getLogger(PersonController.class);

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping(
            path = "/person/{oib}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PersonResponse> getPersonByOib(
            @PathVariable(name = "oib") String oib) {

        logger.debug("Requesting person data for person with oib: {}", oib);

        Person person = personService.getPersonByOib(oib);
        PersonResponse response = PersonResponseMapper.mapToPersonResponse(person);

        return ResponseEntity.ok(response);
    }

    @PostMapping(
            path = "/person",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createPerson(
            @RequestBody PersonRequest personRequest) {

        PersonRequestValidator.validatePersonRequest(personRequest);

        logger.debug("Requesting person creation for person with oib: {}", personRequest.getOib());

        personService.createPerson(personRequest);
        return ResponseEntity.accepted().build();
    }

    @PostMapping(
            path = "/bulk/person",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createBulkPerson(
            @RequestBody BulkPersonRequest bulkPersonRequest) {

        BulkPersonRequestValidator.validateBulkPersonRequest(bulkPersonRequest);

        logger.debug("Requesting bulk person creation for persons with oib: {}", createOibList(bulkPersonRequest));

        personService.createBulkPerson(bulkPersonRequest);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping(
            path = "/person/{oib}"
    )
    public ResponseEntity<Void> deletePerson(
            @PathVariable("oib") String oib) {

        logger.debug("Requesting delete of person with oib: {}", oib);

        personService.deletePerson(oib);
        return ResponseEntity.noContent().build();
    }

    private String createOibList(BulkPersonRequest bulkPersonRequest){
        var oibList = bulkPersonRequest.getPersons()
                .stream()
                .map(PersonRequest::getOib)
                .collect(Collectors.toList());

        return oibList.toString();
    }
}

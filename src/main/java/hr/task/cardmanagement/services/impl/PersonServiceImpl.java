package hr.task.cardmanagement.services.impl;

import hr.task.cardmanagement.controllers.pojo.BulkPersonRequest;
import hr.task.cardmanagement.controllers.pojo.PersonRequest;
import hr.task.cardmanagement.enums.CardFileStatus;
import hr.task.cardmanagement.enums.CardStatus;
import hr.task.cardmanagement.ex.ExceptionUtils;
import hr.task.cardmanagement.ex.PersonAlreadyExistsException;
import hr.task.cardmanagement.ex.PersonNotFoundException;
import hr.task.cardmanagement.model.Person;
import hr.task.cardmanagement.repository.CardFileRepository;
import hr.task.cardmanagement.repository.PersonRepository;
import hr.task.cardmanagement.services.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class PersonServiceImpl implements PersonService {

    private static final Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);

    private final PersonRepository personRepository;
    private final CardFileRepository cardFileRepository;

    public PersonServiceImpl(PersonRepository personRepository, CardFileRepository cardFileRepository) {
        this.personRepository = personRepository;
        this.cardFileRepository = cardFileRepository;
    }

    @Override
    public Person getPersonByOib(String oib) {
        return personRepository.findByOib(oib).orElseThrow(ExceptionUtils.personNotFound(oib));
    }

    @Override
    public void createPerson(PersonRequest personRequest) {

        var oib = personRequest.getOib();
        personRepository.findByOib(oib)
                .ifPresent(p -> {
                    throw new PersonAlreadyExistsException(String.format("Person with oib of %s already exists", oib));
                });

        personRepository.save(mapToPerson(personRequest));
        logger.info("Person with oib: {} was created", oib);
    }

    @Override
    @Transactional
    public void deletePerson(String oib) {

        var person = personRepository.findByOib(oib)
                .orElseThrow(() -> new PersonNotFoundException(String.format("Person with oib of %s does not exist", oib)));

        personRepository.delete(person);
        logger.info("Person with oib: {} was deleted", oib);

        updateCardFileStatus(oib);
        logger.info("Status of card file for person with oib: {} was updated to INACTIVE", oib);
    }

    @Transactional
    @Override
    public void createBulkPerson(BulkPersonRequest bulkPersonRequest) {

        bulkPersonRequest.getPersons()
                .forEach(this::createPerson);

        logger.debug("Creation for persons from bulk is finished");
    }

    private void updateCardFileStatus(String oib) {

        var cardFile = cardFileRepository.findByOibAndStatus(oib, CardFileStatus.CREATED.name());
        cardFile.ifPresent(c -> cardFileRepository.updateStatus(oib, CardFileStatus.INACTIVE.name(), LocalDateTime.now()));
    }

    private Person mapToPerson(PersonRequest personRequest) {

        var person = new Person();
        person.setName(personRequest.getName());
        person.setSurname(personRequest.getSurname());
        person.setOib(personRequest.getOib());
        person.setStatus(CardStatus.valueOf(personRequest.getCardStatus()));
        return person;
    }
}

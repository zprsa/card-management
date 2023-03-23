package hr.task.cardmanagement.services.impl;

import hr.task.cardmanagement.controllers.pojo.CardFileRequest;
import hr.task.cardmanagement.enums.CardFileStatus;
import hr.task.cardmanagement.ex.CardFileAlreadyExistsException;
import hr.task.cardmanagement.ex.CardFileHandlingException;
import hr.task.cardmanagement.model.CardFile;
import hr.task.cardmanagement.model.Person;
import hr.task.cardmanagement.repository.CardFileRepository;
import hr.task.cardmanagement.services.CardFileService;
import hr.task.cardmanagement.services.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class CardFileServiceImpl implements CardFileService {

    private static final Logger logger = LoggerFactory.getLogger(CardFileServiceImpl.class);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss");
    private static final String DELIMITER = ";";
    private static final String CARD_FILE_PREFIX = "card";

    private final CardFileRepository cardFileRepository;
    private final PersonService personService;

    public CardFileServiceImpl(CardFileRepository cardFileRepository, PersonService personService) {
        this.cardFileRepository = cardFileRepository;
        this.personService = personService;
    }

    @Override
    public void createCardFile(CardFileRequest cardFileRequest) {

        var oib = cardFileRequest.getOib();

        var person = personService.getPersonByOib(oib);
        var cardFile = cardFileRepository.findByOibAndStatus(oib, CardFileStatus.CREATED.name());

        if (cardFile.isPresent()) {
            throw new CardFileAlreadyExistsException(String.format("Card file with oib of %s already exists", oib));
        }

        createCardFileAndSaveToDB(person);
        logger.info("Card file for person with oib: {} was created", oib);
    }

    private void createCardFileAndSaveToDB(Person person) {

        var cardFileName = CARD_FILE_PREFIX.concat(person.getOib())
                .concat(getCurrentTimeStampAsString());

        createFileOnFileSystem(cardFileName, person);
        createCardFileRecordInDb(cardFileName, person);

    }

    private void createCardFileRecordInDb(String cardFileName, Person person) {
        cardFileRepository.save(mapToCardFile(cardFileName, person));
    }

    private CardFile mapToCardFile(String cardFileName, Person person) {

        var cardFile = new CardFile();
        cardFile.setName(cardFileName);
        cardFile.setOib(person.getOib());
        cardFile.setStatus(CardFileStatus.CREATED);
        return cardFile;
    }

    private String getCurrentTimeStampAsString() {

        LocalDateTime dateTime = LocalDateTime.now();
        return dateTime.format(DATE_TIME_FORMATTER);
    }

    private void createFileOnFileSystem(String cardFileName, Person person) {

        var oib = person.getOib();

        var content = person.getName()
                .concat(DELIMITER)
                .concat(person.getSurname())
                .concat(DELIMITER)
                .concat(oib)
                .concat(DELIMITER)
                .concat(person.getStatus().name());

        FileWriter fileWriter;

        try {
            fileWriter = new FileWriter(cardFileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(content);
            bufferedWriter.close();

        } catch (IOException e) {
            throw new CardFileHandlingException(String.format("Cannot create card file for oib %s", oib), e);
        }
    }
}

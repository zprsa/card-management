package hr.task.cardmanagement.controllers;

import hr.task.cardmanagement.TestStarter;
import hr.task.cardmanagement.enums.CardFileStatus;
import hr.task.cardmanagement.model.CardFile;
import hr.task.cardmanagement.model.Person;
import hr.task.cardmanagement.repository.CardFileRepository;
import hr.task.cardmanagement.repository.PersonRepository;
import hr.task.cardmanagement.support.ObjectCreator;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CardFileControllerTest extends TestStarter {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private CardFileRepository cardFileRepository;

    @Test
    public void givenPersonExists_whenCreateCardFile_expectCorrect() throws Exception {

        supposePersonExistInDb();

        mvc.perform(
                post("/card-file")
                        .characterEncoding(StandardCharsets.UTF_8.name())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(contentOf("requests/valid/initiate-create-card-file-1.json")))
                .andExpect(status().isAccepted());

        var cardFile = cardFileRepository.findAll().get(0);
        assertThat(cardFile.getStatus().name()).isEqualTo(CardFileStatus.CREATED.name());
    }

    @Test
    public void givenPersonNotExist_whenCreateCardFile_expectNotFound() throws Exception {

        mvc.perform(
                post("/card-file")
                        .characterEncoding(StandardCharsets.UTF_8.name())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(contentOf("requests/valid/initiate-create-card-file-1.json")))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenCardFileExistsInDb_whenCreateCardFile_expectConflict() throws Exception {

        supposePersonExistInDb();
        supposeCreatedCardFileRecordExistsInDb();

        mvc.perform(
                post("/card-file")
                        .characterEncoding(StandardCharsets.UTF_8.name())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(contentOf("requests/valid/initiate-create-card-file-1.json")))
                .andExpect(status().isConflict());
    }

    @Test
    public void givenInvalidRequest_whenCreateCardFile_expectBadRequest() throws Exception {

        mvc.perform(
                post("/card-file")
                        .characterEncoding(StandardCharsets.UTF_8.name())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(contentOf("requests/invalid/initiate-create-card-file-2.json")))
                .andExpect(status().isBadRequest());
    }

    private void supposePersonExistInDb() {

        Person person = new Person(ObjectCreator.MOCK_NAME, ObjectCreator.MOCK_SURNAME, ObjectCreator.MOCK_OIB, ObjectCreator.MOCK_STATUS);
        personRepository.save(person);
    }

    private void supposeCreatedCardFileRecordExistsInDb() {

        CardFile cardFile = new CardFile(ObjectCreator.MOCK_FILE_NAME, ObjectCreator.MOCK_OIB, ObjectCreator.MOCK_FILE_STATUS_CREATED);
        cardFileRepository.save(cardFile);
    }
}

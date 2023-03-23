package hr.task.cardmanagement.controllers;

import hr.task.cardmanagement.TestStarter;
import hr.task.cardmanagement.controllers.pojo.PersonResponse;
import hr.task.cardmanagement.enums.CardFileStatus;
import hr.task.cardmanagement.model.CardFile;
import hr.task.cardmanagement.model.Person;
import hr.task.cardmanagement.repository.CardFileRepository;
import hr.task.cardmanagement.repository.PersonRepository;
import hr.task.cardmanagement.support.ObjectCreator;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PersonControllerTest extends TestStarter {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private CardFileRepository cardFileRepository;

    @Test
    public void givenPersonExists_whenGetPersonByOib_shouldReturnPerson() throws Exception {

        supposePersonExistInDb();

        MvcResult result = mvc.perform(
                get("/person/" + ObjectCreator.MOCK_OIB))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        PersonResponse personResponse = fromJson(result, PersonResponse.class);
        assertThat(personResponse.getName()).isEqualTo(ObjectCreator.MOCK_NAME);
        assertThat(personResponse.getSurname()).isEqualTo(ObjectCreator.MOCK_SURNAME);
        assertThat(personResponse.getOib()).isEqualTo(ObjectCreator.MOCK_OIB);
        assertThat(personResponse.getCardStatus()).isEqualTo(ObjectCreator.MOCK_STATUS.toString());
    }

    @Test
    public void givenPersonNotExist_whenGetPersonByOib_expectNotFound() throws Exception {

        mvc.perform(
                get("/person/" + ObjectCreator.MOCK_OIB))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenValidPerson_whenCreatePerson_expectCorrect() throws Exception {

        mvc.perform(
                post("/person")
                        .characterEncoding(StandardCharsets.UTF_8.name())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(contentOf("requests/valid/initiate-create-person-1.json")))
                .andExpect(status().isAccepted());

        assertThat(personRepository.findAll()).hasSize(1);
    }

    @Test
    public void givenExistingPersonInDb_whenCreatePerson_expectConflict() throws Exception {

        supposePersonExistInDb();

        mvc.perform(
                post("/person")
                        .characterEncoding(StandardCharsets.UTF_8.name())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(contentOf("requests/valid/initiate-create-person-1.json")))
                .andExpect(status().isConflict());

    }

    @Test
    public void givenInvalidPerson_whenCreatePerson_expectBadRequest() throws Exception {

        mvc.perform(
                post("/person")
                        .characterEncoding(StandardCharsets.UTF_8.name())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(contentOf("requests/invalid/initiate-create-person-2.json")))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenValidBulkPerson_whenCreateBulkPerson_expectCorrect() throws Exception {

        mvc.perform(
                post("/bulk/person")
                        .characterEncoding(StandardCharsets.UTF_8.name())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(contentOf("requests/valid/initiate-create-bulk-person-1.json")))
                .andExpect(status().isAccepted());

        assertThat(personRepository.findAll()).hasSize(2);
    }

    @Test
    public void givenInvalidBulkPerson_whenCreateBulkPerson_expectBadRequest() throws Exception {

        mvc.perform(
                post("/bulk/person")
                        .characterEncoding(StandardCharsets.UTF_8.name())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(contentOf("requests/invalid/initiate-create-bulk-person-2.json")))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void given2SamePersonsInBulkPerson_whenCreateBulkPerson_expectConflict() throws Exception {

        mvc.perform(
                post("/bulk/person")
                        .characterEncoding(StandardCharsets.UTF_8.name())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(contentOf("requests/invalid/initiate-create-bulk-person-3.json")))
                .andExpect(status().isConflict());

        assertThat(personRepository.findAll()).isEmpty();
    }

    @Test
    public void givenExistingPersonInDbAndExistingCardFile_whenDeletePerson_expectCorrect() throws Exception {

        supposePersonExistInDb();
        supposeCreatedCardFileRecordExistsInDb();
        mvc.perform(
                delete("/person/" + ObjectCreator.MOCK_OIB))
                .andExpect(status().isNoContent());

        assertThat(personRepository.findAll()).hasSize(0);

        var cardFile = cardFileRepository.findAll().get(0);
        assertThat(cardFile.getStatus().name()).isEqualTo(CardFileStatus.INACTIVE.name());
    }

    @Test
    public void givenPersonNotExist_whenDeletePerson_expectNotFound() throws Exception {

        mvc.perform(
                delete("/person/" + ObjectCreator.MOCK_OIB))
                .andExpect(status().isNotFound());
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

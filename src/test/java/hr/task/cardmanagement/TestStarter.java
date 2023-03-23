package hr.task.cardmanagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.task.cardmanagement.repository.CardFileRepository;
import hr.task.cardmanagement.repository.PersonRepository;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public abstract class TestStarter {

    protected MockMvc mvc;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private CardFileRepository cardFileRepository;

    @Autowired
    protected WebApplicationContext context;

    @Autowired
    protected ObjectMapper objectMapper;

    @Before
    public void setup() {

        personRepository.deleteAll();
        cardFileRepository.deleteAll();
        this.mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    protected String contentOf(String fileName) throws URISyntaxException, IOException {
        return new String(Files.readAllBytes(Paths.get(getClass().getClassLoader()
                .getResource(fileName)
                .toURI())));
    }

    public <T> T fromJson(String content, Class<T> clazz) throws IOException {
        return this.objectMapper.readValue(content, clazz);
    }

    protected <T> T fromJson(MvcResult result, Class<T> clazz) throws IOException {
        return this.objectMapper.readValue(result.getResponse().getContentAsString(), clazz);
    }
}

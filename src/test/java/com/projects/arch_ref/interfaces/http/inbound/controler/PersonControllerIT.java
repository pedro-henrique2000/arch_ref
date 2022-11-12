package com.projects.arch_ref.interfaces.http.inbound.controler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.arch_ref.domain.entity.Gender;
import com.projects.arch_ref.infra.database.model.PersonModel;
import com.projects.arch_ref.infra.database.repositories.jpa.JpaPersonRepository;
import com.projects.arch_ref.interfaces.http.inbound.dto.GenderDTO;
import com.projects.arch_ref.interfaces.http.inbound.dto.PersonRequest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.time.Month;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class PersonControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JpaPersonRepository jpaPersonRepository;

    @Container
    private static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:14.2-alpine");

    @DynamicPropertySource
    static void overrideTestProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @BeforeAll
    static void setupContainer() {
        postgreSQLContainer.start();
    }

    @AfterEach()
    void setup() {
        this.jpaPersonRepository.deleteAll();
    }

    @AfterAll
    static void tearDown() {
        postgreSQLContainer.stop();
    }

    @Test
    void shouldReturn201OnSuccess() throws Exception {
        PersonRequest personRequest = getPersonRequest();

        mockMvc.perform(post("/api/person")
                        .content(objectMapper.writeValueAsString(personRequest))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturn409WhenEmailAlreadyExists() throws Exception {
        PersonModel personModel = new PersonModel();
        personModel.setFirstName("first");
        personModel.setLastName("last");
        personModel.setEmail("pedro@mail.com");
        personModel.setGender(Gender.MALE);
        personModel.setBirthDate(LocalDate.of(1995, Month.SEPTEMBER, 26));

        jpaPersonRepository.save(personModel);

        mockMvc.perform(post("/api/person")
                        .content(objectMapper.writeValueAsString(getPersonRequest()))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isConflict());
    }

    @Test
    void shouldReturn404WhenIdNotFound() throws Exception {
        long id = 9999L;
        mockMvc.perform(get("/api/person/" + id)
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn200WhenIdFound() throws Exception {
        PersonModel personModel = new PersonModel();
        personModel.setFirstName("first");
        personModel.setLastName("last");
        personModel.setEmail("pedro@mail.com");
        personModel.setGender(Gender.MALE);
        personModel.setBirthDate(LocalDate.of(1995, Month.SEPTEMBER, 26));

        PersonModel model = jpaPersonRepository.save(personModel);


        jpaPersonRepository.save(personModel);
        mockMvc.perform(get("/api/person/" + model.getId())
                )
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn200WhenFound() throws Exception {
        PersonModel personModel = new PersonModel();
        personModel.setFirstName("first");
        personModel.setLastName("last");
        personModel.setEmail("pedro@mail.com");
        personModel.setGender(Gender.MALE);
        personModel.setBirthDate(LocalDate.of(1995, Month.SEPTEMBER, 26));

        PersonModel personModel1 = new PersonModel();
        personModel1.setFirstName("second");
        personModel1.setLastName("lastName");
        personModel1.setEmail("henrique@mail.com");
        personModel1.setGender(Gender.MALE);
        personModel1.setBirthDate(LocalDate.of(1995, Month.SEPTEMBER, 26));

        jpaPersonRepository.save(personModel);
        jpaPersonRepository.save(personModel1);

        mockMvc.perform(get("/api/person")
                        .param("firstName", "first")
                        .param("lastName", "last")
                        .param("gender", "MALE")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.persons", hasSize(1)));
    }

    @Test
    void shouldReturn204WhenNotFoundAnyMatch() throws Exception {
        mockMvc.perform(get("/api/person")
                        .param("email", "any@mail.com")
                )
                .andExpect(status().isNoContent());
    }

    private static PersonRequest getPersonRequest() {
        PersonRequest personRequest = new PersonRequest();
        personRequest.setEmail("pedro@mail.com");
        personRequest.setGender(GenderDTO.MALE);
        personRequest.setBirthDate(LocalDate.now().minusYears(18L));
        personRequest.setFirstName("pedro");
        personRequest.setLastName("h");
        return personRequest;
    }

}

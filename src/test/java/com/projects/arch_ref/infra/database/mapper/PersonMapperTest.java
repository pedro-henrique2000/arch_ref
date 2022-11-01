package com.projects.arch_ref.infra.database.mapper;

import com.projects.arch_ref.domain.entity.Gender;
import com.projects.arch_ref.domain.entity.Person;
import com.projects.arch_ref.infra.database.model.PersonModel;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class PersonMapperTest {

    private final PersonMapper personMapper = new PersonMapper();

    @Test
    void shouldConvertToModel() {
        Person person = Person.builder()
                .id(1L)
                .birthDate(LocalDate.of(1995, Month.SEPTEMBER, 26))
                .gender(Gender.MALE)
                .email("any_mail@mail.com")
                .lastName("any_last")
                .firstName("any")
                .build();

        PersonModel personModel = personMapper.toModel(person);

        assertEquals(1L, personModel.getId());
        assertEquals(Gender.MALE, personModel.getGender());
        assertEquals("any_last", personModel.getLastName());
        assertEquals("any_mail@mail.com", personModel.getEmail());
        assertEquals("any", personModel.getFirstName());
        assertEquals(LocalDate.of(1995, Month.SEPTEMBER, 26), personModel.getBirthDate());
    }

    @Test
    void shouldConvertToEntity() {
        PersonModel personModel = new PersonModel();
        personModel.setId(1L);
        personModel.setFirstName("first");
        personModel.setLastName("last");
        personModel.setEmail("mail@mail");
        personModel.setGender(Gender.MALE);
        personModel.setBirthDate(LocalDate.of(1995, Month.SEPTEMBER, 26));

        Person person = personMapper.toEntity(personModel);

        assertEquals(1L, person.getId());
        assertEquals(Gender.MALE, person.getGender());
        assertEquals("last", person.getLastName());
        assertEquals("mail@mail", person.getEmail());
        assertEquals("first", person.getFirstName());
        assertEquals(LocalDate.of(1995, Month.SEPTEMBER, 26), person.getBirthDate());
    }
}

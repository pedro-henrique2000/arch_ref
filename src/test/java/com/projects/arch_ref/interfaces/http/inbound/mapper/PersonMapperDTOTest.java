package com.projects.arch_ref.interfaces.http.inbound.mapper;

import com.projects.arch_ref.domain.entity.Gender;
import com.projects.arch_ref.domain.entity.Person;
import com.projects.arch_ref.interfaces.http.inbound.dto.GenderDTO;
import com.projects.arch_ref.interfaces.http.inbound.dto.PersonRequest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PersonMapperDTOTest {

    private final PersonMapperDTO personMapperDTO = new PersonMapperDTO();

    @Test
    void shouldConvertCorrectly() {
        PersonRequest personRequest = new PersonRequest();
        personRequest.setEmail("valid@mail.com");
        personRequest.setGender(GenderDTO.MALE);
        personRequest.setBirthDate(LocalDate.MIN);
        personRequest.setFirstName("any");
        personRequest.setLastName("any");

        Person person = personMapperDTO.toEntity(personRequest);

        assertEquals("valid@mail.com", person.getEmail());
        assertEquals("any", person.getFirstName());
        assertEquals("any", person.getLastName());
        assertEquals(Gender.MALE, person.getGender());
        assertEquals(LocalDate.MIN, person.getBirthDate());
    }

}

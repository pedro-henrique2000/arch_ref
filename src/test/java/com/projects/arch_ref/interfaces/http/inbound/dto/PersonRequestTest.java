package com.projects.arch_ref.interfaces.http.inbound.dto;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PersonRequestTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void shouldBeInvalidIfNullEnumProvided() {
        PersonRequest personRequest = new PersonRequest();
        personRequest.setEmail("valid@mail.com");
        personRequest.setGender(null);
        personRequest.setBirthDate(LocalDate.MIN);
        personRequest.setFirstName("any");
        personRequest.setLastName("any");

        Set<ConstraintViolation<PersonRequest>> violations = validator.validate(personRequest);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldBeInvalidIfNoFirstNameIsProvided() {
        PersonRequest personRequest = new PersonRequest();
        personRequest.setEmail("valid@mail.com");
        personRequest.setGender(GenderDTO.MALE);
        personRequest.setBirthDate(LocalDate.MIN);
        personRequest.setLastName("any");

        Set<ConstraintViolation<PersonRequest>> violations = validator.validate(personRequest);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldBeInvalidIfNoLastNameIsProvided() {
        PersonRequest personRequest = new PersonRequest();
        personRequest.setEmail("valid@mail.com");
        personRequest.setGender(GenderDTO.MALE);
        personRequest.setBirthDate(LocalDate.MIN);
        personRequest.setFirstName("any");

        Set<ConstraintViolation<PersonRequest>> violations = validator.validate(personRequest);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldBeInvalidIfInvalidEmailIsProvided() {
        PersonRequest personRequest = new PersonRequest();
        personRequest.setEmail("invalid");
        personRequest.setGender(GenderDTO.MALE);
        personRequest.setBirthDate(LocalDate.MIN);
        personRequest.setFirstName("any");
        personRequest.setLastName("any");

        Set<ConstraintViolation<PersonRequest>> violations = validator.validate(personRequest);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldBeInvalidIfNoEmailIsProvided() {
        PersonRequest personRequest = new PersonRequest();
        personRequest.setGender(GenderDTO.MALE);
        personRequest.setBirthDate(LocalDate.MIN);
        personRequest.setFirstName("any");
        personRequest.setLastName("any");

        Set<ConstraintViolation<PersonRequest>> violations = validator.validate(personRequest);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldBeInvalidIfFutureDateIsProvided() {
        PersonRequest personRequest = new PersonRequest();
        personRequest.setEmail("valid@mail.com");
        personRequest.setGender(GenderDTO.MALE);
        personRequest.setBirthDate(LocalDate.MAX);
        personRequest.setFirstName("any");
        personRequest.setLastName("any");

        Set<ConstraintViolation<PersonRequest>> violations = validator.validate(personRequest);

        assertEquals(1, violations.size());
    }

    @Test
    void shouldBeInvalidIfPresentDateIsProvided() {
        PersonRequest personRequest = new PersonRequest();
        personRequest.setEmail("valid@mail.com");
        personRequest.setGender(GenderDTO.MALE);
        personRequest.setBirthDate(LocalDate.now());
        personRequest.setFirstName("any");
        personRequest.setLastName("any");

        Set<ConstraintViolation<PersonRequest>> violations = validator.validate(personRequest);

        assertEquals(1, violations.size());
    }

}

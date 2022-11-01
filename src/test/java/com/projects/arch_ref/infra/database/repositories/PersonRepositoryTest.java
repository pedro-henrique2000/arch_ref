package com.projects.arch_ref.infra.database.repositories;

import com.projects.arch_ref.domain.entity.Person;
import com.projects.arch_ref.infra.database.mapper.PersonMapper;
import com.projects.arch_ref.infra.database.model.PersonModel;
import com.projects.arch_ref.infra.database.repositories.jpa.JpaPersonRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class PersonRepositoryTest {

    @InjectMocks
    private PersonRepository personRepository;

    @Mock
    private JpaPersonRepository jpaPersonRepository;

    @Mock
    private PersonMapper personMapper;

    private Person personToSave;

    private PersonModel personModel;

    private PersonModel savedPersonModel;

    private Person mappedPerson;

    @BeforeEach
    void setup() {
        personToSave = mock(Person.class);
        personModel = mock(PersonModel.class);
        savedPersonModel = mock(PersonModel.class);
        mappedPerson = mock(Person.class);
        when(personMapper.toModel(personToSave)).thenReturn(personModel);
        when(personMapper.toModel(personToSave)).thenReturn(personModel);
        when(jpaPersonRepository.save(personModel)).thenReturn(savedPersonModel);
        when(personMapper.toEntity(savedPersonModel)).thenReturn(mappedPerson);
    }

    @Test
    void shouldCallPersonMapperWithCorrectParam() {
        personRepository.save(personToSave);
        verify(personMapper, times(1)).toModel(personToSave);
    }

    @Test
    void shouldCallJpaPersonRepositoryWithMappedPerson() {
        personRepository.save(personToSave);
        verify(jpaPersonRepository, times(1)).save(personModel);
    }

    @Test
    void shouldCallPersonMapperWithSavedPersonModel() {
        personRepository.save(personToSave);
        verify(personMapper, times(1)).toEntity(savedPersonModel);
    }

    @Test
    void shouldReturnPersonEntityOnSuccess() {
        Person person = assertDoesNotThrow(() -> personRepository.save(personToSave));
        assertNotNull(person);
    }

    @Test
    void shouldThrowsIfJpaRepositorySaveThrows() {
        when(jpaPersonRepository.save(any(PersonModel.class))).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> personRepository.save(personToSave));
    }

    @Test
    void shouldReturnOnUniqueEmailConstraintViolated() {
        DataIntegrityViolationException violationException = mock(DataIntegrityViolationException.class);
        ConstraintViolationException constraintViolationException = mock(ConstraintViolationException.class);
        when(violationException.getCause()).thenReturn(constraintViolationException);
        when(constraintViolationException.getConstraintName()).thenReturn("UNIQUE_EMAIL_INDEX");

        when(jpaPersonRepository.save(any(PersonModel.class))).thenThrow(violationException);
        Person result = personRepository.save(personToSave);

        assertNull(result);
    }

    @Test
    void shouldThrowsIfAnotherConstraintIsViolated() {
        DataIntegrityViolationException violationException = mock(DataIntegrityViolationException.class);
        ConstraintViolationException constraintViolationException = mock(ConstraintViolationException.class);
        when(violationException.getCause()).thenReturn(constraintViolationException);
        when(constraintViolationException.getConstraintName()).thenReturn("ANOTHER_RANDOM");

        when(jpaPersonRepository.save(any(PersonModel.class))).thenThrow(violationException);

        assertThrows(DataIntegrityViolationException.class, () -> personRepository.save(personToSave));
    }

    @Test
    void shouldThrowsOnDataIntegrityViolationException() {
        DataIntegrityViolationException violationException = mock(DataIntegrityViolationException.class);

        when(jpaPersonRepository.save(any(PersonModel.class))).thenThrow(violationException);

        assertThrows(DataIntegrityViolationException.class, () -> personRepository.save(personToSave));
    }
}

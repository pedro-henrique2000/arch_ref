package com.projects.arch_ref.application;

import com.projects.arch_ref.domain.entity.Person;
import com.projects.arch_ref.domain.exceptions.ConflictException;
import com.projects.arch_ref.domain.protocols.IPersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CreatePersonTest {

    @InjectMocks
    private CreatePerson createPerson;

    @Mock
    private IPersonRepository personRepository;

    @BeforeEach
    void setup() {
        Person person = mock(Person.class);
        when(person.getId()).thenReturn(1L);
        when(personRepository.save(any(Person.class))).thenReturn(person);
    }

    @Test
    void shouldCallRepositoryWithCorrectParam() {
        Person person = mock(Person.class);
        createPerson.invoke(person);
        verify(personRepository, times(1)).save(person);
    }

    @Test
    void shouldThrowsIfEmailAlreadyExists() {
        Person person = mock(Person.class);
        when(personRepository.save(person)).thenReturn(null);

        assertThrows(ConflictException.class, () -> createPerson.invoke(person));
    }

    @Test
    void shouldThrowsIfRepositoryThrows() {
        Person person = mock(Person.class);
        when(personRepository.save(person)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> createPerson.invoke(person));
    }

    @Test
    void shouldReturnSavedIdOnSuccess() {
        Person person = mock(Person.class);
        Long savedId = assertDoesNotThrow(() -> createPerson.invoke(person));
        assertEquals(1l, savedId);
    }

}

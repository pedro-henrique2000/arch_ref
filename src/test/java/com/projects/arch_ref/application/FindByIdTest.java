package com.projects.arch_ref.application;

import com.projects.arch_ref.domain.entity.Person;
import com.projects.arch_ref.domain.exceptions.ResourceNotFoundException;
import com.projects.arch_ref.domain.protocols.IPersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FindByIdTest {

    @InjectMocks
    private FindPersonById findPersonById;

    @Mock
    private IPersonRepository personRepository;

    @Test
    void shouldReturnPersonWhenFind() {
        Person person = mock(Person.class);
        when(personRepository.findById(1l)).thenReturn(Optional.of(person));
        Person result = assertDoesNotThrow(() -> findPersonById.invoke(1L));
        assertEquals(person, result);
    }

    @Test
    void shouldThrowResourceNotFoundWhenIdNotFound() {
        when(personRepository.findById(1l)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> findPersonById.invoke(1L));
    }

    @Test
    void shouldThrowIfRepositoryThrows() {
        when(personRepository.findById(anyLong())).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> findPersonById.invoke(1L));
    }

}

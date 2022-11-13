package com.projects.arch_ref.application;

import com.projects.arch_ref.domain.entity.Person;
import com.projects.arch_ref.domain.protocols.IPersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeletePersonTest {

    @InjectMocks
    private DeletePerson deletePerson;

    @Mock
    private IPersonRepository personRepository;

    @Test
    void shouldCallPersonRepository() {
        Person person = mock(Person.class);
        when(personRepository.findById(anyLong())).thenReturn(Optional.of(person));
        deletePerson.invoke(1L);
        verify(personRepository, times(1)).delete(1l);
    }

    @Test
    void shouldNotCallPersonRepositoryDeleteWhenIdNotFound() {
        when(personRepository.findById(1L)).thenReturn(Optional.empty());
        deletePerson.invoke(1L);
        verify(personRepository, times(0)).delete(1L);
    }

}

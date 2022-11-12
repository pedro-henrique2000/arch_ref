package com.projects.arch_ref.application;

import com.projects.arch_ref.domain.entity.search.PersonSearch;
import com.projects.arch_ref.domain.entity.search.PersonSearchResponse;
import com.projects.arch_ref.domain.protocols.IPersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindPersonBySearchTest {

    @InjectMocks
    private FindPersonBySearch findPersonBySearch;

    @Mock
    private IPersonRepository personRepository;

    @Test
    void shouldCallRepositoryWithCorrectParam() {
        PersonSearch personSearch = mock(PersonSearch.class);

        findPersonBySearch.invoke(personSearch);

        verify(personRepository, times(1)).findBySearch(personSearch);
    }

    @Test
    void shouldThrowIfRepositoryThrows() {
        PersonSearch personSearch = mock(PersonSearch.class);

        when(personRepository.findBySearch(personSearch)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> findPersonBySearch.invoke(personSearch));
    }

    @Test
    void shouldReturnResponseOnSuccess() {
        PersonSearch personSearch = mock(PersonSearch.class);
        PersonSearchResponse personSearchResponse = mock(PersonSearchResponse.class);
        when(personRepository.findBySearch(personSearch)).thenReturn(personSearchResponse);
        assertDoesNotThrow(() -> findPersonBySearch.invoke(personSearch));
    }

}

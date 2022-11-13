package com.projects.arch_ref.infra.database.repositories;

import com.projects.arch_ref.domain.entity.Person;
import com.projects.arch_ref.domain.entity.search.PersonSearch;
import com.projects.arch_ref.domain.entity.search.PersonSearchResponse;
import com.projects.arch_ref.domain.entity.search.SortType;
import com.projects.arch_ref.infra.database.mapper.PersonMapper;
import com.projects.arch_ref.infra.database.model.PersonModel;
import com.projects.arch_ref.infra.database.repositories.jpa.JpaPersonRepository;
import com.projects.arch_ref.infra.database.repositories.jpa.JpaPersonSpecification;
import com.projects.arch_ref.infra.database.repositories.jpa.PersonSpecificationBuilder;
import com.projects.arch_ref.infra.database.repositories.jpa.SearchCriteria;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.cloud.context.named.NamedContextFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

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

    @Mock
    private PersonSpecificationBuilder personSpecificationBuilder;

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

    @Test
    void shouldReturnEmptyWhenIdNotFound() {
        when(jpaPersonRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Person> personOptional = personRepository.findById(1L);

        assertFalse(personOptional.isPresent());
    }

    @Test
    void shouldReturnPersonWhenIdExists() {
        PersonModel foundPerson = mock(PersonModel.class);
        Person person = mock(Person.class);

        when(jpaPersonRepository.findById(1L)).thenReturn(Optional.of(foundPerson));
        when(personMapper.toEntity(foundPerson)).thenReturn(person);

        Optional<Person> personOptional = personRepository.findById(1L);

        assertTrue(personOptional.isPresent());
        assertEquals(person, personOptional.get());
    }

    @Test
    void shouldReturnPersonSearchResponse() {
        PersonSearch personSearch = mock(PersonSearch.class);
        SearchCriteria searchCriteria = mock(SearchCriteria.class);
        List<SearchCriteria> searchCriteriaList = List.of(searchCriteria);
        PersonModel personModell = mock(PersonModel.class);
        Person entity = mock(Person.class);
        PageImpl<PersonModel> personModels = new PageImpl<>(List.of(personModell));

        when(personSpecificationBuilder.buildSearchCriterias(personSearch)).thenReturn(searchCriteriaList);
        when(personSearch.getSortType()).thenReturn(SortType.ASC);
        when(personSearch.getPage()).thenReturn(0);
        when(personSearch.getLimit()).thenReturn(10);
        when(jpaPersonRepository.findAll(any(JpaPersonSpecification.class), any(Pageable.class))).thenReturn(personModels);
        when(personMapper.toEntity(personModell)).thenReturn(entity);

        PersonSearchResponse response = personRepository.findBySearch(personSearch);

        assertEquals(entity, response.getPersons().get(0));
    }

    @Test
    void shouldCallJpaRepository() {
        personRepository.delete(1L);
        verify(jpaPersonRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldThrowIfDeleteThrows() {
        doThrow(new RuntimeException()).when(jpaPersonRepository).deleteById(anyLong());
        assertThrows(RuntimeException.class, () -> personRepository.delete(1L));
    }
}

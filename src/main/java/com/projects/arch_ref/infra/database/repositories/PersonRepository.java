package com.projects.arch_ref.infra.database.repositories;

import com.projects.arch_ref.domain.entity.Person;
import com.projects.arch_ref.domain.entity.search.PersonSearch;
import com.projects.arch_ref.domain.entity.search.PersonSearchResponse;
import com.projects.arch_ref.domain.protocols.IPersonRepository;
import com.projects.arch_ref.infra.database.mapper.PersonMapper;
import com.projects.arch_ref.infra.database.model.PersonModel;
import com.projects.arch_ref.infra.database.repositories.jpa.JpaPersonRepository;
import com.projects.arch_ref.infra.database.repositories.jpa.JpaPersonSpecification;
import com.projects.arch_ref.infra.database.repositories.jpa.PersonSpecificationBuilder;
import com.projects.arch_ref.infra.database.repositories.jpa.SearchCriteria;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
@Slf4j
public class PersonRepository implements IPersonRepository {

    public static final String FIRST_NAME = "firstName";
    private final PersonSpecificationBuilder personSpecificationBuilder;
    private final JpaPersonRepository jpaPersonRepository;
    private final PersonMapper personMapper;

    @Override
    public Person save(Person person) {
        PersonModel personModel = personMapper.toModel(person);
        try {
            log.info("saving person with email {}", person.getEmail());
            PersonModel savedPerson = jpaPersonRepository.save(personModel);
            return personMapper.toEntity(savedPerson);
        } catch (DataIntegrityViolationException violationException) {
            if (violationException.getCause() instanceof ConstraintViolationException exception) {
                if (exception.getConstraintName().toLowerCase().contains("unique_email")) {
                    return null;
                }
            }
            log.error("error ocurred while saving person. Message: [{}]", violationException.getMessage());
            throw violationException;
        } catch (Exception exception) {
            log.error("unexpected error occurred while saving person. Message: [{}]", exception.getMessage());
            throw exception;
        }
    }

    @Override
    public Optional<Person> findById(Long id) {
        Optional<PersonModel> personModelOptional = jpaPersonRepository.findById(id);
        if (personModelOptional.isEmpty()) {
            return Optional.empty();
        }
        Person person = personMapper.toEntity(personModelOptional.get());
        return Optional.of(person);
    }

    @Override
    public PersonSearchResponse findBySearch(PersonSearch personSearch) {
        List<SearchCriteria> searchCriteria = getSearchCriteria(personSearch);
        Pageable pageable = getPageable(personSearch);
        Page<PersonModel> result = this.jpaPersonRepository.findAll(new JpaPersonSpecification(searchCriteria), pageable);
        List<Person> persons = result.getContent().stream().map(personMapper::toEntity).toList();
        return new PersonSearchResponse(persons, result.getTotalPages(), result.getTotalElements());
    }

    private static Pageable getPageable(PersonSearch personSearch) {
        Sort.Direction direction = Sort.Direction.fromString(personSearch.getSortType().getValue());
        Sort sort = Sort.by(direction, FIRST_NAME);
        Pageable pageable = PageRequest.of(personSearch.getPage(), personSearch.getLimit(), sort);
        return pageable;
    }

    private List<SearchCriteria> getSearchCriteria(PersonSearch personSearch) {
        return personSpecificationBuilder.buildSearchCriterias(personSearch);
    }
}

package com.projects.arch_ref.infra.database.repositories;

import com.projects.arch_ref.domain.entity.Person;
import com.projects.arch_ref.domain.protocols.IPersonRepository;
import com.projects.arch_ref.infra.database.mapper.PersonMapper;
import com.projects.arch_ref.infra.database.model.PersonModel;
import com.projects.arch_ref.infra.database.repositories.jpa.JpaPersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class PersonRepository implements IPersonRepository {

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
                if (exception.getConstraintName().contains("UNIQUE_EMAIL_INDEX")) {
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
}

package com.projects.arch_ref.application;

import com.projects.arch_ref.domain.entity.Person;
import com.projects.arch_ref.domain.exceptions.ConflictException;
import com.projects.arch_ref.domain.protocols.IPersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class CreatePerson {

    private final IPersonRepository personRepository;

    public Long invoke(Person person) {
        Person savedPerson = personRepository.save(person);
        if (savedPerson == null) {
            throw new ConflictException("There is already a person with email " + person.getEmail());
        }
        log.info("Saved person with ID: {} and Email: {}", savedPerson.getId(), savedPerson.getEmail());
        return savedPerson.getId();
    }
}

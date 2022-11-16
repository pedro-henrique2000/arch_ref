package com.projects.arch_ref.application;

import com.projects.arch_ref.domain.entity.Person;
import com.projects.arch_ref.domain.protocols.IPersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class DeletePerson {

    private final IPersonRepository personRepository;

    public void invoke(Long id) {
        log.info("DeletePerson:invoke execution started {}", id);
        Optional<Person> person = personRepository.findById(id);
        if (person.isEmpty()) {
            log.debug("Not found person with id {} to delete", id);
            return;
        }
        personRepository.delete(id);
        log.info("DeletePerson:invoke execution concluded {}", id);
    }

}

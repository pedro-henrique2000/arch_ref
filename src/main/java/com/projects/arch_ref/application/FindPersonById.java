package com.projects.arch_ref.application;

import com.projects.arch_ref.domain.entity.Person;
import com.projects.arch_ref.domain.exceptions.ResourceNotFoundException;
import com.projects.arch_ref.domain.protocols.IPersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class FindPersonById {

    private final IPersonRepository personRepository;

    public Person invoke(Long id) {
        log.info("FindPersonById:invoke execution started {}", id);
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found person with id " + id));
        log.debug("FindPersonById:invoke found [{}]", person);
        log.info("FindPersonById:invoke execution concluded {}", id);
        return person;
    }

}

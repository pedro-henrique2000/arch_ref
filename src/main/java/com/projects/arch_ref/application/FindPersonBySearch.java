package com.projects.arch_ref.application;

import com.projects.arch_ref.domain.entity.search.PersonSearch;
import com.projects.arch_ref.domain.entity.search.PersonSearchResponse;
import com.projects.arch_ref.domain.protocols.IPersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class FindPersonBySearch {

    private final IPersonRepository personRepository;

    public PersonSearchResponse invoke(PersonSearch personSearch) {
        log.info("FindPersonBySearch:invoke execution started. parameter {}", personSearch);
        PersonSearchResponse bySearch = personRepository.findBySearch(personSearch);
        log.info("FindPersonBySearch:invoke execution finished. response {}", bySearch);
        return bySearch;
    }

}

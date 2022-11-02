package com.projects.arch_ref.domain.protocols;

import com.projects.arch_ref.domain.entity.Person;

import java.util.Optional;

public interface IPersonRepository {
    Person save(Person person);
    Optional<Person> findById(Long id);
}

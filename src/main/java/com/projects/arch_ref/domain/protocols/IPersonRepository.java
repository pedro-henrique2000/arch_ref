package com.projects.arch_ref.domain.protocols;

import com.projects.arch_ref.domain.entity.Person;

public interface IPersonRepository {
    Person save(Person person);
}

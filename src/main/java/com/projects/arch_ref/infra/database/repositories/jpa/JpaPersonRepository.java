package com.projects.arch_ref.infra.database.repositories.jpa;

import com.projects.arch_ref.infra.database.model.PersonModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPersonRepository extends JpaRepository<PersonModel, Long> { }

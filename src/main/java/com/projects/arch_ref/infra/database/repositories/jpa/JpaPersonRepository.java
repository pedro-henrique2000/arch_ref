package com.projects.arch_ref.infra.database.repositories.jpa;

import com.projects.arch_ref.infra.database.model.PersonModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JpaPersonRepository extends JpaRepository<PersonModel, Long>, JpaSpecificationExecutor<PersonModel> { }

package com.projects.arch_ref.interfaces.http.inbound.controler.api;

import com.projects.arch_ref.domain.entity.Person;
import com.projects.arch_ref.interfaces.http.inbound.dto.PersonRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("api/person")
public interface PersonAPI {

    @PostMapping
    ResponseEntity<Void> create(@Valid @RequestBody PersonRequest personRequest);

    @GetMapping("/{id}")
    ResponseEntity<Person> findById(@PathVariable Long id);

}

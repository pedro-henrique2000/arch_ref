package com.projects.arch_ref.interfaces.http.inbound.controler.api;

import com.projects.arch_ref.interfaces.http.inbound.dto.PersonRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("api/person")
public interface PersonAPI {

    @PostMapping
    ResponseEntity<Void> create(@Valid @RequestBody PersonRequest personRequest);

}

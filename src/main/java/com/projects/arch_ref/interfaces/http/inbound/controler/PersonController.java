package com.projects.arch_ref.interfaces.http.inbound.controler;

import com.projects.arch_ref.application.CreatePerson;
import com.projects.arch_ref.interfaces.http.inbound.controler.api.PersonAPI;
import com.projects.arch_ref.interfaces.http.inbound.dto.PersonRequest;
import com.projects.arch_ref.interfaces.http.inbound.mapper.PersonMapperDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@Slf4j
public class PersonController implements PersonAPI {

    private final CreatePerson createPerson;
    private final PersonMapperDTO personMapperDto;

    @Override
    public ResponseEntity<Void> create(PersonRequest personRequest) {
        Long id = createPerson.invoke(personMapperDto.toEntity(personRequest));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(location).build();
    }
}

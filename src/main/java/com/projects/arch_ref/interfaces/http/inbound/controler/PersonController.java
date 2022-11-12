package com.projects.arch_ref.interfaces.http.inbound.controler;

import com.projects.arch_ref.application.CreatePerson;
import com.projects.arch_ref.application.FindPersonById;
import com.projects.arch_ref.application.FindPersonBySearch;
import com.projects.arch_ref.domain.entity.Person;
import com.projects.arch_ref.domain.entity.search.PersonSearch;
import com.projects.arch_ref.domain.entity.search.PersonSearchResponse;
import com.projects.arch_ref.interfaces.http.inbound.controler.api.PersonAPI;
import com.projects.arch_ref.interfaces.http.inbound.dto.PersonRequest;
import com.projects.arch_ref.interfaces.http.inbound.dto.PersonSearchDTO;
import com.projects.arch_ref.interfaces.http.inbound.mapper.PersonMapperDTO;
import com.projects.arch_ref.interfaces.http.inbound.mapper.PersonSearchMapperDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@Slf4j
public class PersonController implements PersonAPI {

    private final FindPersonBySearch findPersonBySearch;
    private final FindPersonById findPersonById;
    private final CreatePerson createPerson;
    private final PersonMapperDTO personMapperDto;
    private final PersonSearchMapperDTO personSearchMapperDTO;

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

    @Override
    public ResponseEntity<Person> findById(Long id) {
        return ResponseEntity.ok(findPersonById.invoke(id));
    }

    @Override
    public ResponseEntity<PersonSearchResponse> findBySearch(PersonSearchDTO dto) {
        PersonSearch personSearch = personSearchMapperDTO.toEntity(dto);
        PersonSearchResponse searchResponse = this.findPersonBySearch.invoke(personSearch);
        HttpStatus status = searchResponse.getPersons().isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return ResponseEntity.status(status.value()).body(searchResponse);
    }
}

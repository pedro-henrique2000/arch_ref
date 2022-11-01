package com.projects.arch_ref.interfaces.http.inbound.mapper;

import com.projects.arch_ref.domain.entity.Person;
import com.projects.arch_ref.interfaces.http.inbound.dto.PersonRequest;
import org.springframework.stereotype.Component;

@Component
public class PersonMapperDTO {

    public Person toEntity(PersonRequest personRequest) {
        return Person.builder()
                .email(personRequest.getEmail())
                .firstName(personRequest.getFirstName())
                .lastName(personRequest.getLastName())
                .email(personRequest.getEmail())
                .gender(personRequest.getGender().toEntity())
                .birthDate(personRequest.getBirthDate())
                .build();
    }

}

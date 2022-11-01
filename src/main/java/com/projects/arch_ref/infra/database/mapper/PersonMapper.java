package com.projects.arch_ref.infra.database.mapper;

import com.projects.arch_ref.domain.entity.Person;
import com.projects.arch_ref.infra.database.model.PersonModel;
import org.springframework.stereotype.Component;

@Component
public class PersonMapper {

    public PersonModel toModel(Person person) {
        PersonModel personModel = new PersonModel();
        personModel.setId(person.getId());
        personModel.setEmail(person.getEmail());
        personModel.setGender(person.getGender());
        personModel.setLastName(person.getLastName());
        personModel.setFirstName(person.getFirstName());
        personModel.setBirthDate(person.getBirthDate());
        return personModel;
    }

    public Person toEntity(PersonModel model) {
        return Person.builder()
                .id(model.getId())
                .email(model.getEmail())
                .firstName(model.getFirstName())
                .lastName(model.getLastName())
                .email(model.getEmail())
                .gender(model.getGender())
                .birthDate(model.getBirthDate())
                .build();
    }

}

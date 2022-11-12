package com.projects.arch_ref.domain.entity.search;

import com.projects.arch_ref.domain.entity.Person;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PersonSearchResponse {

    private List<Person> persons;
    private int page;
    private long total;

}

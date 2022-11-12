package com.projects.arch_ref.domain.entity.search;

import com.projects.arch_ref.domain.entity.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PersonSearch {

    private String firstName;
    private String lastName;
    private String email;
    private Gender gender;
    private int limit = 10;
    private SortType sortType;
    private int page = 0;
}

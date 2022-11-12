package com.projects.arch_ref.interfaces.http.inbound.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PersonSearchDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private int limit = 10;
    private String sortType;
    private int page = 0;
}

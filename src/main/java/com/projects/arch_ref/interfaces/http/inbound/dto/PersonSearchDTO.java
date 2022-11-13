package com.projects.arch_ref.interfaces.http.inbound.dto;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PersonSearchDTO {

    @Parameter(description = "Person First Name")
    private String firstName;
    @Parameter(description = "Person Last Name")
    private String lastName;
    private String email;
    private String gender;
    private int limit = 10;
    private String sortType;
    private int page = 0;
}

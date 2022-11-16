package com.projects.arch_ref.interfaces.http.inbound.dto;

import com.projects.arch_ref.interfaces.http.inbound.dto.validator.ValidEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Getter
@Setter
@ToString
public class PersonRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    @NotBlank
    private String email;

    @Past
    private LocalDate birthDate;

    @ValidEnum(enumType = GenderDTO.class)
    private GenderDTO gender;

}

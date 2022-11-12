package com.projects.arch_ref.interfaces.http.inbound.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
public class ExceptionDetails {

    protected String title;

    protected int status;

    protected String details;

    protected LocalDateTime timestamp;

    protected String developerMessage;

}

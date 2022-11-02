package com.projects.arch_ref.interfaces.http.inbound.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
public class ExceptionDetails {

    protected String title;

    protected int status;

    protected String details;

    protected LocalDateTime timestamp;

    protected String developerMessage;

}

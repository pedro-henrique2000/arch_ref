package com.projects.arch_ref.domain.exceptions;

import lombok.Getter;

@Getter
public class InvalidTypeException extends RuntimeException {
    public InvalidTypeException(String message) {
        super(message);
    }
}

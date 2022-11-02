package com.projects.arch_ref.domain.exceptions;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String message) {
        super(message);
    }
}

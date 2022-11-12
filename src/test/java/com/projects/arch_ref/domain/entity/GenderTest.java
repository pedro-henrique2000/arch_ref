package com.projects.arch_ref.domain.entity;

import com.projects.arch_ref.domain.exceptions.InvalidTypeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenderTest {

    @Test
    void shouldReturnInvalidTypeException() {
        assertThrows(InvalidTypeException.class, () -> Gender.from("any"));
    }

}

package com.projects.arch_ref.domain.entity.search;

import com.projects.arch_ref.domain.entity.Gender;
import com.projects.arch_ref.domain.exceptions.InvalidTypeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SortTypeTest {

    @Test
    void shouldThrowInvalidTypeException() {
        assertThrows(InvalidTypeException.class, () -> SortType.from("any_invalid"));
    }

}

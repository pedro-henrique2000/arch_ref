package com.projects.arch_ref.interfaces.http.inbound.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class GenderDTOTest {

    @Test
    void shouldReturnNullIfInvalidValueProvided() {
        GenderDTO genderDTO = GenderDTO.from("any_invalid");
        assertNull(genderDTO);
    }

    @Test
    void shouldReturnCorrectEnum() {
        GenderDTO genderDTO = GenderDTO.from("female");
        assertEquals(GenderDTO.FEMALE, genderDTO);
    }

}

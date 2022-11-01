package com.projects.arch_ref.interfaces.http.inbound.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.projects.arch_ref.domain.entity.Gender;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum GenderDTO {
    MALE, FEMALE, OTHER;

    public Gender toEntity() {
        return Gender.valueOf(this.name());
    }

    @JsonCreator
    public static GenderDTO from(String value) {
        for (GenderDTO genderDTO : GenderDTO.values()) {
            if (genderDTO.name().equalsIgnoreCase(value)) {
                return genderDTO;
            }
        }
        log.error("Received invalid Gender value. {}", value);
        return null;
    }

}

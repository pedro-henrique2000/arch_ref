package com.projects.arch_ref.domain.entity;

import com.projects.arch_ref.domain.exceptions.InvalidTypeException;

public enum Gender {
    MALE, FEMALE, OTHER;

    public static Gender from(String value) {
        for (Gender gender : Gender.values()) {
            if (gender.name().equalsIgnoreCase(value)) {
                return gender;
            }
        }
        throw new InvalidTypeException("Invalid Gender: " + value);
    }
}

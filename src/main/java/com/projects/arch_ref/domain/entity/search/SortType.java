package com.projects.arch_ref.domain.entity.search;

import com.projects.arch_ref.domain.exceptions.InvalidTypeException;

public enum SortType {
    ASC("asc"), DESC("desc");

    private String value;

    SortType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static SortType from(String value) {
        for (SortType sortType : SortType.values()) {
            if (sortType.name().equalsIgnoreCase(value)) {
                return sortType;
            }
        }
        throw new InvalidTypeException("Invalid Sort Type: " + value);
    }
}

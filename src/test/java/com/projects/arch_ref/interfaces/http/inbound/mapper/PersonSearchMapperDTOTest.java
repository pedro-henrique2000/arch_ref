package com.projects.arch_ref.interfaces.http.inbound.mapper;

import com.projects.arch_ref.domain.entity.Gender;
import com.projects.arch_ref.domain.entity.search.PersonSearch;
import com.projects.arch_ref.domain.entity.search.SortType;
import com.projects.arch_ref.interfaces.http.inbound.dto.PersonSearchDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PersonSearchMapperDTOTest {

    @InjectMocks
    PersonSearchMapperDTO personSearchMapperDTO;

    @Test
    void shouldSetSortTypeDescAsDefault() {
        PersonSearchDTO personSearchDTO = new PersonSearchDTO();
        PersonSearch personSearch = personSearchMapperDTO.toEntity(personSearchDTO);
        assertEquals(SortType.ASC, personSearch.getSortType());
    }

    @Test
    void shouldConvertGenderCorrectly() {
        PersonSearchDTO personSearchDTO = new PersonSearchDTO();
        personSearchDTO.setGender("male");
        PersonSearch personSearch = personSearchMapperDTO.toEntity(personSearchDTO);
        assertEquals(Gender.MALE, personSearch.getGender());
    }

    @Test
    void shouldSetPage0AndLimit10AsDefault() {
        PersonSearchDTO personSearchDTO = new PersonSearchDTO();
        PersonSearch personSearch = personSearchMapperDTO.toEntity(personSearchDTO);
        assertEquals(10, personSearch.getLimit());
        assertEquals(0, personSearch.getPage());
    }

    @Test
    void shouldReturnDescType() {
        PersonSearchDTO personSearchDTO = new PersonSearchDTO();
        personSearchDTO.setSortType("desc");
        PersonSearch personSearch = personSearchMapperDTO.toEntity(personSearchDTO);
        assertEquals(SortType.DESC, personSearch.getSortType());
    }

}

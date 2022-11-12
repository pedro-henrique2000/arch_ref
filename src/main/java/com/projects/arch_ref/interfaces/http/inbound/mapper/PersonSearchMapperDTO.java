package com.projects.arch_ref.interfaces.http.inbound.mapper;

import com.projects.arch_ref.domain.entity.Gender;
import com.projects.arch_ref.domain.entity.search.PersonSearch;
import com.projects.arch_ref.domain.entity.search.SortType;
import com.projects.arch_ref.interfaces.http.inbound.dto.PersonSearchDTO;
import org.springframework.stereotype.Component;

@Component
public class PersonSearchMapperDTO {

    public PersonSearch toEntity(PersonSearchDTO dto) {
        SortType sortType = dto.getSortType() == null ? SortType.ASC : SortType.from(dto.getSortType());
        Gender gender = dto.getGender() == null ? null : Gender.from(dto.getGender());
        PersonSearch personSearch = new PersonSearch();
        personSearch.setEmail(dto.getEmail());
        personSearch.setPage(dto.getPage());
        personSearch.setLimit(dto.getLimit());
        personSearch.setGender(gender);
        personSearch.setLastName(dto.getLastName());
        personSearch.setFirstName(dto.getFirstName());
        personSearch.setSortType(sortType);
        return personSearch;
    }

}

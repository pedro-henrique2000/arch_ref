package com.projects.arch_ref.infra.database.repositories.jpa;

import com.projects.arch_ref.domain.entity.search.PersonSearch;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.projects.arch_ref.infra.database.repositories.jpa.SearchOperation.*;

@Component
public class PersonSpecificationBuilder {

    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String GENDER = "gender";
    private static final String EMAIL = "email";

    public List<SearchCriteria> buildSearchCriterias(final PersonSearch personSearch) {
        List<SearchCriteria> criterias = new ArrayList<>();
        if (personSearch.getFirstName() != null) {
            SearchCriteria nameCriteria = new SearchCriteria(FIRST_NAME, personSearch.getFirstName(), LIKE);
            criterias.add(nameCriteria);
        }
        if (personSearch.getLastName() != null) {
            SearchCriteria priceCriteria = new SearchCriteria(LAST_NAME, personSearch.getLastName(), LIKE);
            criterias.add(priceCriteria);
        }
        if (personSearch.getEmail() != null) {
            SearchCriteria emailCriteria = new SearchCriteria(EMAIL, personSearch.getEmail(), LIKE);
            criterias.add(emailCriteria);
        }
        if (personSearch.getGender() != null) {
            SearchCriteria genderCriteria = new SearchCriteria(GENDER, personSearch.getGender(), EQUAL);
            criterias.add(genderCriteria);
        }
        return criterias;
    }

}

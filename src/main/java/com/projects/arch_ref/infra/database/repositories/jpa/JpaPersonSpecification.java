package com.projects.arch_ref.infra.database.repositories.jpa;

import com.projects.arch_ref.infra.database.model.PersonModel;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class JpaPersonSpecification implements Specification<PersonModel> {

    private List<SearchCriteria> criterias;

    public JpaPersonSpecification(List<SearchCriteria> list) {
        this.criterias = list;
    }

    @Override
    public Predicate toPredicate(Root<PersonModel> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();
        for (SearchCriteria criteria : criterias) {
            switch (criteria.getOperation()) {
                case EQUAL -> predicates.add(builder.equal(root.get(criteria.getKey()), criteria.getValue()));
                case LIKE -> predicates.add(builder.like(builder.lower(root.get(criteria.getKey())), "%" + criteria.getValue().toString().toLowerCase() + "%"));
            }
        }
        return builder.and(predicates.toArray(new Predicate[0]));
    }
}

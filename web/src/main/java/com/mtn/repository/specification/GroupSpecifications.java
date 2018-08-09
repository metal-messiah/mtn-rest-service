package com.mtn.repository.specification;

import com.mtn.model.domain.Group;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by Allen on 4/23/2017.
 */
public class GroupSpecifications extends AuditingEntitySpecifications {

    private static final String DISPLAY_NAME = "displayName";

    public static Specification<Group> displayNameContains(String value) {
        return new Specification<Group>() {
            @Override
            public Predicate toPredicate(Root<Group> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(criteriaBuilder.lower(root.get(DISPLAY_NAME)), String.format("%%%s%%", value.toLowerCase()));
            }
        };
    }

    public static Specification<Group> displayNameEqualsIgnoreCase(String value) {
        return new Specification<Group>() {
            @Override
            public Predicate toPredicate(Root<Group> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(criteriaBuilder.lower(root.get(DISPLAY_NAME)), value.toLowerCase());
            }
        };
    }
}

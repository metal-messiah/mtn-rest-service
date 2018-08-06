package com.mtn.repository.specification;

import com.mtn.model.domain.Role;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by Allen on 4/23/2017.
 */
public class RoleSpecifications extends AuditingEntitySpecifications{

    private static final String DELETED_DATE = "deletedDate";
    private static final String DISPLAY_NAME = "displayName";
    private static final String ID = "id";

    public static Specification<Role> displayNameContains(String value) {
        return new Specification<Role>() {
            @Override
            public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(criteriaBuilder.lower(root.get(DISPLAY_NAME)), String.format("%%%s%%", value.toLowerCase()));
            }
        };
    }

    public static Specification<Role> displayNameEqualsIgnoreCase(String value) {
        return new Specification<Role>() {
            @Override
            public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(criteriaBuilder.lower(root.get(DISPLAY_NAME)), value.toLowerCase());
            }
        };
    }

    public static Specification<Role> isNotAdmin() {
        return new Specification<Role>() {
            @Override
            public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.notEqual(root.get(ID), 1);
            }
        };
    }

}

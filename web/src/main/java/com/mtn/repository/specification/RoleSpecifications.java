package com.mtn.repository.specification;

import com.mtn.model.domain.auth.Role;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by Allen on 4/23/2017.
 */
public class RoleSpecifications {

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

    public static Specification<Role> idEquals(Integer id) {
        return new Specification<Role>() {
            @Override
            public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get(ID), id);
            }
        };
    }

    public static Specification<Role> isNotDeleted() {
        return new Specification<Role>() {
            @Override
            public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.isNull(root.get(DELETED_DATE));
            }
        };
    }

}
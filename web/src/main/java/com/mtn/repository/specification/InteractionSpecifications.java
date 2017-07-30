package com.mtn.repository.specification;

import com.mtn.model.domain.Interaction;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by Allen on 4/23/2017.
 */
public class InteractionSpecifications {

    private static final String DELETED_DATE = "deletedDate";
    private static final String ID = "id";

    public static Specification<Interaction> idEquals(Integer id) {
        return new Specification<Interaction>() {
            @Override
            public Predicate toPredicate(Root<Interaction> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get(ID), id);
            }
        };
    }

    public static Specification<Interaction> isNotDeleted() {
        return new Specification<Interaction>() {
            @Override
            public Predicate toPredicate(Root<Interaction> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.isNull(root.get(DELETED_DATE));
            }
        };
    }
}

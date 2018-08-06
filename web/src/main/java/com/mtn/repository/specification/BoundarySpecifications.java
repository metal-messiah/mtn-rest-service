package com.mtn.repository.specification;

import com.mtn.model.domain.Banner;
import com.mtn.model.domain.Boundary;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by Tyler on 8/2/2018.
 */
public class BoundarySpecifications {

    private static final String DELETED_DATE = "deletedDate";
    private static final String ID = "id";

    public static Specification<Boundary> idEquals(Integer id) {
        return new Specification<Boundary>() {
            @Override
            public Predicate toPredicate(Root<Boundary> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get(ID), id);
            }
        };
    }

    public static Specification<Boundary> isNotDeleted() {
        return new Specification<Boundary>() {
            @Override
            public Predicate toPredicate(Root<Boundary> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.isNull(root.get(DELETED_DATE));
            }
        };
    }

}

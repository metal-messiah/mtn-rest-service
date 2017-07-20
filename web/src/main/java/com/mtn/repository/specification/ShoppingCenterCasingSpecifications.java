package com.mtn.repository.specification;

import com.mtn.model.domain.ShoppingCenterCasing;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by Allen on 4/23/2017.
 */
public class ShoppingCenterCasingSpecifications {

    private static final String DELETED_DATE = "deletedDate";
    private static final String ID = "id";

    public static Specification<ShoppingCenterCasing> idEquals(Integer id) {
        return new Specification<ShoppingCenterCasing>() {
            @Override
            public Predicate toPredicate(Root<ShoppingCenterCasing> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get(ID), id);
            }
        };
    }

    public static Specification<ShoppingCenterCasing> isNotDeleted() {
        return new Specification<ShoppingCenterCasing>() {
            @Override
            public Predicate toPredicate(Root<ShoppingCenterCasing> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.isNull(root.get(DELETED_DATE));
            }
        };
    }
}

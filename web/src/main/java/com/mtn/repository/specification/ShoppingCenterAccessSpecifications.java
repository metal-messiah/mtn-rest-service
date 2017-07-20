package com.mtn.repository.specification;

import com.mtn.model.domain.ShoppingCenterAccess;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by Allen on 4/23/2017.
 */
public class ShoppingCenterAccessSpecifications {

    private static final String DELETED_DATE = "deletedDate";
    private static final String ID = "id";

    public static Specification<ShoppingCenterAccess> idEquals(Integer id) {
        return new Specification<ShoppingCenterAccess>() {
            @Override
            public Predicate toPredicate(Root<ShoppingCenterAccess> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get(ID), id);
            }
        };
    }

    public static Specification<ShoppingCenterAccess> isNotDeleted() {
        return new Specification<ShoppingCenterAccess>() {
            @Override
            public Predicate toPredicate(Root<ShoppingCenterAccess> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.isNull(root.get(DELETED_DATE));
            }
        };
    }
}

package com.mtn.repository.specification;

import com.mtn.model.domain.search.ShoppingCenterSearchResult;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Created by Allen on 4/27/2017.
 */
public class ShoppingCenterSearchSpecifications {

    private static final String SITE_DELETED_DATE = "siteDeletedDate";
    private static final String SHOPPING_CENTER_DELETED_DATE = "shoppingCenterDeletedDate";

    public static Specification<ShoppingCenterSearchResult> isNotDeleted() {
        return where(siteIsNotDeleted())
                .and(shoppingCenterIsNotDeleted());
    }

    private static Specification<ShoppingCenterSearchResult> siteIsNotDeleted() {
        return new Specification<ShoppingCenterSearchResult>() {
            @Override
            public Predicate toPredicate(Root<ShoppingCenterSearchResult> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.isNull(root.get(SITE_DELETED_DATE));
            }
        };
    }

    private static Specification<ShoppingCenterSearchResult> shoppingCenterIsNotDeleted() {
        return new Specification<ShoppingCenterSearchResult>() {
            @Override
            public Predicate toPredicate(Root<ShoppingCenterSearchResult> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.isNull(root.get(SITE_DELETED_DATE));
            }
        };
    }
}

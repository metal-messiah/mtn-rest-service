package com.mtn.repository.specification;

import com.mtn.model.domain.search.ShoppingCenterSearchResult;
import com.mtn.util.QueryUtils;
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

    private static final String CITY = "city";
    private static final String COUNTY = "county";
    private static final String NAME = "name";
    private static final String OWNER = "owner";
    private static final String POSTAL_CODE = "postalCode";
    private static final String SITE_DELETED_DATE = "siteDeletedDate";
    private static final String SHOPPING_CENTER_DELETED_DATE = "shoppingCenterDeletedDate";
    private static final String STATE = "state";

    public static Specification<ShoppingCenterSearchResult> shoppingCenterIsNotDeleted() {
        return where(siteIsNotDeleted())
                .and(internalShoppingCenterIsNotDeleted());
    }

    public static Specification<ShoppingCenterSearchResult> shoppingCenterCityContains(String value) {
        return fieldContains(CITY, value);
    }

    public static Specification<ShoppingCenterSearchResult> shoppingCenterCountyContains(String value) {
        return fieldContains(COUNTY, value);
    }

    public static Specification<ShoppingCenterSearchResult> shoppingCenterNameContains(String value) {
        return fieldContains(NAME, value);
    }

    public static Specification<ShoppingCenterSearchResult> shoppingCenterOwnerContains(String value) {
        return fieldContains(OWNER, value);
    }

    public static Specification<ShoppingCenterSearchResult> shoppingCenterPostalCodeContains(String value) {
        return fieldContains(POSTAL_CODE, value);
    }

    public static Specification<ShoppingCenterSearchResult> shoppingCenterStateContains(String value) {
        return fieldContains(STATE, value);
    }

    private static Specification<ShoppingCenterSearchResult> fieldContains(String field, String value) {
        return new Specification<ShoppingCenterSearchResult>() {
            @Override
            public Predicate toPredicate(Root<ShoppingCenterSearchResult> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(criteriaBuilder.lower(root.get(field)), QueryUtils.contains(value.toLowerCase()));
            }
        };
    }

    private static Specification<ShoppingCenterSearchResult> siteIsNotDeleted() {
        return new Specification<ShoppingCenterSearchResult>() {
            @Override
            public Predicate toPredicate(Root<ShoppingCenterSearchResult> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.isNull(root.get(SITE_DELETED_DATE));
            }
        };
    }

    private static Specification<ShoppingCenterSearchResult> internalShoppingCenterIsNotDeleted() {
        return new Specification<ShoppingCenterSearchResult>() {
            @Override
            public Predicate toPredicate(Root<ShoppingCenterSearchResult> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.isNull(root.get(SHOPPING_CENTER_DELETED_DATE));
            }
        };
    }
}

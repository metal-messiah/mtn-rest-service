package com.mtn.repository.specification;

import com.mtn.model.domain.search.StoreSearchResult;
import com.mtn.util.QueryUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;

import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Created by Allen on 4/27/2017.
 */
public class StoreSearchSpecifications {

    private static final String CITY = "city";
    private static final String CLOSED_DATE = "storeClosedDate";
    private static final String COUNTY = "county";
    private static final String NAME = "name";
    private static final String OPENED_DATE = "storeOpenedDate";
    private static final String POSTAL_CODE = "postalCode";
    private static final String SITE_DELETED_DATE = "siteDeletedDate";
    private static final String STATE = "state";
    private static final String STORE_DELETED_DATE = "storeDeletedDate";

    public static Specification<StoreSearchResult> storeCityContains(String value) {
        return fieldContains(CITY, value);
    }

    public static Specification<StoreSearchResult> storeClosedBetween(LocalDateTime start, LocalDateTime end) {
        return dateBetween(CLOSED_DATE, start, end);
    }

    public static Specification<StoreSearchResult> storeCountyContains(String value) {
        return fieldContains(COUNTY, value);
    }

    public static Specification<StoreSearchResult> storeIsNotDeleted() {
        return where(siteIsNotDeleted())
                .and(internalStoreIsNotDeleted());
    }

    public static Specification<StoreSearchResult> storeNameContains(String value) {
        return fieldContains(NAME, value);
    }

    public static Specification<StoreSearchResult> storeOpenedBetween(LocalDateTime start, LocalDateTime end) {
        return dateBetween(OPENED_DATE, start, end);
    }

    public static Specification<StoreSearchResult> storePostalCodeContains(String value) {
        return fieldContains(POSTAL_CODE, value);
    }

    public static Specification<StoreSearchResult> storeStateContains(String value) {
        return fieldContains(STATE, value);
    }

    private static Specification<StoreSearchResult> dateBetween(String field, LocalDateTime start, LocalDateTime end) {
        return new Specification<StoreSearchResult>() {
            @Override
            public Predicate toPredicate(Root<StoreSearchResult> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.between(root.get(field), start, end);
            }
        };
    }

    private static Specification<StoreSearchResult> fieldContains(String field, String value) {
        return new Specification<StoreSearchResult>() {
            @Override
            public Predicate toPredicate(Root<StoreSearchResult> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(criteriaBuilder.lower(root.get(field)), QueryUtils.contains(value.toLowerCase()));
            }
        };
    }

    private static Specification<StoreSearchResult> siteIsNotDeleted() {
        return new Specification<StoreSearchResult>() {
            @Override
            public Predicate toPredicate(Root<StoreSearchResult> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.isNull(root.get(SITE_DELETED_DATE));
            }
        };
    }

    private static Specification<StoreSearchResult> internalStoreIsNotDeleted() {
        return new Specification<StoreSearchResult>() {
            @Override
            public Predicate toPredicate(Root<StoreSearchResult> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.isNull(root.get(STORE_DELETED_DATE));
            }
        };
    }
}

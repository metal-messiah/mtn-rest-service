package com.mtn.repository.specification;

import com.mtn.constant.StoreType;
import com.mtn.model.domain.ShoppingCenter;
import com.mtn.model.domain.Site;
import com.mtn.model.domain.Store;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

/**
 * Created by Allen on 4/24/2017.
 */
public class SiteSpecifications {

    private static final String DELETED_DATE = "deletedDate";
    private static final String ID = "id";
    private static final String SHOPPING_CENTER = "shoppingCenter";
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";

    public static Specification<Site> idEquals(Integer id) {
        return new Specification<Site>() {
            @Override
            public Predicate toPredicate(Root<Site> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get(ID), id);
            }
        };
    }

    public static Specification<Site> withinBoundingBox(Float north, Float south, Float east, Float west) {
        return new Specification<Site>() {
            @Override
            public Predicate toPredicate(Root<Site> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate northBound = criteriaBuilder.lessThanOrEqualTo(root.get(LATITUDE), north);
                Predicate southBound = criteriaBuilder.greaterThanOrEqualTo(root.get(LATITUDE), south);
                Predicate westBound = criteriaBuilder.greaterThanOrEqualTo(root.get(LONGITUDE), west);
                Predicate eastBound = criteriaBuilder.lessThanOrEqualTo(root.get(LONGITUDE), east);
                return criteriaBuilder.and(northBound, southBound, westBound, eastBound);
            }
        };
    }

    public static Specification<Site> shoppingCenterIdEquals(Integer id) {
        return new Specification<Site>() {
            @Override
            public Predicate toPredicate(Root<Site> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Join<Site, ShoppingCenter> siteShoppingCenterJoin = root.join(SHOPPING_CENTER);
                return criteriaBuilder.equal(siteShoppingCenterJoin.get(ID), id);
            }
        };
    }

    public static Specification<Site> hasActiveStore() {
        return new Specification<Site>() {
            @Override
            public Predicate toPredicate(Root<Site> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Join<Site, Store> siteStoreJoin = root.join("stores");
                return criteriaBuilder.equal(siteStoreJoin.get("storeType"), StoreType.ACTIVE);
            }
        };
    }

    public static Specification<Site> isNotDeleted() {
        return new Specification<Site>() {
            @Override
            public Predicate toPredicate(Root<Site> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.isNull(root.get(DELETED_DATE));
            }
        };
    }
}

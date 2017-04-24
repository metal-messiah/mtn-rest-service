package com.mtn.repository.specification;

import com.mtn.model.domain.ShoppingCenter;
import com.mtn.model.domain.Site;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

/**
 * Created by Allen on 4/24/2017.
 */
public class SiteSpecifications {

    private static final String DELETED_DATE = "deletedDate";
    private static final String ID = "id";
    private static final String SHOPPING_CENTER = "shoppingCenter";

    public static Specification<Site> idEquals(Integer id) {
        return new Specification<Site>() {
            @Override
            public Predicate toPredicate(Root<Site> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get(ID), id);
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

    public static Specification<Site> isNotDeleted() {
        return new Specification<Site>() {
            @Override
            public Predicate toPredicate(Root<Site> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.isNull(root.get(DELETED_DATE));
            }
        };
    }
}

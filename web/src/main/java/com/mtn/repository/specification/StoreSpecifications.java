package com.mtn.repository.specification;

import com.mtn.model.domain.Site;
import com.mtn.model.domain.Store;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

/**
 * Created by Allen on 4/26/2017.
 */
public class StoreSpecifications {

    private static final String DELETED_DATE = "deletedDate";
    private static final String ID = "id";
    private static final String SITE = "site";

    public static Specification<Store> idEquals(Integer id) {
        return new Specification<Store>() {
            @Override
            public Predicate toPredicate(Root<Store> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get(ID), id);
            }
        };
    }

    public static Specification<Store> siteIdEquals(Integer id) {
        return new Specification<Store>() {
            @Override
            public Predicate toPredicate(Root<Store> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Join<Store, Site> storeSiteJoin = root.join(SITE);
                return criteriaBuilder.equal(storeSiteJoin.get(ID), id);
            }
        };
    }

    public static Specification<Store> isNotDeleted() {
        return new Specification<Store>() {
            @Override
            public Predicate toPredicate(Root<Store> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.isNull(root.get(DELETED_DATE));
            }
        };
    }
}

package com.mtn.repository.specification;

import com.mtn.model.domain.Store;
import com.mtn.model.domain.StoreCasing;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

/**
 * Created by Allen on 4/23/2017.
 */
public class StoreCasingSpecifications {

    private static final String DELETED_DATE = "deletedDate";
    private static final String ID = "id";
    private static final String STORE = "store";

    public static Specification<StoreCasing> idEquals(Integer id) {
        return new Specification<StoreCasing>() {
            @Override
            public Predicate toPredicate(Root<StoreCasing> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get(ID), id);
            }
        };
    }

    public static Specification<StoreCasing> storeIdEquals(Integer id) {
        return new Specification<StoreCasing>() {
            @Override
            public Predicate toPredicate(Root<StoreCasing> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Join<StoreCasing, Store> storeCasingStoreJoin = root.join(STORE);
                return criteriaBuilder.equal(storeCasingStoreJoin.get(ID), id);
            }
        };
    }

    public static Specification<StoreCasing> isNotDeleted() {
        return new Specification<StoreCasing>() {
            @Override
            public Predicate toPredicate(Root<StoreCasing> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.isNull(root.get(DELETED_DATE));
            }
        };
    }
}

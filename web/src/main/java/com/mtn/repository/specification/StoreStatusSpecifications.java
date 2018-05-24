package com.mtn.repository.specification;

import com.mtn.model.domain.Store;
import com.mtn.model.domain.StoreStatus;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

/**
 * Created by Allen on 4/23/2017.
 */
public class StoreStatusSpecifications {

    private static final String DELETED_DATE = "deletedDate";
    private static final String ID = "id";
    private static final String STORE = "store";

    public static Specification<StoreStatus> idEquals(Integer id) {
        return new Specification<StoreStatus>() {
            @Override
            public Predicate toPredicate(Root<StoreStatus> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get(ID), id);
            }
        };
    }

    public static Specification<StoreStatus> storeIdEquals(Integer id) {
        return new Specification<StoreStatus>() {
            @Override
            public Predicate toPredicate(Root<StoreStatus> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Join<StoreStatus, Store> storeStatusStoreJoin = root.join(STORE);
                return criteriaBuilder.equal(storeStatusStoreJoin.get(ID), id);
            }
        };
    }

    public static Specification<StoreStatus> isNotDeleted() {
        return new Specification<StoreStatus>() {
            @Override
            public Predicate toPredicate(Root<StoreStatus> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.isNull(root.get(DELETED_DATE));
            }
        };
    }
}

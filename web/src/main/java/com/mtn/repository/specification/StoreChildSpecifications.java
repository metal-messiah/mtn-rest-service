package com.mtn.repository.specification;

import com.mtn.model.domain.Store;
import com.mtn.model.domain.StoreVolume;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public class StoreChildSpecifications {

    private static final String STORE = "store";
    private static final String ID = "id";

    public static <T> Specification<T> storeIdEquals(Integer id) {
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Join<T, Store> storeChildJoin = root.join(STORE);
                return criteriaBuilder.equal(storeChildJoin.get(ID), id);
            }
        };
    }
}

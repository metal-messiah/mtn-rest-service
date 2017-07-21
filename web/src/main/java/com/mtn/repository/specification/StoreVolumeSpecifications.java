package com.mtn.repository.specification;

import com.mtn.model.domain.Store;
import com.mtn.model.domain.StoreVolume;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

/**
 * Created by Allen on 4/23/2017.
 */
public class StoreVolumeSpecifications {

    private static final String DELETED_DATE = "deletedDate";
    private static final String ID = "id";
    private static final String STORE = "store";

    public static Specification<StoreVolume> idEquals(Integer id) {
        return new Specification<StoreVolume>() {
            @Override
            public Predicate toPredicate(Root<StoreVolume> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get(ID), id);
            }
        };
    }

    public static Specification<StoreVolume> storeIdEquals(Integer id) {
        return new Specification<StoreVolume>() {
            @Override
            public Predicate toPredicate(Root<StoreVolume> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Join<StoreVolume, Store> storeVolumeStoreJoin = root.join(STORE);
                return criteriaBuilder.equal(storeVolumeStoreJoin.get(ID), id);
            }
        };
    }

    public static Specification<StoreVolume> isNotDeleted() {
        return new Specification<StoreVolume>() {
            @Override
            public Predicate toPredicate(Root<StoreVolume> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.isNull(root.get(DELETED_DATE));
            }
        };
    }
}

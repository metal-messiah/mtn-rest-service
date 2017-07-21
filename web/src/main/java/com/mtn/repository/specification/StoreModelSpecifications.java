package com.mtn.repository.specification;

import com.mtn.model.domain.Store;
import com.mtn.model.domain.StoreModel;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

/**
 * Created by Allen on 4/23/2017.
 */
public class StoreModelSpecifications {

    private static final String DELETED_DATE = "deletedDate";
    private static final String ID = "id";
    private static final String STORE = "store";

    public static Specification<StoreModel> idEquals(Integer id) {
        return new Specification<StoreModel>() {
            @Override
            public Predicate toPredicate(Root<StoreModel> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get(ID), id);
            }
        };
    }

    public static Specification<StoreModel> storeIdEquals(Integer id) {
        return new Specification<StoreModel>() {
            @Override
            public Predicate toPredicate(Root<StoreModel> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Join<StoreModel, Store> storeModelStoreJoin = root.join(STORE);
                return criteriaBuilder.equal(storeModelStoreJoin.get(ID), id);
            }
        };
    }

    public static Specification<StoreModel> isNotDeleted() {
        return new Specification<StoreModel>() {
            @Override
            public Predicate toPredicate(Root<StoreModel> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.isNull(root.get(DELETED_DATE));
            }
        };
    }
}

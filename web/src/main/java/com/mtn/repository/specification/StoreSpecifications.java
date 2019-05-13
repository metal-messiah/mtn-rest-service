package com.mtn.repository.specification;

import com.mtn.constant.StoreType;
import com.mtn.model.domain.Site_;
import com.mtn.model.domain.Store;
import com.mtn.model.domain.Store_;
import org.springframework.data.jpa.domain.Specification;

public class StoreSpecifications extends AuditingEntitySpecifications {

    public static Specification<Store> siteIdEquals(Integer id) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.join(Store_.site).get(Site_.id), id);
    }

    public static Specification<Store> storeTypeEquals(StoreType storeType) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(Store_.storeType), storeType);
    }

    public static Specification<Store> isFloat() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.isTrue(root.get(Store_.floating));
    }


}

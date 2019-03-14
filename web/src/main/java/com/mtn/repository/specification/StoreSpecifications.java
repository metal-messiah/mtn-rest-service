package com.mtn.repository.specification;

import com.mtn.model.domain.Site_;
import com.mtn.model.domain.Store;
import com.mtn.model.domain.Store_;
import org.springframework.data.jpa.domain.Specification;

public class StoreSpecifications extends AuditingEntitySpecifications {

    public static Specification<Store> siteIdEquals(Integer id) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.join(Store_.site).get(Site_.id), id);
    }

}

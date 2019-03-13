package com.mtn.repository.specification;

import com.mtn.constant.StoreType;
import com.mtn.model.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class StoreSpecifications extends AuditingEntitySpecifications {

    public static Specification<Store> siteIdEquals(Integer id) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.join(Store_.site).get(Site_.id), id);
    }

    public static Specification<Store> ofTypes(List<StoreType> storeTypes) {
        return (root, criteriaQuery, criteriaBuilder) -> root.get(Store_.storeType).in(storeTypes);
    }

    public static Specification<Store> assignedTo(Integer assigneeId) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.join(Store_.site).join(Site_.assignee).get(UserProfile_.id), assigneeId);
    }

}

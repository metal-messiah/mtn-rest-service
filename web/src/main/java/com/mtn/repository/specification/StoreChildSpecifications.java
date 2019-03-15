package com.mtn.repository.specification;

import org.springframework.data.jpa.domain.Specification;

public class StoreChildSpecifications extends AuditingEntitySpecifications {

    public static <T> Specification<T> storeIdEquals(Integer id) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.join("store").get("id"), id);
    }
}

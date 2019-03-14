package com.mtn.repository.specification;

import com.mtn.model.domain.AuditingEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class AuditingEntitySpecifications {

    public static <T extends AuditingEntity> Specification<T> idIn(List<Integer> ids) {
        return (root, criteriaQuery, criteriaBuilder) -> root.get("id").in(ids);
    }

    public static <T extends AuditingEntity> Specification<T> idEquals(Integer id) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }

    public static <T extends AuditingEntity> Specification<T> isNotDeleted() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.isNull(root.get("deletedDate"));
    }

}

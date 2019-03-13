package com.mtn.repository.specification;

import com.mtn.model.domain.AuditingEntity;
import com.mtn.model.domain.Banner;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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

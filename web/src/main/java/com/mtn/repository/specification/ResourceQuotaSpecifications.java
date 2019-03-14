package com.mtn.repository.specification;

import com.mtn.model.domain.ResourceQuota;
import com.mtn.model.domain.ResourceQuota_;
import org.springframework.data.jpa.domain.Specification;

public class ResourceQuotaSpecifications extends AuditingEntitySpecifications {

    public static Specification<ResourceQuota> resourceNameEquals(String name) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(ResourceQuota_.resourceName), name);
    }
}

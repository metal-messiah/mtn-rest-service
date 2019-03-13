package com.mtn.repository.specification;

import com.mtn.model.domain.Permission;
import com.mtn.model.domain.Permission_;
import com.mtn.model.domain.Role_;
import org.springframework.data.jpa.domain.Specification;

public class PermissionSpecifications extends AuditingEntitySpecifications {

    public static Specification<Permission> roleIdEquals(Integer id) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.join(Permission_.roles).get(Role_.id), id);
    }

}

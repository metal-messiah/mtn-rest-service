package com.mtn.repository.specification;

import com.mtn.model.domain.Role;
import com.mtn.model.domain.Role_;
import org.springframework.data.jpa.domain.Specification;

public class RoleSpecifications extends AuditingEntitySpecifications {

	public static Specification<Role> displayNameContains(String value) {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get(Role_.displayName)), String.format("%%%s%%", value.toLowerCase()));
	}

	public static Specification<Role> isAdmin() {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(Role_.id), 1);
	}

}

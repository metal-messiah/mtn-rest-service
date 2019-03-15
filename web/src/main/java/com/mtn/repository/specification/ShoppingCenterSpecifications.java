package com.mtn.repository.specification;

import com.mtn.model.domain.ShoppingCenter;
import com.mtn.model.domain.ShoppingCenter_;
import org.springframework.data.jpa.domain.Specification;

public class ShoppingCenterSpecifications extends AuditingEntitySpecifications {

	public static Specification<ShoppingCenter> nameContains(String value) {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get(ShoppingCenter_.name)), String.format("%%%s%%", value.toLowerCase()));
	}

	public static Specification<ShoppingCenter> ownerContains(String value) {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get(ShoppingCenter_.owner)), String.format("%%%s%%", value.toLowerCase()));
	}

}

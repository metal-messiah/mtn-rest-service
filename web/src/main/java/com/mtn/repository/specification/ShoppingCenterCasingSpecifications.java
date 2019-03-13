package com.mtn.repository.specification;

import com.mtn.model.domain.ShoppingCenterCasing;
import com.mtn.model.domain.ShoppingCenterCasing_;
import com.mtn.model.domain.ShoppingCenter_;
import org.springframework.data.jpa.domain.Specification;

public class ShoppingCenterCasingSpecifications extends AuditingEntitySpecifications {

	public static Specification<ShoppingCenterCasing> shoppingCenterIdEquals(Integer id) {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.join(ShoppingCenterCasing_.shoppingCenter).get(ShoppingCenter_.id), id);
	}

}

package com.mtn.repository.specification;

import com.mtn.model.domain.ShoppingCenterAccess;
import com.mtn.model.domain.ShoppingCenterAccess_;
import com.mtn.model.domain.ShoppingCenterSurvey_;
import org.springframework.data.jpa.domain.Specification;

public class ShoppingCenterAccessSpecifications extends AuditingEntitySpecifications {

	public static Specification<ShoppingCenterAccess> shoppingCenterSurveyIdEquals(Integer id) {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.join(ShoppingCenterAccess_.survey).get(ShoppingCenterSurvey_.id), id);
	}
}

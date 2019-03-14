package com.mtn.repository.specification;

import com.mtn.model.domain.ShoppingCenterSurvey_;
import com.mtn.model.domain.ShoppingCenterTenant;
import com.mtn.model.domain.ShoppingCenterTenant_;
import org.springframework.data.jpa.domain.Specification;

public class ShoppingCenterTenantSpecifications extends AuditingEntitySpecifications {

	public static Specification<ShoppingCenterTenant> shoppingCenterSurveyIdEquals(Integer id) {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.join(ShoppingCenterTenant_.survey).get(ShoppingCenterSurvey_.id), id);
	}

}

package com.mtn.repository.specification;

import com.mtn.model.domain.*;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public class ShoppingCenterTenantSpecifications extends AuditingEntitySpecifications {

	public static Specification<ShoppingCenterTenant> shoppingCenterSurveyIdEquals(Integer id) {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.join(ShoppingCenterTenant_.survey).get(ShoppingCenterSurvey_.id), id);
	}

}

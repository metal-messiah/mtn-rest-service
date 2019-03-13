package com.mtn.repository.specification;

import com.mtn.model.domain.*;
import org.springframework.data.jpa.domain.Specification;

public class ShoppingCenterSurveySpecifications extends AuditingEntitySpecifications {

	public static Specification<ShoppingCenterSurvey> shoppingCenterIdEquals(Integer id) {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.join(ShoppingCenterSurvey_.shoppingCenter).get(ShoppingCenter_.id), id);
	}

}

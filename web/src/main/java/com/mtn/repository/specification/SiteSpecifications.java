package com.mtn.repository.specification;

import com.mtn.model.domain.ShoppingCenter_;
import com.mtn.model.domain.Site;
import com.mtn.model.domain.Site_;
import org.springframework.data.jpa.domain.Specification;

public class SiteSpecifications extends AuditingEntitySpecifications {

	public static Specification<Site> isDuplicate() {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.isTrue(root.get(Site_.duplicate));
	}

	public static Specification<Site> shoppingCenterIdEquals(Integer id) {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.join(Site_.shoppingCenter).get(ShoppingCenter_.id), id);
	}

}

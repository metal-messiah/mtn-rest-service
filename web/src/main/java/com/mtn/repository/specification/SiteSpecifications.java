package com.mtn.repository.specification;

import com.mtn.model.domain.ShoppingCenter;
import com.mtn.model.domain.Site;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

/**
 * Created by Allen on 4/24/2017.
 */
public class SiteSpecifications extends AuditingEntitySpecifications {

	private static final String SHOPPING_CENTER = "shoppingCenter";
	private static final String DUPLICATE = "duplicate";

	public static Specification<Site> isDuplicate() {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.isTrue(root.get(DUPLICATE));
	}

	public static Specification<Site> shoppingCenterIdEquals(Integer id) {
		return new Specification<Site>() {
			@Override
			public Predicate toPredicate(Root<Site> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
				Join<Site, ShoppingCenter> siteShoppingCenterJoin = root.join(SHOPPING_CENTER);
				return criteriaBuilder.equal(siteShoppingCenterJoin.get(ID), id);
			}
		};
	}

}

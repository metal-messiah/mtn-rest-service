package com.mtn.repository.specification;

import com.mtn.model.domain.ShoppingCenter;
import com.mtn.model.domain.ShoppingCenterCasing;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

/**
 * Created by Allen on 4/23/2017.
 */
public class ShoppingCenterCasingSpecifications extends AuditingEntitySpecifications {

    private static final String ID = "id";
    private static final String SHOPPING_CENTER = "shoppingCenter";

	public static Specification<ShoppingCenterCasing> shoppingCenterIdEquals(Integer id) {
		return new Specification<ShoppingCenterCasing>() {
			@Override
			public Predicate toPredicate(Root<ShoppingCenterCasing> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
				Join<ShoppingCenterCasing, ShoppingCenter> shoppingCenterCasingShoppingCenterJoin = root.join(SHOPPING_CENTER);
				return criteriaBuilder.equal(shoppingCenterCasingShoppingCenterJoin.get(ID), id);
			}
		};
	}

}

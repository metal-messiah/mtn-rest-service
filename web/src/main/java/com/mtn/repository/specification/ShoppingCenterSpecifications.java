package com.mtn.repository.specification;

import com.mtn.model.domain.ShoppingCenter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by Allen on 4/23/2017.
 */
public class ShoppingCenterSpecifications extends AuditingEntitySpecifications {

	private static final String NAME = "name";
	private static final String OWNER = "owner";

	public static Specification<ShoppingCenter> nameContains(String value) {
		return new Specification<ShoppingCenter>() {
			@Override
			public Predicate toPredicate(Root<ShoppingCenter> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
				return criteriaBuilder.like(criteriaBuilder.lower(root.get(NAME)), String.format("%%%s%%", value.toLowerCase()));
			}
		};
	}

	public static Specification<ShoppingCenter> ownerContains(String value) {
		return new Specification<ShoppingCenter>() {
			@Override
			public Predicate toPredicate(Root<ShoppingCenter> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
				return criteriaBuilder.like(criteriaBuilder.lower(root.get(OWNER)), String.format("%%%s%%", value.toLowerCase()));
			}
		};
	}

}

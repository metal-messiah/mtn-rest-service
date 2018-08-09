package com.mtn.repository.specification;

import com.mtn.model.domain.StoreSource;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public class StoreSourceSpecifications extends StoreChildSpecifications {

	private static final String VALIDATED_DATE = "validatedDate";
	private static final String SOURCE_NATIVE_ID = "sourceNativeId";
	private static final String SOURCE_NAME = "sourceName";

	public static Specification<StoreSource> sourceNativeIdEquals(String id) {
		return new Specification<StoreSource>() {
			@Override
			public Predicate toPredicate(Root<StoreSource> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
				return criteriaBuilder.equal(root.get(SOURCE_NATIVE_ID), id);
			}
		};
	}

	public static Specification<StoreSource> sourceNameEquals(String sourceName) {
		return new Specification<StoreSource>() {
			@Override
			public Predicate toPredicate(Root<StoreSource> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
				return criteriaBuilder.equal(root.get(SOURCE_NAME), sourceName);
			}
		};
	}

	public static Specification<StoreSource> isNotValidated() {
		return new Specification<StoreSource>() {
			@Override
			public Predicate toPredicate(Root<StoreSource> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
				return criteriaBuilder.isNull(root.get(VALIDATED_DATE));
			}
		};
	}

	public static Specification<StoreSource> isValidated() {
		return new Specification<StoreSource>() {
			@Override
			public Predicate toPredicate(Root<StoreSource> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
				return criteriaBuilder.isNotNull(root.get(VALIDATED_DATE));
			}
		};
	}
}

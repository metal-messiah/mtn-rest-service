package com.mtn.repository.specification;

import com.mtn.model.domain.BannerSource;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class BannerSourceSpecifications extends AuditingEntitySpecifications{

	private static final String VALIDATED_DATE = "validatedDate";
	private static final String SOURCE_NATIVE_ID = "sourceNativeId";
	private static final String SOURCE_NAME = "sourceName";

	public static Specification<BannerSource> sourceNativeIdEquals(String id) {
		return new Specification<BannerSource>() {
			@Override
			public Predicate toPredicate(Root<BannerSource> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
				return criteriaBuilder.equal(root.get(SOURCE_NATIVE_ID), id);
			}
		};
	}

	public static Specification<BannerSource> sourceNameEquals(String sourceName) {
		return new Specification<BannerSource>() {
			@Override
			public Predicate toPredicate(Root<BannerSource> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
				return criteriaBuilder.equal(root.get(SOURCE_NAME), sourceName);
			}
		};
	}

	public static Specification<BannerSource> isNotValidated() {
		return new Specification<BannerSource>() {
			@Override
			public Predicate toPredicate(Root<BannerSource> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
				return criteriaBuilder.isNull(root.get(VALIDATED_DATE));
			}
		};
	}

	public static Specification<BannerSource> isValidated() {
		return new Specification<BannerSource>() {
			@Override
			public Predicate toPredicate(Root<BannerSource> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
				return criteriaBuilder.isNotNull(root.get(VALIDATED_DATE));
			}
		};
	}
}

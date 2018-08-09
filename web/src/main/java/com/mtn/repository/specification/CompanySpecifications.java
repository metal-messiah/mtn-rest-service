package com.mtn.repository.specification;

import com.mtn.model.domain.Company;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by Allen on 4/23/2017.
 */
public class CompanySpecifications extends AuditingEntitySpecifications {

	private static final String NAME = "companyName";

	public static Specification<Company> companyNameLike(String value) {
		return new Specification<Company>() {
			@Override
			public Predicate toPredicate(Root<Company> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
				return criteriaBuilder.like(criteriaBuilder.lower(root.get(NAME)), String.format("%%%s%%", value.toLowerCase()));
			}
		};
	}

	public static Specification<Company> companyNameEquals(String value) {
		return new Specification<Company>() {
			@Override
			public Predicate toPredicate(Root<Company> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
				return criteriaBuilder.equal(criteriaBuilder.lower(root.get(NAME)), value.toLowerCase());
			}
		};
	}
}

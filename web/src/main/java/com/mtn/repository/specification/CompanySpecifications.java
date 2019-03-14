package com.mtn.repository.specification;

import com.mtn.model.domain.Company;
import com.mtn.model.domain.Company_;
import org.springframework.data.jpa.domain.Specification;

public class CompanySpecifications extends AuditingEntitySpecifications {

	public static Specification<Company> companyNameLike(String value) {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get(Company_.companyName)), String.format("%%%s%%", value.toLowerCase()));
	}

}

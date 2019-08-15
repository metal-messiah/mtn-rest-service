package com.mtn.repository.specification;

import com.mtn.model.domain.UserBoundary;
import com.mtn.model.domain.UserBoundary_;
import com.mtn.model.domain.UserProfile_;

import org.springframework.data.jpa.domain.Specification;

public class UserBoundarySpecifications extends AuditingEntitySpecifications {
	public static Specification<UserBoundary> createdByEquals(Integer id) {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
				.equal(root.get(UserBoundary_.createdBy).get(UserProfile_.id), id);
	}
}

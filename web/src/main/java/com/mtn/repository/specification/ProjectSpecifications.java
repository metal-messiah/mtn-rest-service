package com.mtn.repository.specification;

import com.mtn.model.domain.Project;
import com.mtn.model.domain.Project_;
import org.springframework.data.jpa.domain.Specification;

public class ProjectSpecifications extends AuditingEntitySpecifications {

	public static Specification<Project> active() {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.isTrue(root.get(Project_.active));
	}

	public static Specification<Project> primaryData() {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.isTrue(root.get(Project_.primaryData));
	}

	public static Specification<Project> projectNameIsLike(String nameQuery) {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get(Project_.projectName), nameQuery);
	}

}

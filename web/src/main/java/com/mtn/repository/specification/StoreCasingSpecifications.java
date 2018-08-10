package com.mtn.repository.specification;

import com.mtn.model.domain.Project;
import com.mtn.model.domain.StoreCasing;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

/**
 * Created by Allen on 4/23/2017.
 */
public class StoreCasingSpecifications extends StoreChildSpecifications {

	private static final String ID = "id";
	private static final String PROJECT = "projects";

	public static Specification<StoreCasing> projectIdEquals(Integer projectId) {
		return new Specification<StoreCasing>() {
			@Override
			public Predicate toPredicate(Root<StoreCasing> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
				Join<StoreCasing, Project> storeCasingProjectJoin = root.join(PROJECT);
				return criteriaBuilder.equal(storeCasingProjectJoin.get(ID), projectId);
			}
		};
	}

}

package com.mtn.repository.specification;

import com.mtn.model.domain.Project;
import com.mtn.model.domain.StoreModel;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

/**
 * Created by Allen on 4/23/2017.
 */
public class StoreModelSpecifications extends StoreChildSpecifications {

	private static final String ID = "id";
	private static final String PROJECT = "project";

	public static Specification<StoreModel> projectIdEquals(Integer id) {
		return new Specification<StoreModel>() {
			@Override
			public Predicate toPredicate(Root<StoreModel> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
				Join<StoreModel, Project> storeModelProjectJoin = root.join(PROJECT);
				return criteriaBuilder.equal(storeModelProjectJoin.get(ID), id);
			}
		};
	}

}

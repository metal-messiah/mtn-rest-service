package com.mtn.repository.specification;

import com.mtn.model.domain.Project_;
import com.mtn.model.domain.StoreModel;
import com.mtn.model.domain.StoreModel_;
import org.springframework.data.jpa.domain.Specification;

public class StoreModelSpecifications extends StoreChildSpecifications {

	public static Specification<StoreModel> projectIdEquals(Integer id) {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.join(StoreModel_.project).get(Project_.id), id);
	}

}

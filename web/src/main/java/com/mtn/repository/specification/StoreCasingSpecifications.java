package com.mtn.repository.specification;

import com.mtn.model.domain.Project_;
import com.mtn.model.domain.StoreCasing;
import com.mtn.model.domain.StoreCasing_;
import org.springframework.data.jpa.domain.Specification;

public class StoreCasingSpecifications extends StoreChildSpecifications {

	public static Specification<StoreCasing> projectIdEquals(Integer projectId) {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.join(StoreCasing_.projects).get(Project_.id), projectId);
	}

}

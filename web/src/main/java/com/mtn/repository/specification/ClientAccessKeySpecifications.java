package com.mtn.repository.specification;

import com.mtn.model.domain.ClientAccessKey;
import com.mtn.model.domain.ClientAccessKey_;
import org.springframework.data.jpa.domain.Specification;

public class ClientAccessKeySpecifications extends AuditingEntitySpecifications {

	public static Specification<ClientAccessKey> accessKeyEquals(String accessKey) {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(ClientAccessKey_.accessKey), accessKey);
	}
}

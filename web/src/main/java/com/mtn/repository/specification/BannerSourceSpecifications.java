package com.mtn.repository.specification;

import com.mtn.model.domain.BannerSource;
import com.mtn.model.domain.BannerSource_;
import org.springframework.data.jpa.domain.Specification;

public class BannerSourceSpecifications extends AuditingEntitySpecifications {

	public static Specification<BannerSource> sourceNativeIdEquals(String id) {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(BannerSource_.sourceNativeId), id);
	}

	public static Specification<BannerSource> sourceNameEquals(String sourceName) {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(BannerSource_.sourceName), sourceName);
	}

	public static Specification<BannerSource> isValidated() {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.isNotNull(root.get(BannerSource_.validatedDate));
	}
}

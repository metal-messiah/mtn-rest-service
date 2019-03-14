package com.mtn.repository.specification;

import com.mtn.model.domain.BannerSource_;
import com.mtn.model.domain.StoreSource;
import com.mtn.model.domain.StoreSource_;
import org.springframework.data.jpa.domain.Specification;

public class StoreSourceSpecifications extends StoreChildSpecifications {

	public static Specification<StoreSource> sourceNativeIdEquals(String id) {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(StoreSource_.sourceNativeId), id);
	}

	public static Specification<StoreSource> bannerSourceIdEquals(Integer id) {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.join(StoreSource_.bannerSource).get(BannerSource_.id), id);
	}

	public static Specification<StoreSource> sourceNameEquals(String sourceName) {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(StoreSource_.sourceName), sourceName);
	}

	public static Specification<StoreSource> isValidated() {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.isNotNull(root.get(StoreSource_.validatedDate));
	}
}

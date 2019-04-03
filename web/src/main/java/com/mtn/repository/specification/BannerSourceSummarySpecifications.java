package com.mtn.repository.specification;

import com.mtn.model.domain.BannerSourceSummary;
import com.mtn.model.domain.BannerSourceSummary_;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class BannerSourceSummarySpecifications {

	public static Specification<BannerSourceSummary> idIn(List<Integer> ids) {
		return (root, criteriaQuery, criteriaBuilder) -> root.get(BannerSourceSummary_.id).in(ids);
	}

	public static Specification<BannerSourceSummary> isDeleted() {
		return (root, criteriaQuery, criteriaBuilder) -> root.get(BannerSourceSummary_.id).isNull();
	}

	public static Specification<BannerSourceSummary> idEquals(Integer id) {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(BannerSourceSummary_.id), id);
	}

	public static Specification<BannerSourceSummary> sourceNameEquals(String sourceName) {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(BannerSourceSummary_.sourceName), sourceName);
	}

	public static Specification<BannerSourceSummary> isNotValidated() {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.isNull(root.get(BannerSourceSummary_.validatedDate));
	}

	public static Specification<BannerSourceSummary> isValidated() {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.isNotNull(root.get(BannerSourceSummary_.validatedDate));
	}
}

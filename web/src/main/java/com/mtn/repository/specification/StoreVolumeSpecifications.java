package com.mtn.repository.specification;

import com.mtn.model.domain.StoreVolume;
import com.mtn.model.domain.StoreVolume_;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class StoreVolumeSpecifications extends StoreChildSpecifications {

	public static Specification<StoreVolume> sourceEquals(String source) {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(StoreVolume_.source), source);
	}

	public static Specification<StoreVolume> volumeDateEquals(LocalDate volumeDate) {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(StoreVolume_.volumeDate), volumeDate);
	}
}

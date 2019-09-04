package com.mtn.repository.specification;

import com.mtn.model.domain.BlockGroupPopulationCentroid;
import com.mtn.model.domain.BlockGroupPopulationCentroid_;
import com.mtn.repository.specification.predicate.WithinGeoJson;
import com.mtn.repository.specification.predicate.WithinPredicate;
import com.mtn.repository.specification.predicate.WithinSphericalDistancePredicate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;

public class BlockGroupPopulationCentroidSpecifications {

	public static Specification<BlockGroupPopulationCentroid> withinGeometry(Geometry geometry) {
		return (root, criteriaQuery, criteriaBuilder) -> new WithinPredicate((CriteriaBuilderImpl) criteriaBuilder, root.get("location"), geometry);
	}

	public static Specification<BlockGroupPopulationCentroid> withinSphericalDistance(Point circleCenter, Float distance) {
		return (root, criteriaQuery, criteriaBuilder) -> new WithinSphericalDistancePredicate((CriteriaBuilderImpl) criteriaBuilder, root.get("location"), circleCenter, distance);
	}

	public static Specification<BlockGroupPopulationCentroid> withinGeoJson(String geoJson) {
		return (root, criteriaQuery, criteriaBuilder) -> new WithinGeoJson((CriteriaBuilderImpl) criteriaBuilder, root.get("location"), geoJson);
	}

	public static Specification<BlockGroupPopulationCentroid> withinBoundingBox(Float north, Float south, Float east, Float west) {
		return (root, criteriaQuery, criteriaBuilder) -> {
			Predicate northBound = criteriaBuilder.le(root.get(BlockGroupPopulationCentroid_.latitude), north);
			Predicate southBound = criteriaBuilder.ge(root.get(BlockGroupPopulationCentroid_.latitude), south);
			Predicate eastBound = criteriaBuilder.le(root.get(BlockGroupPopulationCentroid_.longitude), east);
			Predicate westBound = criteriaBuilder.ge(root.get(BlockGroupPopulationCentroid_.longitude), west);
			return criteriaBuilder.and(northBound, southBound, eastBound, westBound);
		};
	}


}

package com.mtn.repository.specification;

import com.mtn.model.domain.Site;
import com.mtn.model.domain.Site_;
import com.mtn.model.domain.Store_;
import com.mtn.repository.specification.predicate.WithinGeoJson;
import com.mtn.repository.specification.predicate.WithinPredicate;
import com.mtn.repository.specification.predicate.WithinSphericalDistancePredicate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;

public class SiteSpecifications extends AuditingEntitySpecifications {

	public static Specification<Site> withinGeometry(Geometry geometry) {
		return (root, criteriaQuery, criteriaBuilder) -> new WithinPredicate((CriteriaBuilderImpl) criteriaBuilder, root.get("location"), geometry);
	}

	public static Specification<Site> withinSphericalDistance(Point circleCenter, Float distance) {
		return (root, criteriaQuery, criteriaBuilder) -> new WithinSphericalDistancePredicate((CriteriaBuilderImpl) criteriaBuilder, root.get("location"), circleCenter, distance);
	}

	public static Specification<Site> withinGeoJson(String geoJson) {
		return (root, criteriaQuery, criteriaBuilder) -> new WithinGeoJson((CriteriaBuilderImpl) criteriaBuilder, root.get("location"), geoJson);
	}

	public static Specification<Site> withinBoundingBox(Float north, Float south, Float east, Float west) {
		return (root, criteriaQuery, criteriaBuilder) -> {
			Predicate northBound = criteriaBuilder.le(root.get(Site_.latitude), north);
			Predicate southBound = criteriaBuilder.ge(root.get(Site_.latitude), south);
			Predicate eastBound = criteriaBuilder.le(root.get(Site_.longitude), east);
			Predicate westBound = criteriaBuilder.ge(root.get(Site_.longitude), west);
			return criteriaBuilder.and(northBound, southBound, eastBound, westBound);
		};
	}

	public static Specification<Site> updatedSince(LocalDateTime updatedAt) {
		return (root, criteriaQuery, criteriaBuilder) -> {
			Predicate siteUpdated = criteriaBuilder.greaterThanOrEqualTo(root.get(Site_.updatedDate), updatedAt);
			Predicate storesUpdated = criteriaBuilder.greaterThanOrEqualTo(root.join(Site_.stores, JoinType.LEFT).get(Store_.updatedDate), updatedAt);
			return criteriaBuilder.or(siteUpdated, storesUpdated);
		};
	}

}

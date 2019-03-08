package com.mtn.repository.specification;

import com.mtn.model.domain.ShoppingCenter;
import com.mtn.model.domain.Site;
import com.mtn.repository.specification.predicate.*;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public class SiteSpecifications extends AuditingEntitySpecifications {

	private static final String SHOPPING_CENTER = "shoppingCenter";
	private static final String DUPLICATE = "duplicate";

	public static Specification<Site> isDuplicate() {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.isTrue(root.get(DUPLICATE));
	}

	public static Specification<Site> shoppingCenterIdEquals(Integer id) {
		return (root, criteriaQuery, criteriaBuilder) -> {
			Join<Site, ShoppingCenter> siteShoppingCenterJoin = root.join(SHOPPING_CENTER);
			return criteriaBuilder.equal(siteShoppingCenterJoin.get(ID), id);
		};
	}

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
			Predicate northBound = criteriaBuilder.le(root.get("latitude"), north);
			Predicate southBound = criteriaBuilder.ge(root.get("latitude"), south);
			Predicate eastBound = criteriaBuilder.le(root.get("longitude"), east);
			Predicate westBound = criteriaBuilder.ge(root.get("longitude"), west);
			return criteriaBuilder.and(northBound, southBound, eastBound, westBound);
		};
	}

}

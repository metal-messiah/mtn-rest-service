package com.mtn.repository.specification;

import com.mtn.constant.StoreType;
import com.mtn.model.domain.ShoppingCenter;
import com.mtn.model.domain.Site;
import com.mtn.model.domain.Store;
import com.mtn.model.domain.UserProfile;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.List;

/**
 * Created by Allen on 4/24/2017.
 */
public class SiteSpecifications extends AuditingEntitySpecifications {

	private static final String ID = "id";
	private static final String SHOPPING_CENTER = "shoppingCenter";
	private static final String LATITUDE = "latitude";
	private static final String LONGITUDE = "longitude";
	private static final String DUPLICATE = "duplicate";
	private static final String ASSIGNEE = "assignee";

	public static Specification<Site> isDuplicate() {
		return new Specification<Site>() {
			@Override
			public Predicate toPredicate(Root<Site> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
				return criteriaBuilder.isTrue(root.get(DUPLICATE));
			}
		};
	}

	public static Specification<Site> withinBoundingBox(Float north, Float south, Float east, Float west) {
		return new Specification<Site>() {
			@Override
			public Predicate toPredicate(Root<Site> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
				Predicate northBound = criteriaBuilder.lessThanOrEqualTo(root.get(LATITUDE), north);
				Predicate southBound = criteriaBuilder.greaterThanOrEqualTo(root.get(LATITUDE), south);
				Predicate westBound = criteriaBuilder.greaterThanOrEqualTo(root.get(LONGITUDE), west);
				Predicate eastBound = criteriaBuilder.lessThanOrEqualTo(root.get(LONGITUDE), east);
				return criteriaBuilder.and(northBound, southBound, westBound, eastBound);
			}
		};
	}

	public static Specification<Site> withinBoundingBoxOrAssignedTo(Float north, Float south, Float east, Float west, Integer assigneeId) {
		return new Specification<Site>() {
			@Override
			public Predicate toPredicate(Root<Site> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
				Join<Site, UserProfile> siteAssignee = root.join(ASSIGNEE, JoinType.LEFT);
				Predicate northBound = cb.lessThanOrEqualTo(root.get(LATITUDE), north);
				Predicate southBound = cb.greaterThanOrEqualTo(root.get(LATITUDE), south);
				Predicate westBound = cb.greaterThanOrEqualTo(root.get(LONGITUDE), west);
				Predicate eastBound = cb.lessThanOrEqualTo(root.get(LONGITUDE), east);
				Predicate inBounds = cb.and(northBound, southBound, westBound, eastBound);
				Predicate assignedTo = cb.equal(siteAssignee.get(ID), assigneeId);
				return cb.or(assignedTo, inBounds);
			}
		};
	}

	public static Specification<Site> shoppingCenterIdEquals(Integer id) {
		return new Specification<Site>() {
			@Override
			public Predicate toPredicate(Root<Site> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
				Join<Site, ShoppingCenter> siteShoppingCenterJoin = root.join(SHOPPING_CENTER);
				return criteriaBuilder.equal(siteShoppingCenterJoin.get(ID), id);
			}
		};
	}

	public static Specification<Site> assigneeIdEquals(Integer assigneeId) {
		return new Specification<Site>() {
			@Override
			public Predicate toPredicate(Root<Site> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
				Join<Site, UserProfile> siteAssignee = root.join(ASSIGNEE);
				return criteriaBuilder.equal(siteAssignee.get(ID), assigneeId);
			}
		};
	}

	public static Specification<Site> hasActiveStore() {
		return new Specification<Site>() {
			@Override
			public Predicate toPredicate(Root<Site> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
				Join<Site, Store> siteStoreJoin = root.join("stores");
				return criteriaBuilder.equal(siteStoreJoin.get("storeType"), StoreType.ACTIVE);
			}
		};
	}

	public static Specification<Site> hasNoStore() {
		return new Specification<Site>() {
			@Override
			public Predicate toPredicate(Root<Site> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
				return criteriaBuilder.isEmpty(root.get("stores"));
			}
		};
	}

}

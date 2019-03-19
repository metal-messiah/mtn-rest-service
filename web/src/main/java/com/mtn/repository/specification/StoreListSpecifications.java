package com.mtn.repository.specification;

import com.mtn.model.domain.Store;
import com.mtn.model.domain.StoreList;
import com.mtn.model.domain.StoreList_;
import com.mtn.model.domain.UserProfile;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Join;

/**
 * Created by Allen on 4/23/2017.
 */
public class StoreListSpecifications extends AuditingEntitySpecifications {

	private static final String ID = "id";

	public static Specification<StoreList> subscriberIdEquals(Integer subscriberId) {
		return new Specification<StoreList>() {
			@Override
			public Predicate toPredicate(Root<StoreList> root, CriteriaQuery<?> criteriaQuery,
					CriteriaBuilder criteriaBuilder) {
				Join<StoreList, UserProfile> storeListSubscriberJoin = root.join("subscribers");
				return criteriaBuilder.equal(storeListSubscriberJoin.get(ID), subscriberId);
			}
		};
	}

	public static Specification<StoreList> storeIdEquals(Integer storeId) {
		return new Specification<StoreList>() {
			@Override
			public Predicate toPredicate(Root<StoreList> root, CriteriaQuery<?> criteriaQuery,
					CriteriaBuilder criteriaBuilder) {
				Join<StoreList, Store> storeListStoreJoin = root.join("stores");
				return criteriaBuilder.equal(storeListStoreJoin.get(ID), storeId);
			}
		};
	}

	public static Specification<StoreList> createdByEquals(Integer id) {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(StoreList_.createdBy), id);
	}
}

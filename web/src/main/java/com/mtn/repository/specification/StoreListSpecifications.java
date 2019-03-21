package com.mtn.repository.specification;

import com.mtn.model.domain.Store;
import com.mtn.model.domain.StoreList;
import com.mtn.model.domain.StoreList_;
import com.mtn.model.domain.Store_;
import com.mtn.model.domain.UserProfile;
import com.mtn.model.domain.UserProfile_;
import com.mtn.service.StoreService;

import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

	public static Specification<StoreList> getStoreListsWhereAnySubscriberIsMember(List<UserProfile> users) {
		return (root, criteriaQuery, criteriaBuilder) -> users.stream()
				.map(user -> criteriaBuilder.isMember(user, root.get(StoreList_.subscribers)))
				.reduce(criteriaBuilder::or).orElse(criteriaBuilder.isNotNull(root.get(StoreList_.id)));

	}

	public static Specification<StoreList> getStoreListsWhereAllSubscribersAreMembers(List<UserProfile> users) {
		return (root, criteriaQuery, criteriaBuilder) -> users.stream()
				.map(user -> criteriaBuilder.isMember(user, root.get(StoreList_.subscribers)))
				.reduce(criteriaBuilder::and).orElse(criteriaBuilder.isNotNull(root.get(StoreList_.id)));
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

	public static Specification<StoreList> storeIdIn(List<Integer> ids) {
		return (root, criteriaQuery, criteriaBuilder) -> {
			criteriaQuery.distinct(true);
			return root.join(StoreList_.stores).get(Store_.id).in(ids);
		};
	}

	public static Specification<StoreList> storeIdNotIn(List<Integer> ids) {
		return (root, criteriaQuery, criteriaBuilder) -> {
			criteriaQuery.distinct(true);
			return root.join(StoreList_.stores).get(Store_.id).in(ids).not();
		};
	}

	public static Specification<StoreList> getStoreListsWhereAnyStoreIsMember(List<Store> includedStores) {
		return (root, criteriaQuery, criteriaBuilder) -> includedStores.stream()
				.map(store -> criteriaBuilder.isMember(store, root.get(StoreList_.stores))).reduce(criteriaBuilder::or)
				.orElse(criteriaBuilder.isNotNull(root.get(StoreList_.id)));
	}

	public static Specification<StoreList> getStoreListsWhereAnyStoreIsNotMember(List<Store> includedStores) {
		return (root, criteriaQuery, criteriaBuilder) -> includedStores.stream()
				.map(store -> criteriaBuilder.isNotMember(store, root.get(StoreList_.stores)))
				.reduce(criteriaBuilder::or).orElse(criteriaBuilder.isNotNull(root.get(StoreList_.id)));
	}

	public static Specification<StoreList> getStoreListsWhereAllStoresAreMembers(List<Store> includedStores) {
		return (root, criteriaQuery, criteriaBuilder) -> includedStores.stream()
				.map(store -> criteriaBuilder.isMember(store, root.get(StoreList_.stores))).reduce(criteriaBuilder::and)
				.orElse(criteriaBuilder.isNotNull(root.get(StoreList_.id)));
	}

	public static Specification<StoreList> getStoreListsWhereAllStoresAreNotMembers(List<Store> includedStores) {
		return (root, criteriaQuery, criteriaBuilder) -> includedStores.stream()
				.map(store -> criteriaBuilder.isNotMember(store, root.get(StoreList_.stores)))
				.reduce(criteriaBuilder::and).orElse(criteriaBuilder.isNotNull(root.get(StoreList_.id)));
	}

	public static Specification<StoreList> createdByEquals(Integer id) {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(StoreList_.createdBy), id);
	}
}

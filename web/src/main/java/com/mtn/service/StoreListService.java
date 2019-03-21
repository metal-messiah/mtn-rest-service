package com.mtn.service;

import com.mtn.model.domain.Store;
import com.mtn.model.domain.Boundary;
import com.mtn.model.domain.Project;
import com.mtn.model.domain.StoreCasing;
import com.mtn.model.domain.StoreList;
import com.mtn.model.domain.UserProfile;
import com.mtn.model.view.ProjectView;
import com.mtn.model.view.StoreListView;
import com.mtn.model.simpleView.SimpleStoreListView;
import com.mtn.repository.ProjectRepository;
import com.mtn.repository.StoreListRepository;
import com.mtn.repository.specification.ProjectSpecifications;
import com.mtn.repository.specification.StoreListSpecifications;
import com.mtn.validators.ProjectValidator;
import com.mtn.validators.StoreListValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import static org.springframework.data.jpa.domain.Specifications.not;
import static org.springframework.data.jpa.domain.Specifications.where;

import com.mtn.constant.StoreListSearchType;

@Service
public class StoreListService extends EntityService<StoreList, StoreListView> {
	StoreService storeService;
	UserProfileService userProfileService;

	@Autowired
	public StoreListService(SecurityService securityService, StoreListRepository repository,
			StoreListValidator validator, StoreService storeService, UserProfileService userProfileService) {
		super(securityService, repository, validator, StoreList::new);
		this.storeService = storeService;
		this.userProfileService = userProfileService;
	}

	public Page<StoreList> findAllByQueryUsingSpecs(Pageable page, List<Integer> subscriberIds,
			List<Integer> includingStoreIds, List<Integer> excludingStoreIds, StoreListSearchType searchType) {
		Specifications<StoreList> spec = where(StoreListSpecifications.isNotDeleted());

		if (subscriberIds != null) {
			List<UserProfile> subscribers = this.userProfileService.findAllByIdsUsingSpecs(subscriberIds);
			if (searchType == StoreListSearchType.ANY) {
				spec = spec.and(StoreListSpecifications.getStoreListsWhereAnySubscriberIsMember(subscribers));
			} else {
				spec = spec.and(StoreListSpecifications.getStoreListsWhereAllSubscribersAreMembers(subscribers));
			}
			// spec = spec.and(StoreListSpecifications.subscriberIdEquals(subscriberId));
		}

		if (includingStoreIds != null) {
			List<Store> includedStores = this.storeService.findAllByIdsUsingSpecs(includingStoreIds);
			if (searchType == StoreListSearchType.ANY) {
				spec = spec.and(StoreListSpecifications.getStoreListsWhereAnyStoreIsMember(includedStores));
			} else {
				spec = spec.and(StoreListSpecifications.getStoreListsWhereAllStoresAreMembers(includedStores));
			}
		}

		if (excludingStoreIds != null) {
			List<Store> excludedStores = this.storeService.findAllByIdsUsingSpecs(excludingStoreIds);
			if (searchType == StoreListSearchType.ANY) {
				spec = spec.and(StoreListSpecifications.getStoreListsWhereAnyStoreIsNotMember(excludedStores));
			} else {
				spec = spec.and(StoreListSpecifications.getStoreListsWhereAllStoresAreNotMembers(excludedStores));
			}
		}

		return this.repository.findAll(spec, page);
	}

	public StoreList addStoresToStoreList(Integer storeListId, List<Integer> storeIds) {
		StoreList storeList = this.repository.findOne(where(StoreListSpecifications.idEquals(storeListId)));
		if (storeList == null) {
			throw new EntityNotFoundException("StoreList not found");
		}

		List<Store> storeListStores = storeList.getStores();
		List<Store> stores = this.storeService.findAllByIdsUsingSpecs(storeIds);
		stores.forEach(s -> {
			if (storeListStores.contains(s)) {
				// do nothing
			} else {
				storeList.addStore(s);
			}
		});

		this.repository.save(storeList);

		return storeList;
	}

	public StoreList removeStoresFromStoreList(Integer storeListId, List<Integer> storeIds) {
		StoreList storeList = this.repository.findOne(where(StoreListSpecifications.idEquals(storeListId)));
		if (storeList == null) {
			throw new EntityNotFoundException("StoreList not found");
		}

		List<Store> storeListStores = storeList.getStores();
		List<Store> stores = this.storeService.findAllByIdsUsingSpecs(storeIds);
		stores.forEach(s -> {
			if (storeListStores.contains(s)) {
				storeList.removeStore(s);
			} else {
				// do nothing
			}
		});

		this.repository.save(storeList);

		return storeList;
	}

	@Override
	protected void setEntityAttributesFromRequest(StoreList entity, StoreListView request) {
		entity.setStoreListName(request.getStoreListName());
	}

	@Override
	public void handleAssociationsOnDeletion(StoreList storeList) {
		storeList.getSubscribers().forEach(subscriber -> subscriber.getSubscribedStoreLists().remove(storeList));
	}
}

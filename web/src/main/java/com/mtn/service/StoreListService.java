package com.mtn.service;

import com.mtn.constant.StoreListSearchType;
import com.mtn.model.domain.Store;
import com.mtn.model.domain.StoreList;
import com.mtn.model.view.StoreListView;
import com.mtn.repository.StoreListRepository;
import com.mtn.repository.specification.StoreListSpecifications;
import com.mtn.validators.StoreListValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.springframework.data.jpa.domain.Specifications.where;

@Service
public class StoreListService extends EntityService<StoreList, StoreListView> {

	private final StoreService storeService;

	@Autowired
	public StoreListService(SecurityService securityService,
							StoreListRepository repository,
							StoreListValidator validator,
							StoreService storeService) {
		super(securityService, repository, validator, StoreList::new);
		this.storeService = storeService;
	}

	/**
	 * Currently finds only those lists which belong to the current user and which are not deleted.
	 * TODO Build in support for subscription to other user's lists
	 *
	 * @param page A pageable
	 * @param includingStoreIds Returned lists will be those that include Any|All of these store ids according to the search type
	 * @param excludingStoreIds Returned lists will be those that exclude Any|All of these store ids according to the search type
	 * @param searchType Specifies criteria if ANY or ALL ids must be included/excluded
	 * @return A page of store lists
	 */
	public Page<StoreList> findAllByQueryUsingSpecs(Pageable page,
													List<Integer> includingStoreIds,
													List<Integer> excludingStoreIds,
													StoreListSearchType searchType) {
		Specifications<StoreList> spec = where(StoreListSpecifications.isNotDeleted());

		spec = spec.and(StoreListSpecifications.createdByEquals(securityService.getCurrentUser().getId()));
		// TODO support subscriptions to other user's lists

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
			if (!storeListStores.contains(s)) {
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

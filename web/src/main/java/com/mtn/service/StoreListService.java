package com.mtn.service;

import com.mtn.model.domain.Store;
import com.mtn.model.domain.Boundary;
import com.mtn.model.domain.Project;
import com.mtn.model.domain.StoreCasing;
import com.mtn.model.domain.StoreList;
import com.mtn.model.view.ProjectView;
import com.mtn.model.view.StoreListView;
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

import static org.springframework.data.jpa.domain.Specifications.where;

@Service
public class StoreListService extends EntityService<StoreList, StoreListView> {
	StoreService storeService;

	@Autowired
	public StoreListService(SecurityService securityService, StoreListRepository repository,
			StoreListValidator validator, StoreService storeService) {
		super(securityService, repository, validator, StoreList::new);
		this.storeService = storeService;
	}

	public Page<StoreList> findAllByQueryUsingSpecs(Pageable page, Integer subscriberId) {
		Specifications<StoreList> spec = where(StoreListSpecifications.isNotDeleted());

		if (subscriberId != null) {
			spec = spec.and(StoreListSpecifications.subscriberIdEquals(subscriberId));
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
		// entity.setStores(request.getStores());
		// entity.setSubscribers(request.getSubscribers());
	}

	@Override
	public void handleAssociationsOnDeletion(StoreList storeList) {
		storeList.getSubscribers().forEach(subscriber -> subscriber.getSubscribedStoreLists().remove(storeList));
	}
}

package com.mtn.service;

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

import static org.springframework.data.jpa.domain.Specifications.where;

@Service
public class StoreListService extends EntityService<StoreList, StoreListView> {

	@Autowired
	public StoreListService(SecurityService securityService, StoreListRepository repository,
			StoreListValidator validator) {
		super(securityService, repository, validator, StoreList::new);
	}

	public Page<StoreList> findAllByQueryUsingSpecs(Pageable page, Integer subscriberId) {
		Specifications<StoreList> spec = where(StoreListSpecifications.isNotDeleted());

		if (subscriberId != null) {
			spec = spec.and(StoreListSpecifications.subscriberIdEquals(subscriberId));
		}

		return this.repository.findAll(spec, page);
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

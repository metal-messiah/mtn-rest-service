package com.mtn.service;

import com.mtn.model.domain.*;
import com.mtn.model.simpleView.SimpleStoreView;
import com.mtn.model.utils.StoreUtil;
import com.mtn.model.view.StoreCasingView;
import com.mtn.model.view.StoreStatusView;
import com.mtn.repository.StoreCasingRepository;
import com.mtn.repository.specification.StoreCasingSpecifications;
import com.mtn.validators.StoreCasingValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StoreCasingService extends StoreChildService<StoreCasing, StoreCasingView> {

	private final StoreStatusService storeStatusService;
	private final StoreSurveyService storeSurveyService;
	private final ShoppingCenterCasingService shoppingCenterCasingService;

	@Autowired
	public StoreCasingService(SecurityService securityService,
							  StoreCasingRepository repository,
							  StoreCasingValidator validator,
							  StoreStatusService storeStatusService,
							  StoreSurveyService storeSurveyService,
							  ShoppingCenterCasingService shoppingCenterCasingService) {
		super(securityService, repository, validator, StoreCasing::new);
		this.storeStatusService = storeStatusService;
		this.storeSurveyService = storeSurveyService;
		this.shoppingCenterCasingService = shoppingCenterCasingService;
	}

	public List<StoreCasing> findAllByProjectId(Integer projectId) {
		return this.repository.findAll(Specifications.where(StoreCasingSpecifications.projectIdEquals(projectId))
				.and(StoreCasingSpecifications.isNotDeleted()));
	}

	@Transactional
	public StoreCasing setStoreVolume(Integer storeCasingId, StoreVolume volume) {
		StoreCasing casing = this.findOne(storeCasingId);
		casing.setStoreVolume(volume);
		return casing;
	}

	@Transactional
	public StoreCasing addProject(Integer storeCasingId, Project project) {
		StoreCasing casing = this.findOne(storeCasingId);
		casing.getProjects().add(project);
		return casing;
	}

	@Transactional
	public StoreCasing addOneCasingToStore(StoreCasingView request, Store store) {
		this.validator.validateForInsert(request);

		UserProfile currentUser = this.securityService.getCurrentUser();

		StoreCasing casing = createNewEntityFromRequest(request);
		casing.setCreatedBy(currentUser);
		casing.setUpdatedBy(currentUser);

		casing.setStore(store);

		// If store has no status or status is outdated, create a new store status to to match casing status
		Optional<StoreStatus> current = StoreUtil.getLatestStatusAsOfDateTime(store, request.getCasingDate());
		if (current.isPresent()) {
			casing.setStoreStatus(current.get().getStatus());
		} else {
			casing.setStoreStatus("Open");
			StoreStatusView storeStatusRequest = new StoreStatusView();
			storeStatusRequest.setStatusStartDate(request.getCasingDate());
			storeStatusRequest.setStatus("Open");
			storeStatusService.addOneToStore(storeStatusRequest, casing.getStore());
		}

		StoreSurvey survey = storeSurveyService.getCloneOfLatestForStore(store, casing.getCasingDate());
		casing.setStoreSurvey(survey);

		ShoppingCenterCasing scCasing = shoppingCenterCasingService
				.createNewForShoppingCenter(store.getSite().getShoppingCenter(), casing.getCasingDate());
		casing.setShoppingCenterCasing(scCasing);

		return this.repository.save(casing);
	}

	@Transactional
	public StoreCasing removeProject(Integer storeCasingId, Integer projectId) {
		StoreCasing casing = this.findOne(storeCasingId);
		casing.getProjects().removeIf(p -> p.getId().equals(projectId));
		return casing;
	}

	@Transactional
	public StoreCasing removeStoreVolume(Integer storeCasingId) {
		StoreCasing casing = this.findOne(storeCasingId);
		casing.setStoreVolume(null);
		return casing;
	}

	@Override
	protected void setEntityAttributesFromRequest(StoreCasing casing, StoreCasingView request) {
		casing.setCasingDate(request.getCasingDate());
		casing.setNote(request.getNote());
		casing.setConditionCeiling(request.getConditionCeiling());
		casing.setConditionCheckstands(request.getConditionCheckstands());
		casing.setConditionFloors(request.getConditionFloors());
		casing.setConditionFrozenRefrigerated(request.getConditionFrozenRefrigerated());
		casing.setConditionShelvingGondolas(request.getConditionShelvingGondolas());
		casing.setConditionWalls(request.getConditionWalls());
		casing.setFuelGallonsWeekly(request.getFuelGallonsWeekly());
		casing.setStoreStatus(request.getStoreStatus());

		// If store has no status or status is outdated, create a new store status to to match casing status
		Optional<StoreStatus> current = StoreUtil.getLatestStatusAsOfDateTime(casing.getStore(), request.getCasingDate());
		if (request.getId() != null && (!current.isPresent() || !current.get().getStatus().equals(request.getStoreStatus()))) {
			StoreStatusView storeStatus = new StoreStatusView();
			storeStatus.setStatusStartDate(request.getCasingDate());
			storeStatus.setStatus(request.getStoreStatus());
			storeStatus.setStore(new SimpleStoreView(casing.getStore()));
			storeStatusService.addOneToStore(storeStatus, casing.getStore());
		}
	}

	@Override
	public void handleAssociationsOnDeletion(StoreCasing existing) {
		existing.getProjects().forEach(project -> project.getStoreCasings().remove(existing));
	}
}

package com.mtn.service;

import com.mtn.model.domain.*;
import com.mtn.repository.StoreCasingRepository;
import com.mtn.validators.StoreCasingValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.mtn.repository.specification.StoreCasingSpecifications.*;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Created by Allen on 5/6/2017.
 */
@Service
public class StoreCasingServiceImpl extends EntityServiceImpl<StoreCasing> implements StoreCasingService {

	@Autowired
	private StoreCasingRepository storeCasingRepository;
	@Autowired
	private StoreVolumeService storeVolumeService;
	@Autowired
	private StoreStatusService storeStatusService;
	@Autowired
	private StoreCasingValidator storeCasingValidator;
	@Autowired
	private ProjectService projectService;

	@Override
	public List<StoreCasing> findAllByProjectId(Integer projectId) {
		return getEntityRepository().findAllByProjectsIdAndDeletedDateIsNull(projectId);
	}

	@Override
	public List<StoreCasing> findAllByStoreId(Integer storeId) {
		return getEntityRepository().findAllByStoreId(storeId);
	}

	@Override
	public List<StoreCasing> findAllByStoreIdUsingSpecs(Integer storeId) {
		return getEntityRepository().findAll(
				where(storeIdEquals(storeId))
						.and(isNotDeleted())
		);
	}

	@Override
	public Page<StoreCasing> findAllUsingSpecs(Pageable page) {
		return getEntityRepository().findAll(isNotDeleted(), page);
	}

	@Override
	public StoreCasing findOneUsingSpecs(Integer id) {
		return getEntityRepository().findOne(
				where(idEquals(id))
						.and(isNotDeleted())
		);
	}

	@Override
	@Transactional
	public StoreCasing setStoreVolume(Integer storeCasingId, Integer storeVolumeId) {
		StoreCasing casing = this.findOne(storeCasingId);
		StoreVolume volume = this.storeVolumeService.findOne(storeVolumeId);
		casing.setStoreVolume(volume);

		return casing;
	}

	@Override
	@Transactional
	public StoreCasing createStoreVolume(Integer storeCasingId, StoreVolume volumeRequest) {
		StoreCasing casing = this.findOne(storeCasingId);
		UserProfile currentUser = securityService.getCurrentUser();
		volumeRequest.setStore(casing.getStore());
		StoreVolume savedVolume = this.storeVolumeService.addOne(volumeRequest);

		casing.setStoreVolume(savedVolume);
		casing.setUpdatedBy(currentUser);

		return casing;
	}

	@Override
	@Transactional
	public StoreCasing addProject(Integer storeCasingId, Integer projectId) {
		StoreCasing casing = this.findOne(storeCasingId);
		Project project = this.projectService.findOne(projectId);
		casing.getProjects().add(project);

		return casing;
	}

	@Override
	@Transactional
	public StoreCasing removeProject(Integer storeCasingId, Integer projectId) {
		StoreCasing casing = this.findOne(storeCasingId);
		casing.getProjects().removeIf(p -> p.getId().equals(projectId));

		return casing;
	}

	@Override
	@Transactional
	public StoreCasing removeStoreVolume(Integer storeCasingId) {
		StoreCasing casing = this.findOne(storeCasingId);
		casing.setStoreVolume(null);

		return casing;
	}

	@Override
	public StoreCasing updateEntityAttributes(StoreCasing existing, StoreCasing request) {
		existing.setCasingDate(request.getCasingDate());
		existing.setNote(request.getNote());
		existing.setConditionCeiling(request.getConditionCeiling());
		existing.setConditionCheckstands(request.getConditionCheckstands());
		existing.setConditionFloors(request.getConditionFloors());
		existing.setConditionFrozenRefrigerated(request.getConditionFrozenRefrigerated());
		existing.setConditionShelvingGondolas(request.getConditionShelvingGondolas());
		existing.setConditionWalls(request.getConditionWalls());
		existing.setFuelGallonsWeekly(request.getFuelGallonsWeekly());
		existing.setPharmacyScriptsWeekly(request.getPharmacyScriptsWeekly());
		existing.setPharmacyAvgDollarsPerScript(request.getPharmacyAvgDollarsPerScript());
		existing.setPharmacyPharmacistCount(request.getPharmacyPharmacistCount());
		existing.setPharmacyTechnicianCount(request.getPharmacyTechnicianCount());
		existing.setStoreStatus(request.getStoreStatus());

		// If most recent since casing date
		List<StoreStatus> statuses = existing.getStore().getStatuses();
		if (statuses.stream().noneMatch(storeStatus -> storeStatus.getStatus().equals(request.getStoreStatus()))) {
			StoreStatus storeStatus = new StoreStatus();
			storeStatus.setStatusStartDate(request.getCasingDate());
			storeStatus.setStatus(request.getStoreStatus());
			storeStatus.setStore(existing.getStore());
			storeStatusService.addOne(storeStatus);
		}

		return existing;
	}

	@Override
	public String getEntityName() {
		return "StoreCasing";
	}

	@Override
	public void handleAssociationsOnDeletion(StoreCasing existing) {
		// TODO - Handle Store and Survey
	}

	@Override
	public void handleAssociationsOnCreation(StoreCasing request) {
		// TODO - Handle Store and Survey
	}

	@Override
	public StoreCasingRepository getEntityRepository() {
		return storeCasingRepository;
	}

	@Override
	public StoreCasingValidator getValidator() {
		return storeCasingValidator;
	}
}

package com.mtn.service;

import com.mtn.model.domain.*;
import com.mtn.model.utils.StoreUtil;
import com.mtn.repository.StoreCasingRepository;
import com.mtn.repository.specification.AuditingEntitySpecifications;
import com.mtn.repository.specification.StoreCasingSpecifications;
import com.mtn.validators.StoreCasingValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Created by Allen on 5/6/2017.
 */
@Service
public class StoreCasingServiceImpl extends StoreChildServiceImpl<StoreCasing> implements StoreCasingService {

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
		return storeCasingRepository.findAll(Specifications.where(StoreCasingSpecifications.projectIdEquals(projectId))
				.and(AuditingEntitySpecifications.isNotDeleted()));
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

		// If store has no status or status is outdated, create a new store status to to match casing status
		Optional<StoreStatus> current = StoreUtil.getLatestStatusAsOfDateTime(existing.getStore(), request.getCasingDate());
		if (!current.isPresent() || !current.get().getStatus().equals(request.getStoreStatus())) {
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
	public StoreCasingValidator getValidator() {
		return storeCasingValidator;
	}
}

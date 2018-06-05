package com.mtn.service;

import com.mtn.model.domain.Project;
import com.mtn.model.domain.StoreCasing;
import com.mtn.model.domain.StoreVolume;
import com.mtn.repository.StoreCasingRepository;
import com.mtn.validators.StoreCasingValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
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
		if (casing == null) {
			throw new EntityNotFoundException("No StoreCasing found with id: " + storeCasingId);
		}
		StoreVolume volume = this.storeVolumeService.findOne(storeVolumeId);
		if (volume == null) {
			throw new EntityNotFoundException("No StoreVolume found with id: " + storeVolumeId);
		}
		casing.setStoreVolume(volume);

		return casing;
	}

	@Override
	@Transactional
	public StoreCasing addProject(Integer storeCasingId, Integer projectId) {
		StoreCasing casing = this.findOne(storeCasingId);
		if (casing == null) {
			throw new EntityNotFoundException("No StoreCasing found with id: " + storeCasingId);
		}
		Project project = this.projectService.findOne(projectId);
		if (project == null) {
			throw new EntityNotFoundException("No Project found with id: " + projectId);
		}
		casing.getProjects().add(project);

		return casing;
	}

	@Override
	@Transactional
	public StoreCasing removeProject(Integer storeCasingId, Integer projectId) {
		StoreCasing casing = this.findOne(storeCasingId);
		if (casing == null) {
			throw new EntityNotFoundException("No StoreCasing found with id: " + storeCasingId);
		}
		casing.getProjects().removeIf(p -> p.getId().equals(projectId));

		return casing;
	}

	@Override
	@Transactional
	public StoreCasing removeStoreVolume(Integer storeCasingId) {
		StoreCasing casing = this.findOne(storeCasingId);
		if (casing == null) {
			throw new EntityNotFoundException("No StoreCasing found with id: " + storeCasingId);
		}
		casing.setStoreVolume(null);

		return casing;
	}

	@Override
	public StoreCasing getUpdatedEntity(StoreCasing existing, StoreCasing request) {
		existing.setNote(request.getNote());
		existing.setStoreStatus(request.getStoreStatus());
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
		existing.setLegacyCasingId(request.getLegacyCasingId());

		// TODO Save storeVolume

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

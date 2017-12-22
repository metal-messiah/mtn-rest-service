package com.mtn.service;

import com.mtn.exception.VersionConflictException;
import com.mtn.model.domain.StoreCasing;
import com.mtn.model.domain.UserProfile;
import com.mtn.model.simpleView.SimpleStoreCasingView;
import com.mtn.repository.StoreCasingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.mtn.repository.specification.StoreCasingSpecifications.*;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Created by Allen on 5/6/2017.
 */
@Service
public class StoreCasingService extends ValidatingDataService<StoreCasing> {

    @Autowired
    private StoreCasingRepository casingRepository;
    @Autowired
    private SecurityService securityService;

    @Transactional
    public StoreCasing addOne(StoreCasing request) {
        validateForInsert(request);

        UserProfile currentUser = securityService.getCurrentUser();
        request.setCreatedBy(currentUser);
        request.setUpdatedBy(currentUser);

        return casingRepository.save(request);
    }

    @Transactional
    public void deleteOne(Integer id) {
        StoreCasing existing = findOneUsingSpecs(id);
        if (existing == null) {
            throw new IllegalArgumentException("No StoreCasing found with this id");
        }

        existing.setDeletedBy(securityService.getCurrentUser());
    }

    public List<StoreCasing> findAllByProjectId(Integer id) {
        return casingRepository.findAllByInteractionsProjectIdAndDeletedDateIsNull(id);
    }

    public List<StoreCasing> findAllByStoreId(Integer storeId) {
        return casingRepository.findAllByStoreId(storeId);
    }

    public List<StoreCasing> findAllByStoreIdUsingSpecs(Integer storeId) {
        return casingRepository.findAll(
                where(storeIdEquals(storeId))
                        .and(isNotDeleted())
        );
    }

    public StoreCasing findOne(Integer id) {
        return casingRepository.findOne(id);
    }

    public StoreCasing findOneUsingSpecs(Integer id) {
        return casingRepository.findOne(
                where(idEquals(id))
                        .and(isNotDeleted())
        );
    }

    @Transactional
    public StoreCasing updateOne(Integer id, StoreCasing request) {
        validateNotNull(request);
        validateForUpdate(request);

        StoreCasing existing = findOneUsingSpecs(id);
        if (existing == null) {
            throw new IllegalArgumentException("No StoreCasing found with this id");
        }
        if (!request.getVersion().equals(existing.getVersion())) {
            throw new VersionConflictException(new SimpleStoreCasingView(existing));
        }

        existing.setNote(request.getNote());
        existing.setStatus(request.getStatus());
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
        existing.setVolumeGrocery(request.getVolumeGrocery());
        existing.setVolumePercentGrocery(request.getVolumePercentGrocery());
        existing.setVolumeMeat(request.getVolumeMeat());
        existing.setVolumePercentMeat(request.getVolumePercentMeat());
        existing.setVolumeNonFood(request.getVolumeNonFood());
        existing.setVolumePercentNonFood(request.getVolumePercentNonFood());
        existing.setVolumeOther(request.getVolumeOther());
        existing.setVolumePercentOther(request.getVolumePercentOther());
        existing.setVolumePercentProduce(request.getVolumePercentProduce());
        existing.setVolumeProduce(request.getVolumeProduce());
        existing.setVolumePlusMinus(request.getVolumePlusMinus());
        existing.setVolumeNote(request.getVolumeNote());
        existing.setVolumeTotal(request.getVolumeTotal());
        existing.setVolumeConfidence(request.getVolumeConfidence());
        existing.setVersion(request.getVersion());
        existing.setLegacyCasingId(request.getLegacyCasingId());
        existing.setUpdatedBy(securityService.getCurrentUser());

        return existing;
    }

    @Override
    public String getEntityName() {
        return "StoreCasing";
    }

    @Override
    public void validateForInsert(StoreCasing object) {
        super.validateForInsert(object);

        if (object.getStore() == null) {
            throw new IllegalStateException("StoreCasing was not mapped to Store before saving!");
        }
        if (object.getVersion() == null) {
            throw new IllegalArgumentException("StoreCasing version must be provided");
        }
    }

    @Override
    public void validateBusinessRules(StoreCasing object) {
        //No business requirements to enforce yet
    }

    @Override
    public void validateDoesNotExist(StoreCasing object) {
        //No unique contraints to enforce
    }
}

package com.mtn.service;

import com.mtn.exception.VersionConflictException;
import com.mtn.model.domain.StoreVolume;
import com.mtn.model.domain.UserProfile;
import com.mtn.model.simpleView.SimpleStoreVolumeView;
import com.mtn.repository.StoreVolumeRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.mtn.repository.specification.StoreVolumeSpecifications.*;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Created by Allen on 5/6/2017.
 */
@Service
public class StoreVolumeService extends ValidatingDataService<StoreVolume> {

    @Autowired
    private StoreVolumeRepository volumeRepository;
    @Autowired
    private SecurityService securityService;

    @Transactional
    public StoreVolume addOne(StoreVolume request) {
        validateForInsert(request);

        UserProfile currentUser = securityService.getCurrentUser();
        request.setCreatedBy(currentUser);
        request.setUpdatedBy(currentUser);

        return volumeRepository.save(request);
    }

    @Transactional
    public void deleteOne(Integer id) {
        StoreVolume existing = findOneUsingSpecs(id);
        if (existing == null) {
            throw new IllegalArgumentException("No StoreVolume found with this id");
        }

        existing.setDeletedBy(securityService.getCurrentUser());
    }

    public List<StoreVolume> findAllByStoreId(Integer storeId) {
        return volumeRepository.findAllByStoreId(storeId);
    }

    public List<StoreVolume> findAllByStoreIdUsingSpecs(Integer storeId) {
        return volumeRepository.findAll(
                where(storeIdEquals(storeId))
                        .and(isNotDeleted())
        );
    }

    public StoreVolume findOne(Integer id) {
        return volumeRepository.findOne(id);
    }

    public StoreVolume findOneUsingSpecs(Integer id) {
        return volumeRepository.findOne(
                where(idEquals(id))
                        .and(isNotDeleted())
        );
    }

    @Transactional
    public StoreVolume updateOne(Integer id, StoreVolume request) {
        validateNotNull(request);
        validateForUpdate(request);

        StoreVolume existing = findOneUsingSpecs(id);
        if (existing == null) {
            throw new IllegalArgumentException("No StoreVolume found with this id");
        }
        if (!request.getVersion().equals(existing.getVersion())) {
            throw new VersionConflictException(new SimpleStoreVolumeView(existing));
        }

        existing.setVolumeTotal(request.getVolumeTotal());
        existing.setVolumeDate(request.getVolumeDate());
        existing.setVolumeType(request.getVolumeType());
        existing.setSource(request.getSource());
        existing.setVersion(request.getVersion());
        existing.setLegacyCasingId(request.getLegacyCasingId());
        existing.setUpdatedBy(securityService.getCurrentUser());

        return existing;
    }

    @Override
    public String getEntityName() {
        return "StoreVolume";
    }

    @Override
    public void validateForInsert(StoreVolume object) {
        super.validateForInsert(object);

        if (object.getStore() == null) {
            throw new IllegalStateException("StoreVolume was not mapped to Store before saving!");
        }
        if (object.getVersion() == null) {
            throw new IllegalArgumentException("StoreVolume version must be provided");
        }
    }

    @Override
    public void validateBusinessRules(StoreVolume object) {
        if (object.getVolumeTotal() == null) {
            throw new IllegalArgumentException("StoreVolume volumeTotal must be provided");
        } else if (object.getVolumeDate() == null) {
            throw new IllegalArgumentException("StoreVolume volumeDate must be provided");
        } else if (object.getVolumeType() == null) {
            throw new IllegalArgumentException("StoreVolume volumeType must be provided");
        } else if (StringUtils.isBlank(object.getSource())) {
            throw new IllegalArgumentException("StoreVolume source must be provided");
        }
    }

    @Override
    public void validateUnique(StoreVolume object) {
        //No unique contraints to enforce
    }
}

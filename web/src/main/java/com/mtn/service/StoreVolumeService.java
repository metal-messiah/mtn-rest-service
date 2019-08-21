package com.mtn.service;

import com.mtn.model.domain.Store;
import com.mtn.model.domain.StoreVolume;
import com.mtn.model.domain.UserProfile;
import com.mtn.model.view.StoreVolumeView;
import com.mtn.repository.StoreVolumeRepository;
import com.mtn.repository.specification.AuditingEntitySpecifications;
import com.mtn.repository.specification.StoreVolumeSpecifications;
import com.mtn.validators.StoreVolumeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.data.jpa.domain.Specifications.where;

@Service
public class StoreVolumeService extends StoreChildService<StoreVolume, StoreVolumeView> {

    @Autowired
    public StoreVolumeService(SecurityService securityService,
                              StoreVolumeRepository repository,
                              StoreVolumeValidator validator) {
        super(securityService, repository, validator, StoreVolume::new);
    }

    public List<StoreVolume> findAllBySourceAndDate(String source, LocalDate volumeDate) {
        Specifications<StoreVolume> spec = where(AuditingEntitySpecifications.isNotDeleted());
        spec = spec.and(StoreVolumeSpecifications.sourceEquals(source));
        spec = spec.and(StoreVolumeSpecifications.volumeDateEquals(volumeDate));
        return this.repository.findAll(spec);
    }

    @Transactional
    public StoreVolume addOneToStore(StoreVolumeView request, Store store) {
        this.validator.validateForInsert(request);

        UserProfile currentUser = this.securityService.getCurrentUser();

        StoreVolume volume = createNewEntityFromRequest(request);
        volume.setCreatedBy(currentUser);
        volume.setUpdatedBy(currentUser);

        volume.setStore(store);

        return this.repository.save(volume);
    }

    @Override
    protected void setEntityAttributesFromRequest(StoreVolume volume, StoreVolumeView request) {
        volume.setVolumeTotal(request.getVolumeTotal());
        volume.setVolumeBoxTotal(request.getVolumeBoxTotal());
        volume.setVolumeDate(request.getVolumeDate());
        volume.setVolumeType(request.getVolumeType());
        volume.setSource(request.getSource());
        volume.setVolumeGrocery(request.getVolumeGrocery());
        volume.setVolumePercentGrocery(request.getVolumePercentGrocery());
        volume.setVolumeMeat(request.getVolumeMeat());
        volume.setVolumePercentMeat(request.getVolumePercentMeat());
        volume.setVolumeNonFood(request.getVolumeNonFood());
        volume.setVolumePercentNonFood(request.getVolumePercentNonFood());
        volume.setVolumeOther(request.getVolumeOther());
        volume.setVolumePercentOther(request.getVolumePercentOther());
        volume.setVolumePercentProduce(request.getVolumePercentProduce());
        volume.setVolumeProduce(request.getVolumeProduce());
        volume.setVolumePlusMinus(request.getVolumePlusMinus());
        volume.setVolumeNote(request.getVolumeNote());
        volume.setVolumeConfidence(request.getVolumeConfidence());
    }

    @Override
    public void handleAssociationsOnDeletion(StoreVolume existing) {
        // Do Nothing
    }
}

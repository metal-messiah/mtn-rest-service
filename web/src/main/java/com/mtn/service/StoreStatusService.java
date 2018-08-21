package com.mtn.service;

import com.mtn.model.domain.*;
import com.mtn.model.utils.StoreUtil;
import com.mtn.model.view.StoreStatusView;
import com.mtn.repository.StoreStatusRepository;
import com.mtn.validators.StoreStatusValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class StoreStatusService extends StoreChildServiceImpl<StoreStatus, StoreStatusView> {

    @Autowired
    public StoreStatusService(EntityServiceDependencies services,
                              StoreStatusRepository repository,
                              StoreStatusValidator validator) {
        super(services, repository, validator);
    }

    @Transactional
    public StoreStatus addOneToStore(StoreStatusView request, Store store) {
        this.validator.validateForInsert(request);

        UserProfile currentUser = this.securityService.getCurrentUser();

        StoreStatus status = createNewEntityFromRequest(request);
        status.setCreatedBy(currentUser);
        status.setUpdatedBy(currentUser);

        status.setStore(store);

        return status;
    }

    @Transactional
    public void updateStoreStatusesFromCasing(StoreCasing casing) {
        Optional<StoreStatus> storeStatus = StoreUtil.getLatestStatusAsOfDateTime(casing.getStore(), casing.getCasingDate());
        if (!storeStatus.isPresent() || storeStatus.get().getStatus().equals(casing.getStoreStatus())) {
            StoreStatus newStatus = new StoreStatus();
            newStatus.setStatusStartDate(casing.getCasingDate());
            newStatus.setStatus("Open");
            newStatus.setStore(casing.getStore());
        }
    }

    @Override
    protected StoreStatus createNewEntity() {
        return new StoreStatus();
    }

    @Override
    protected void setEntityAttributesFromRequest(StoreStatus status, StoreStatusView request) {
        status.setStatus(request.getStatus());
        status.setStatusStartDate(request.getStatusStartDate());
    }

    @Override
    public void handleAssociationsOnDeletion(StoreStatus existing) {
        existing.setStore(null);
    }
}

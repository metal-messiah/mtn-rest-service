package com.mtn.service;

import com.mtn.model.domain.StoreStatus;
import com.mtn.repository.StoreStatusRepository;
import com.mtn.validators.StoreStatusValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mtn.repository.specification.StoreStatusSpecifications.*;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Created by Allen on 5/6/2017.
 */
@Service
public class StoreStatusServiceImpl extends EntityServiceImpl<StoreStatus> implements StoreStatusService {

    @Autowired
    private StoreStatusRepository storeStatusRepository;
    @Autowired
    private StoreStatusValidator storeStatusValidator;

    @Override
	public List<StoreStatus> findAllByStoreId(Integer storeId) {
        return getEntityRepository().findAllByStoreId(storeId);
    }

    @Override
	public List<StoreStatus> findAllByStoreIdUsingSpecs(Integer storeId) {
        return getEntityRepository().findAll(
                where(storeIdEquals(storeId))
                        .and(isNotDeleted())
        );
    }

    @Override
    public Page<StoreStatus> findAllUsingSpecs(Pageable page) {
        return getEntityRepository().findAll(where(isNotDeleted()), page);
    }

    @Override
    public StoreStatus findOneUsingSpecs(Integer id) {
        return getEntityRepository().findOne(
                where(idEquals(id))
                        .and(isNotDeleted())
        );
    }

    @Override
    public StoreStatus updateEntityAttributes(StoreStatus existing, StoreStatus request) {
        existing.setStatus(request.getStatus());
        existing.setStatusStartDate(request.getStatusStartDate());

        return existing;
    }

    @Override
    public String getEntityName() {
        return "StoreStatus";
    }

    @Override
    public void handleAssociationsOnDeletion(StoreStatus existing) {
        // TODO - Handle Store
    }

    @Override
    public void handleAssociationsOnCreation(StoreStatus request) {
        // TODO - Handle Store
    }

    @Override
    public StoreStatusRepository getEntityRepository() {
        return storeStatusRepository;
    }

    @Override
    public StoreStatusValidator getValidator() {
        return storeStatusValidator;
    }

}

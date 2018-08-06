package com.mtn.service;

import com.mtn.model.domain.StoreStatus;
import com.mtn.validators.StoreStatusValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Allen on 5/6/2017.
 */
@Service
public class StoreStatusServiceImpl extends StoreChildServiceImpl<StoreStatus> implements StoreStatusService {

    @Autowired
    private StoreStatusValidator storeStatusValidator;

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
    public StoreStatusValidator getValidator() {
        return storeStatusValidator;
    }

}

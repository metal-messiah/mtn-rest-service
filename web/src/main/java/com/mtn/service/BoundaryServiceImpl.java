package com.mtn.service;

import com.mtn.model.domain.*;
import com.mtn.repository.BoundaryRepository;
import com.mtn.validators.BoundaryValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoundaryServiceImpl extends EntityServiceImpl<Boundary> implements BoundaryService {

    @Autowired
    BoundaryRepository boundaryRepository;
    @Autowired
    BoundaryValidator boundaryValidator;

    @Override
    public Boundary updateEntityAttributes(Boundary existing, Boundary request) {
        existing.setGeojson(request.getGeojson());
        return existing;
    }

    @Override
    public String getEntityName() {
        return "Boundary";
    }

    @Override
    public BoundaryValidator getValidator() {
        return boundaryValidator;
    }
}

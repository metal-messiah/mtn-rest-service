package com.mtn.service;

import com.mtn.model.domain.Boundary;
import com.mtn.model.view.BoundaryView;
import com.mtn.repository.BoundaryRepository;
import com.mtn.validators.BoundaryValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoundaryService extends EntityServiceImpl<Boundary, BoundaryView> {

    @Autowired
    public BoundaryService(EntityServiceDependencies services,
                           BoundaryRepository boundaryRepository,
                           BoundaryValidator boundaryValidator) {
        super(services, boundaryRepository, boundaryValidator);
    }

    @Override
    protected Boundary createNewEntity() {
        return new Boundary();
    }

    @Override
    protected void setEntityAttributesFromRequest(Boundary object, BoundaryView request) {
        object.setGeojson(request.getGeojson());
    }

    @Override
    public void handleAssociationsOnDeletion(Boundary existing) {
        existing.getProjects().forEach(project -> project.setBoundary(null));
    }
}

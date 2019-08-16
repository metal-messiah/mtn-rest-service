package com.mtn.service;

import com.mtn.model.domain.UserBoundary;
import com.mtn.model.view.UserBoundaryView;
import com.mtn.repository.UserBoundaryRepository;
import com.mtn.repository.specification.UserBoundarySpecifications;
import com.mtn.validators.UserBoundaryValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.jpa.domain.Specifications.where;

@Service
public class UserBoundaryService extends EntityService<UserBoundary, UserBoundaryView> {

    @Autowired
    public UserBoundaryService(SecurityService securityService, UserBoundaryRepository repository,
            UserBoundaryValidator validator) {
        super(securityService, repository, validator, UserBoundary::new);
    }

    public List<UserBoundary> findRecordsByUserId(Integer userId) {
        Specifications<UserBoundary> spec = where(UserBoundarySpecifications.isNotDeleted());

        if (userId != null) {
            spec = spec.and(UserBoundarySpecifications.createdByEquals(userId));
        }

        List<UserBoundary> domainObjects = this.repository.findAll(spec);
        return domainObjects;
    }

    @Override
    protected void setEntityAttributesFromRequest(UserBoundary userBoundary, UserBoundaryView request) {
        userBoundary.setGeojson(request.getGeojson());
        userBoundary.setName(request.getName());
    }

    @Override
    public void handleAssociationsOnDeletion(UserBoundary existing) {
        // do nothing for now
    }

}

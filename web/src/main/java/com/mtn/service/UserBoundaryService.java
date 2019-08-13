package com.mtn.service;

import com.mtn.model.domain.UserBoundary;
import com.mtn.model.view.UserBoundaryView;
import com.mtn.repository.UserBoundaryRepository;
import com.mtn.repository.specification.UserBoundarySpecification;
import com.mtn.validators.UserBoundaryValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Comparator;
import java.util.List;

import static org.springframework.data.jpa.domain.Specifications.where;

@Service
public class UserBoundaryService extends EntityService<UserBoundary, UserBoundaryView> {

    @Autowired
    public UserBoundaryService(SecurityService securityService, UserBoundaryRepository repository,
            UserBoundaryValidator validator) {
        super(securityService, repository, validator, UserBoundary::new);
    }

    public UserBoundary findRecordsByUserId(Integer userId) {
        Specifications<UserBoundary> spec = where(UserBoundarySpecifications.isNotDeleted());

        if (userId != null) {
            spec = spec.and(UserBoundarySpecifications.createdByEquals(userId));
        }

        List<UserBoundary> domainObjects =  this.repository.findAll(spec);
        return domainObjects
    }

    @Override
    protected void setEntityAttributesFromRequest(ResourceQuota resourceQuota, ResourceQuotaView request) {
        resourceQuota.setResourceName(request.getResourceName());
        resourceQuota.setPeriodStartDate(request.getPeriodStartDate());
        resourceQuota.setQueryCount(request.getQueryCount());
        resourceQuota.setQuotaLimit(request.getQuotaLimit());
    }

    @Override
    public void handleAssociationsOnDeletion(ResourceQuota existing) {
        // do nothing for now
    }

}

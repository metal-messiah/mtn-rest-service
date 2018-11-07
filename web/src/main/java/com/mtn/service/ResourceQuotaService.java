package com.mtn.service;

import com.mtn.model.domain.ResourceQuota;
import com.mtn.model.view.ResourceQuotaView;
import com.mtn.repository.ResourceQuotaRepository;
import com.mtn.repository.specification.ResourceQuotaSpecifications;
import com.mtn.validators.ResourceQuotaValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

import static org.springframework.data.jpa.domain.Specifications.where;

import java.util.Comparator;
import java.util.List;

@Service
public class ResourceQuotaService extends EntityService<ResourceQuota, ResourceQuotaView> {

    @Autowired
    public ResourceQuotaService(SecurityService securityService, ResourceQuotaRepository repository,
            ResourceQuotaValidator validator) {
        super(securityService, repository, validator, ResourceQuota::new);
    }

    public ResourceQuota findNewestRecordByName(String name) {
        Specifications<ResourceQuota> spec = where(ResourceQuotaSpecifications.isNotDeleted());

        if (name != null) {
            spec = spec.and(ResourceQuotaSpecifications.resourceNameEquals(name));
        }

        List<ResourceQuota> domainObjects =  this.repository.findAll(spec);
        return domainObjects.stream().max(Comparator.comparing(ResourceQuota::getPeriodStartDate))
                .orElseThrow(EntityNotFoundException::new);
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

package com.mtn.service;

import com.mtn.model.domain.ResourceQuota;
import com.mtn.model.domain.UserProfile;
import com.mtn.model.view.ResourceQuotaView;
import com.mtn.repository.ResourceQuotaRepository;
import com.mtn.repository.specification.ResourceQuotaSpecifications;
import com.mtn.validators.ResourceQuotaValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.data.jpa.domain.Specifications.where;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ResourceQuotaService extends EntityService<ResourceQuota, ResourceQuotaView> {

    @Autowired
    public ResourceQuotaService(SecurityService securityService, ResourceQuotaRepository repository,
            ResourceQuotaValidator validator) {
        super(securityService, repository, validator, ResourceQuota::new);
    }

    public Page<ResourceQuota> findNewestRecordByName(Pageable page, String name) {
        System.out.println("FIND ALL BY NAME USING SPECS");
        Specifications<ResourceQuota> spec = where(ResourceQuotaSpecifications.isNotDeleted());

        if (name != null) {
            spec = spec.and(ResourceQuotaSpecifications.resourceNameEquals(name));
        }

        return this.repository.findAll(spec, page);

    }

    @Transactional
	public ResourceQuota addOne(ResourceQuotaView request) {
        this.validator.validateForInsert(request);
        
        UserProfile currentUser = securityService.getCurrentUser();
        
		ResourceQuota rq = createNewEntityFromRequest(request);
		rq.setCreatedBy(currentUser);
        rq.setUpdatedBy(currentUser);

        // rq.setPeriodStartDate(LocalDateTime.now());

		return this.repository.save(rq);
    }
    

    @Override
    protected void setEntityAttributesFromRequest(ResourceQuota resourceQuota, ResourceQuotaView request) {
        System.out.println("SET ENTITY ATTRIBUTES");
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

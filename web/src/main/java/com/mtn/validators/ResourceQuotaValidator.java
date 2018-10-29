package com.mtn.validators;

import com.mtn.model.domain.ResourceQuota;
import com.mtn.model.view.ResourceQuotaView;
import com.mtn.repository.ResourceQuotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceQuotaValidator extends EntityValidator<ResourceQuota, ResourceQuotaView> {

	@Autowired
	public ResourceQuotaValidator(ResourceQuotaRepository entityRepository) {
		super(entityRepository);
	}
}

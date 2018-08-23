package com.mtn.service;

import com.mtn.model.domain.ShoppingCenterSurvey;
import com.mtn.model.domain.ShoppingCenterTenant;
import com.mtn.model.domain.UserProfile;
import com.mtn.model.view.ShoppingCenterTenantView;
import com.mtn.repository.ShoppingCenterTenantRepository;
import com.mtn.repository.specification.AuditingEntitySpecifications;
import com.mtn.repository.specification.ShoppingCenterTenantSpecifications;
import com.mtn.validators.ShoppingCenterTenantValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingCenterTenantService extends EntityService<ShoppingCenterTenant, ShoppingCenterTenantView> {

	@Autowired
	public ShoppingCenterTenantService(SecurityService securityService,
									   ShoppingCenterTenantRepository repository,
									   ShoppingCenterTenantValidator validator) {
		super(securityService, repository, validator, ShoppingCenterTenant::new);
	}

	public List<ShoppingCenterTenant> findAllBySurveyIdUsingSpecs(Integer id) {
		return this.repository.findAll(
				Specifications.where(ShoppingCenterTenantSpecifications.shoppingCenterSurveyIdEquals(id))
						.and(AuditingEntitySpecifications.isNotDeleted())
		);
	}

	@Transactional
	public List<ShoppingCenterTenant> addMultiple(List<ShoppingCenterTenantView> requests, ShoppingCenterSurvey survey) {
		UserProfile currentUser = this.securityService.getCurrentUser();
		return requests.stream().map(request -> {
			this.validator.validateForInsert(request);
			ShoppingCenterTenant tenant = this.createNewEntityFromRequest(request);
			tenant.setSurvey(survey);
			tenant.setCreatedBy(currentUser);
			tenant.setCreatedDate(LocalDateTime.now());
			return tenant;
		}).collect(Collectors.toList());
	}

	@Override
	protected void setEntityAttributesFromRequest(ShoppingCenterTenant tenant, ShoppingCenterTenantView request) {
		tenant.setName(request.getName());
		tenant.setIsAnchor(request.getAnchor());
		tenant.setOutparcel(request.getOutparcel());
		tenant.setTenantSqft(request.getTenantSqft());
	}

	@Override
	public void handleAssociationsOnDeletion(ShoppingCenterTenant existing) {
		existing.setSurvey(null);
	}
}

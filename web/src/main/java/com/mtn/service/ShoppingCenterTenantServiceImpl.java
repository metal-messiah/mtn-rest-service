package com.mtn.service;

import com.mtn.model.domain.ShoppingCenterTenant;
import com.mtn.repository.ShoppingCenterTenantRepository;
import com.mtn.repository.specification.AuditingEntitySpecifications;
import com.mtn.repository.specification.ShoppingCenterTenantSpecifications;
import com.mtn.validators.ShoppingCenterTenantValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Allen on 5/6/2017.
 */
@Service
public class ShoppingCenterTenantServiceImpl extends EntityServiceImpl<ShoppingCenterTenant> implements ShoppingCenterTenantService {

	@Autowired
	private ShoppingCenterTenantRepository shoppingCenterTenantRepository;
	@Autowired
	private ShoppingCenterTenantValidator shoppingCenterTenantValidator;

	@Override
	public List<ShoppingCenterTenant> findAllBySurveyId(Integer shoppingCenterSurveyId) {
		return shoppingCenterTenantRepository.findAll(
				Specifications.where(ShoppingCenterTenantSpecifications.shoppingCenterSurveyIdEquals(shoppingCenterSurveyId)));
	}

	@Override
	public List<ShoppingCenterTenant> findAllBySurveyIdUsingSpecs(Integer id) {
		return shoppingCenterTenantRepository.findAll(
				Specifications.where(ShoppingCenterTenantSpecifications.shoppingCenterSurveyIdEquals(id))
						.and(AuditingEntitySpecifications.isNotDeleted())
		);
	}

	@Override
	public ShoppingCenterTenant updateEntityAttributes(ShoppingCenterTenant existing, ShoppingCenterTenant request) {
		existing.setName(request.getName());
		existing.setIsAnchor(request.getIsAnchor());
		existing.setOutparcel(request.getOutparcel());
		existing.setTenantSqft(request.getTenantSqft());

		return existing;
	}

	@Override
	public String getEntityName() {
		return "ShoppingCenterTenant";
	}

	@Override
	public ShoppingCenterTenantValidator getValidator() {
		return shoppingCenterTenantValidator;
	}

}

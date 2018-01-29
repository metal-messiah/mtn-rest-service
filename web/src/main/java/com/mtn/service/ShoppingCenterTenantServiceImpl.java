package com.mtn.service;

import com.mtn.model.domain.ShoppingCenterTenant;
import com.mtn.repository.ShoppingCenterTenantRepository;
import com.mtn.validators.ShoppingCenterTenantValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mtn.repository.specification.ShoppingCenterTenantSpecifications.*;
import static org.springframework.data.jpa.domain.Specifications.where;

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
	public List<ShoppingCenterTenant> findAllBySurveyId(Integer id) {
		return getEntityRepository().findAllBySurveyId(id);
	}

	@Override
	public List<ShoppingCenterTenant> findAllBySurveyIdUsingSpecs(Integer id) {
		return getEntityRepository().findAll(
				where(shoppingCenterSurveyIdEquals(id))
						.and(isNotDeleted())
		);
	}

	@Override
	public Page<ShoppingCenterTenant> findAllUsingSpecs(Pageable page) {
		return getEntityRepository().findAll(
				where(isNotDeleted()), page
		);
	}

	@Override
	public ShoppingCenterTenant findOneUsingSpecs(Integer id) {
		return getEntityRepository().findOne(
				where(idEquals(id))
						.and(isNotDeleted())
		);
	}

	@Override
	public ShoppingCenterTenant getUpdatedEntity(ShoppingCenterTenant existing, ShoppingCenterTenant request) {
		existing.setName(request.getName());
		existing.setIsAnchor(request.getIsAnchor());
		existing.setIsOutparcel(request.getIsOutparcel());
		existing.setSqft(request.getSqft());

		return existing;
	}

	@Override
	public String getEntityName() {
		return "ShoppingCenterTenant";
	}

	@Override
	public void handleAssociationsOnDeletion(ShoppingCenterTenant existing) {
		// TODO - handle associations
	}

	@Override
	public void handleAssociationsOnCreation(ShoppingCenterTenant request) {
		// TODO - handle associations
	}

	@Override
	public ShoppingCenterTenantRepository getEntityRepository() {
		return shoppingCenterTenantRepository;
	}

	@Override
	public ShoppingCenterTenantValidator getValidator() {
		return shoppingCenterTenantValidator;
	}

}

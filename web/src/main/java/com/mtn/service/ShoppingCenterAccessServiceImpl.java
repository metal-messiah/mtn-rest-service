package com.mtn.service;

import com.mtn.model.domain.ShoppingCenterAccess;
import com.mtn.repository.ShoppingCenterAccessRepository;
import com.mtn.validators.ShoppingCenterAccessValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mtn.repository.specification.ShoppingCenterAccessSpecifications.*;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Created by Allen on 5/6/2017.
 */
@Service
public class ShoppingCenterAccessServiceImpl extends EntityServiceImpl<ShoppingCenterAccess> implements ShoppingCenterAccessService {

	@Autowired
	private ShoppingCenterAccessRepository accessRepository;
	@Autowired
	private ShoppingCenterAccessValidator shoppingCenterAccessValidator;

	@Override
	public List<ShoppingCenterAccess> findAllBySurveyIdUsingSpecs(Integer id) {
		return getEntityRepository().findAll(
				where(shoppingCenterSurveyIdEquals(id))
						.and(isNotDeleted())
		);
	}

	@Override
	public Page<ShoppingCenterAccess> findAllUsingSpecs(Pageable page) {
		return getEntityRepository().findAll(
				where(isNotDeleted()),
				page
		);
	}

	@Override
	public ShoppingCenterAccess findOneUsingSpecs(Integer id) {
		return getEntityRepository().findOne(
				where(idEquals(id))
						.and(isNotDeleted())
		);
	}

	@Override
	public ShoppingCenterAccess getUpdatedEntity(ShoppingCenterAccess existing, ShoppingCenterAccess request) {
		existing.setHasLeftIn(request.getHasLeftIn());
		existing.setHasLeftOut(request.getHasLeftOut());
		existing.setHasTrafficLight(request.getHasTrafficLight());
		existing.setHasOneWayRoad(request.getHasOneWayRoad());
		existing.setHasRightIn(request.getHasRightIn());
		existing.setHasRightOut(request.getHasRightOut());

		return existing;
	}

	@Override
	public String getEntityName() {
		return "ShoppingCenterAccess";
	}

	@Override
	public void handleAssociationsOnDeletion(ShoppingCenterAccess existing) {
		// No children to handle
	}

	@Override
	public void handleAssociationsOnCreation(ShoppingCenterAccess request) {
		// No children to handle
	}

	@Override
	public ShoppingCenterAccessRepository getEntityRepository() {
		return accessRepository;
	}

	@Override
	public ShoppingCenterAccessValidator getValidator() {
		return shoppingCenterAccessValidator;
	}
}

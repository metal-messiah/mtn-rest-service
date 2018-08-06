package com.mtn.service;

import com.mtn.model.domain.ShoppingCenterAccess;
import com.mtn.repository.ShoppingCenterAccessRepository;
import com.mtn.repository.specification.AuditingEntitySpecifications;
import com.mtn.repository.specification.ShoppingCenterAccessSpecifications;
import com.mtn.validators.ShoppingCenterAccessValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import java.util.List;

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
		return accessRepository.findAll(
				Specifications.where(ShoppingCenterAccessSpecifications.shoppingCenterSurveyIdEquals(id))
						.and(AuditingEntitySpecifications.isNotDeleted())
		);
	}

	@Override
	public ShoppingCenterAccess updateEntityAttributes(ShoppingCenterAccess existing, ShoppingCenterAccess request) {
		existing.setAccessType(request.getAccessType());
		existing.setHasLeftIn(request.getHasLeftIn());
		existing.setHasLeftOut(request.getHasLeftOut());
		existing.setHasTrafficLight(request.getHasTrafficLight());
		existing.setOneWayRoad(request.getOneWayRoad());
		existing.setHasRightIn(request.getHasRightIn());
		existing.setHasRightOut(request.getHasRightOut());

		return existing;
	}

	@Override
	public String getEntityName() {
		return "ShoppingCenterAccess";
	}

	@Override
	public ShoppingCenterAccessValidator getValidator() {
		return shoppingCenterAccessValidator;
	}
}

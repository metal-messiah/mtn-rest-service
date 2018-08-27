package com.mtn.service;

import com.mtn.model.domain.ShoppingCenterAccess;
import com.mtn.model.domain.ShoppingCenterSurvey;
import com.mtn.model.domain.UserProfile;
import com.mtn.model.view.ShoppingCenterAccessView;
import com.mtn.repository.ShoppingCenterAccessRepository;
import com.mtn.repository.specification.AuditingEntitySpecifications;
import com.mtn.repository.specification.ShoppingCenterAccessSpecifications;
import com.mtn.validators.ShoppingCenterAccessValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ShoppingCenterAccessService extends EntityService<ShoppingCenterAccess, ShoppingCenterAccessView> {

	@Autowired
	public ShoppingCenterAccessService(SecurityService securityService,
									   ShoppingCenterAccessRepository repository,
									   ShoppingCenterAccessValidator validator) {
		super(securityService, repository, validator, ShoppingCenterAccess::new);
	}

	public List<ShoppingCenterAccess> findAllBySurveyIdUsingSpecs(Integer id) {
		return this.repository.findAll(
				Specifications.where(ShoppingCenterAccessSpecifications.shoppingCenterSurveyIdEquals(id))
						.and(AuditingEntitySpecifications.isNotDeleted())
		);
	}

	@Transactional
	public ShoppingCenterAccess addOne(ShoppingCenterAccessView request, ShoppingCenterSurvey survey) {
		this.validator.validateForInsert(request);

		UserProfile currentUser = securityService.getCurrentUser();

		ShoppingCenterAccess access = createNewEntityFromRequest(request);
		access.setSurvey(survey);
		access.setCreatedBy(currentUser);
		access.setUpdatedBy(currentUser);

		return this.repository.save(access);
	}

	@Override
	protected void setEntityAttributesFromRequest(ShoppingCenterAccess access, ShoppingCenterAccessView request) {
		access.setAccessType(request.getAccessType());
		access.setHasLeftIn(request.getHasLeftIn());
		access.setOneWayRoad(request.getOneWayRoad());
		access.setHasLeftOut(request.getHasLeftOut());
		access.setHasTrafficLight(request.getHasTrafficLight());
		access.setHasRightIn(request.getHasRightIn());
		access.setHasRightOut(request.getHasRightOut());
	}

	@Override
	public void handleAssociationsOnDeletion(ShoppingCenterAccess existing) {
		// Do Nothing
	}
}

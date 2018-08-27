package com.mtn.validators;

import com.mtn.model.domain.Boundary;
import com.mtn.model.view.BoundaryView;
import com.mtn.repository.BoundaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoundaryValidator extends EntityValidator<Boundary, BoundaryView> {

	@Autowired
	public BoundaryValidator(BoundaryRepository boundaryRepository) {
		super(boundaryRepository);
	}

	protected void validateUpdateInsertBusinessRules(BoundaryView request) {
		if (request.getGeojson() == null) {
			throw new IllegalArgumentException("Boundary must have geojson");
		}
	}
}

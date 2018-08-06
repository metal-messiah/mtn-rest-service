package com.mtn.validators;

import com.mtn.model.domain.Boundary;
import com.mtn.service.BoundaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoundaryValidator extends ValidatingDataService<Boundary> {

	@Autowired
	private BoundaryService boundaryService;

	@Override
	public BoundaryService getEntityService() {
		return boundaryService;
	}

}

package com.mtn.validators;

import com.mtn.model.domain.StoreStatus;
import com.mtn.model.view.StoreStatusView;
import com.mtn.repository.StoreStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreStatusValidator extends EntityValidator<StoreStatus, StoreStatusView> {

	@Autowired
	public StoreStatusValidator(StoreStatusRepository repository) {
		super(repository);
	}

	@Override
	protected void validateUpdateInsertBusinessRules(StoreStatusView request) {
		if (request.getStatus() == null) {
			throw new IllegalArgumentException("StoreStatus status must be provided");
		} else if (request.getStatusStartDate() == null) {
			throw new IllegalArgumentException("StoreStatus statusStartDate must be provided");
		}
	}

}

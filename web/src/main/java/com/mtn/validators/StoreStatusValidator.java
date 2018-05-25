package com.mtn.validators;

import com.mtn.model.domain.StoreStatus;
import com.mtn.service.StoreStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreStatusValidator extends ValidatingDataService<StoreStatus> {

	@Autowired
	private StoreStatusService storeStatusService;

	@Override
	public StoreStatusService getEntityService() {
		return storeStatusService;
	}

	@Override
	public void validateForInsert(StoreStatus object) {
		super.validateForInsert(object);

		if (object.getStore() == null) {
			throw new IllegalStateException("StoreStatus was not mapped to Store before saving!");
		}
	}

	@Override
	public void validateBusinessRules(StoreStatus object) {
		if (object.getStatus() == null) {
			throw new IllegalArgumentException("StoreStatus status must be provided");
		} else if (object.getStatusStartDate() == null) {
			throw new IllegalArgumentException("StoreStatus statusStartDate must be provided");
		}
	}

}

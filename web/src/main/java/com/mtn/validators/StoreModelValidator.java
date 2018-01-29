package com.mtn.validators;

import com.mtn.model.domain.StoreModel;
import com.mtn.service.StoreModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreModelValidator extends ValidatingDataService<StoreModel> {

	@Autowired
	private StoreModelService storeModelService;

	@Override
	public StoreModelService getEntityService() {
		return storeModelService;
	}

	@Override
	public void validateForInsert(StoreModel object) {
		super.validateForInsert(object);

		if (object.getStore() == null) {
			throw new IllegalStateException("StoreModel was not mapped to Store before saving!");
		}
	}

	@Override
	public void validateBusinessRules(StoreModel object) {
		if (object.getModelType() == null) {
			throw new IllegalArgumentException("StoreModel modelType must be provided");
		} else if (object.getModelDate() == null) {
			throw new IllegalArgumentException("StoreModel modelDate must be provided");
		} else if (object.getProject() == null) {
			throw new IllegalArgumentException("StoreModel project must be provided");
		} else if (object.getStore() == null) {
			throw new IllegalArgumentException("StoreModel store must be provided");
		}
	}


}

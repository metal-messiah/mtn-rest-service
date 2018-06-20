package com.mtn.validators;

import com.mtn.constant.StoreType;
import com.mtn.model.domain.Store;
import com.mtn.service.StoreService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreValidator extends ValidatingDataService<Store> {

	@Autowired
	private StoreService storeService;

	@Override
	public StoreService getEntityService() {
		return storeService;
	}

	@Override
	public void validateBusinessRules(Store object) {
		if (object.getStoreType() == null) {
			throw new IllegalArgumentException(String.format("Store type must be one of: %s", StringUtils.join(StoreType.values(), ", ")));
		}
	}
}

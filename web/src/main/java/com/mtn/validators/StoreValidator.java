package com.mtn.validators;

import com.mtn.constant.StoreType;
import com.mtn.model.domain.Store;
import com.mtn.model.view.StoreView;
import com.mtn.repository.StoreRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreValidator extends EntityValidator<Store, StoreView> {

	@Autowired
	public StoreValidator(StoreRepository repository) {
		super(repository);
	}

	@Override
	protected void validateUpdateInsertBusinessRules(StoreView request) {
		if (request.getStoreType() == null) {
			throw new IllegalArgumentException(String.format("Store type must be one of: %s", StringUtils.join(StoreType.values(), ", ")));
		}
	}
}

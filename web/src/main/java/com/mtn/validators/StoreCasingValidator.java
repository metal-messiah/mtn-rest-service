package com.mtn.validators;

import com.mtn.model.domain.Site;
import com.mtn.model.domain.StoreCasing;
import com.mtn.service.StoreCasingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreCasingValidator extends ValidatingDataService<StoreCasing> {

	@Autowired
	private StoreCasingService storeCasingService;

	@Override
	public StoreCasingService getEntityService() {
		return storeCasingService;
	}

	@Override
	public void validateForInsert(StoreCasing object) {
		super.validateForInsert(object);

		if (object.getStore() == null) {
			throw new IllegalStateException("StoreCasing was not mapped to Store before saving!");
		}
	}

}

package com.mtn.validators;

import com.mtn.model.domain.StoreSource;
import com.mtn.model.domain.StoreVolume;
import com.mtn.service.StoreSourceService;
import com.mtn.service.StoreVolumeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreSourceValidator extends ValidatingDataService<StoreSource> {

	@Autowired
	private StoreSourceService storeSourceService;

	@Override
	public StoreSourceService getEntityService() {
		return storeSourceService;
	}

	@Override
	public void validateForInsert(StoreSource object) {
		super.validateForInsert(object);

		if (object.getSourceName() == null) {
			throw new IllegalStateException("StoreSource must have a Source Name (ex. Planned Grocery)!");
		}
		if (object.getSourceNativeId() == null) {
			throw new IllegalStateException("StoreSource must have a Source Native Id!");
		}
	}
}

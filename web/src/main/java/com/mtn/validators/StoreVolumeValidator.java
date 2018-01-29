package com.mtn.validators;

import com.mtn.model.domain.StoreVolume;
import com.mtn.service.StoreVolumeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreVolumeValidator extends ValidatingDataService<StoreVolume> {

	@Autowired
	private StoreVolumeService storeVolumeService;

	@Override
	public StoreVolumeService getEntityService() {
		return storeVolumeService;
	}

	@Override
	public void validateForInsert(StoreVolume object) {
		super.validateForInsert(object);

		if (object.getStore() == null) {
			throw new IllegalStateException("StoreVolume was not mapped to Store before saving!");
		}
	}

	@Override
	public void validateBusinessRules(StoreVolume object) {
		if (object.getVolumeTotal() == null) {
			throw new IllegalArgumentException("StoreVolume volumeTotal must be provided");
		} else if (object.getVolumeDate() == null) {
			throw new IllegalArgumentException("StoreVolume volumeDate must be provided");
		} else if (object.getVolumeType() == null) {
			throw new IllegalArgumentException("StoreVolume volumeType must be provided");
		} else if (StringUtils.isBlank(object.getSource())) {
			throw new IllegalArgumentException("StoreVolume source must be provided");
		}
	}

}

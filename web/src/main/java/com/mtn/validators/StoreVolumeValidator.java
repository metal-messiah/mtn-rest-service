package com.mtn.validators;

import com.mtn.model.domain.StoreVolume;
import com.mtn.model.view.StoreVolumeView;
import com.mtn.repository.StoreVolumeRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreVolumeValidator extends EntityValidator<StoreVolume, StoreVolumeView> {

	@Autowired
	public StoreVolumeValidator(StoreVolumeRepository repository) {
		super(repository);
	}

	@Override
	protected void validateUpdateInsertBusinessRules(StoreVolumeView request) {
		if (request.getVolumeTotal() == null) {
			throw new IllegalArgumentException("StoreVolume volumeTotal must be provided");
		} else if (request.getVolumeDate() == null) {
			throw new IllegalArgumentException("StoreVolume volumeDate must be provided");
		} else if (request.getVolumeType() == null) {
			throw new IllegalArgumentException("StoreVolume volumeType must be provided");
		} else if (StringUtils.isBlank(request.getSource())) {
			throw new IllegalArgumentException("StoreVolume source must be provided");
		}
	}

}

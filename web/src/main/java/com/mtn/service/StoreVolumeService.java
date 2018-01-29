package com.mtn.service;

import com.mtn.model.domain.StoreVolume;

import java.util.List;

public interface StoreVolumeService extends EntityService<StoreVolume> {
	List<StoreVolume> findAllByStoreId(Integer storeId);

	List<StoreVolume> findAllByStoreIdUsingSpecs(Integer storeId);
}

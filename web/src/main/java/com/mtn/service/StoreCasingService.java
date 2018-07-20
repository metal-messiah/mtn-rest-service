package com.mtn.service;

import com.mtn.model.domain.StoreCasing;
import com.mtn.model.domain.StoreVolume;

import java.util.List;

public interface StoreCasingService extends EntityService<StoreCasing> {
	List<StoreCasing> findAllByProjectId(Integer id);

	List<StoreCasing> findAllByStoreId(Integer storeId);

	List<StoreCasing> findAllByStoreIdUsingSpecs(Integer storeId);

	StoreCasing setStoreVolume(Integer storeCasingId, Integer storeVolumeId);

	StoreCasing removeStoreVolume(Integer storeCasingId);

	StoreCasing addProject(Integer storeCasingId, Integer projectId);

	StoreCasing removeProject(Integer storeCasingId, Integer projeectId);

	StoreCasing createStoreVolume(Integer storeCasingId, StoreVolume volumeRequest);
}

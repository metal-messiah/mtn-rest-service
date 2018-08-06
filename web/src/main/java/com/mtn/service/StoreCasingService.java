package com.mtn.service;

import com.mtn.model.domain.StoreCasing;
import com.mtn.model.domain.StoreVolume;

import java.util.List;

public interface StoreCasingService extends EntityService<StoreCasing>, StoreChildService<StoreCasing> {
	List<StoreCasing> findAllByProjectId(Integer id);

	StoreCasing setStoreVolume(Integer storeCasingId, Integer storeVolumeId);

	StoreCasing removeStoreVolume(Integer storeCasingId);

	StoreCasing addProject(Integer storeCasingId, Integer projectId);

	StoreCasing removeProject(Integer storeCasingId, Integer projeectId);

	StoreCasing createStoreVolume(Integer storeCasingId, StoreVolume volumeRequest);
}

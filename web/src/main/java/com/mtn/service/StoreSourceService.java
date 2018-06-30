package com.mtn.service;

import com.mtn.model.domain.StoreSource;
import com.mtn.model.domain.UserProfile;

import java.time.LocalDateTime;
import java.util.List;

public interface StoreSourceService extends EntityService<StoreSource> {
	List<StoreSource> findAllByStoreId(Integer storeId);

	List<StoreSource> findAllByStoreIdUsingSpecs(Integer storeId);

	LocalDateTime getMaxSourceEditedDate();

	void addAndUpdateSourcesFromPlannedGrocery(UserProfile validator);

	StoreSource findOneBySourceNativeIdUsingSpecs(String sourceName, String id);

}

package com.mtn.service;

import com.mtn.model.domain.StoreStatus;

import java.util.List;

public interface StoreStatusService extends EntityService<StoreStatus> {
	List<StoreStatus> findAllByStoreId(Integer storeId);

	List<StoreStatus> findAllByStoreIdUsingSpecs(Integer storeId);
}

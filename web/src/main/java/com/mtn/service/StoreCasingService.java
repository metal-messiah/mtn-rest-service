package com.mtn.service;

import com.mtn.model.domain.StoreCasing;

import java.util.List;

public interface StoreCasingService extends EntityService<StoreCasing> {
	List<StoreCasing> findAllByProjectId(Integer id);

	List<StoreCasing> findAllByStoreId(Integer storeId);

	List<StoreCasing> findAllByStoreIdUsingSpecs(Integer storeId);
}

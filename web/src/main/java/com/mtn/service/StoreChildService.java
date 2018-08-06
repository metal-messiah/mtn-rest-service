package com.mtn.service;

import com.mtn.model.domain.AuditingEntity;

import java.util.List;

public interface StoreChildService<T extends AuditingEntity> {
	List<T> findAllByStoreId(Integer storeId);

	List<T> findAllByStoreIdUsingSpecs(Integer storeId);
}

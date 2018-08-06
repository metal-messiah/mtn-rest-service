package com.mtn.service;

import com.mtn.model.domain.StoreModel;

import java.util.List;

public interface StoreModelService extends EntityService<StoreModel>, StoreChildService<StoreModel> {
	List<StoreModel> findAllByProjectId(Integer projectId);

	List<StoreModel> findAllByProjectIdUsingSpecs(Integer projectId);
}

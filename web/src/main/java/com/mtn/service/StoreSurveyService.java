package com.mtn.service;

import com.mtn.model.domain.StoreSurvey;

import java.util.List;

public interface StoreSurveyService extends EntityService<StoreSurvey> {
	List<StoreSurvey> findAllByStoreId(Integer storeId);

	List<StoreSurvey> findAllByStoreIdUsingSpecs(Integer storeId);
}

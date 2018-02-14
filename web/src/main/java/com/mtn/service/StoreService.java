package com.mtn.service;

import com.mtn.model.domain.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StoreService extends EntityService<Store> {
	StoreCasing addOneCasingToStore(Integer storeId, StoreCasing request);

	StoreModel addOneModelToStore(Integer storeId, StoreModel request);

	StoreSurvey addOneSurveyToStore(Integer storeId, StoreSurvey request);

	StoreVolume addOneVolumeToStore(Integer storeId, StoreVolume volume);

	List<Store> findAllByProjectId(Integer id);

	List<Store> findAllByBannerId(Integer companyId);

	List<Store> findAllByParentCompanyIdRecursive(Integer companyId);

	List<Store> findAllBySiteIdUsingSpecs(Integer siteId);

	Store updateOneBanner(Integer storeId, Integer bannerId);
}

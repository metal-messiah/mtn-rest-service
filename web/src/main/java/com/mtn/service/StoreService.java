package com.mtn.service;

import com.mtn.constant.StoreType;
import com.mtn.model.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StoreService extends EntityService<Store> {

	Page<Store> findAllOfTypesInBounds(Float north, Float south, Float east, Float west, List<StoreType> storeTypes, Pageable page);

	StoreCasing addOneCasingToStore(Integer storeId, StoreCasing request, boolean storeRemodeled, boolean shoppingCenterRedeveloped);

	StoreModel addOneModelToStore(Integer storeId, StoreModel request);

	StoreSurvey addOneSurveyToStore(Integer storeId, StoreSurvey request);

	StoreVolume addOneVolumeToStore(Integer storeId, StoreVolume volume);

	List<Store> findAllByProjectId(Integer id);

	List<Store> findAllByBannerId(Integer companyId);

	List<Store> findAllByParentCompanyIdRecursive(Integer companyId);

	List<Store> findAllBySiteIdUsingSpecs(Integer siteId);

	Store updateOneBanner(Integer storeId, Integer bannerId);

	Store setCurrentStoreStatus(Integer storeId, Integer statusId);

	StoreStatus addOneStatusToStore(Integer storeId, StoreStatus storeStatus);

	void deleteStoreStatus(Integer storeId, Integer statusId);

	void deleteStoreVolume(Integer storeId, Integer volumeId);
}

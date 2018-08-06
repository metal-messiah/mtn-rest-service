package com.mtn.service;

import com.mtn.constant.StoreType;
import com.mtn.model.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public interface StoreService extends EntityService<Store> {

	Page<Store> findAllOfTypesInBounds(Float north, Float south, Float east, Float west, List<StoreType> storeTypes, Pageable page);

	Page<Store> findAllAssignedTo(Integer assigneeId, List<StoreType> storeTypes, Pageable page);

	StoreCasing addOneCasingToStore(Integer storeId, StoreCasing request);

	StoreModel addOneModelToStore(Integer storeId, StoreModel request);

	StoreSurvey addOneSurveyToStore(Integer storeId, StoreSurvey request);

	StoreVolume addOneVolumeToStore(Integer storeId, StoreVolume volume);

	List<Store> findAllByProjectId(Integer id);

	List<Store> findAllByBannerId(Integer companyId);

	List<Store> findAllBySiteIdUsingSpecs(Integer siteId);

	Store updateOneBanner(Integer storeId, Integer bannerId);

	StoreStatus addOneStatusToStore(Integer storeId, StoreStatus storeStatus);

	void deleteStoreStatus(Integer storeId, Integer statusId);

	void deleteStoreVolume(Integer storeId, Integer volumeId);

}

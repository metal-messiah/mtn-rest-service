package com.mtn.service;

import com.mtn.model.domain.StoreSource;
import com.mtn.model.domain.UserProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface StoreSourceService extends EntityService<StoreSource> {
	List<StoreSource> findAllByStoreId(Integer storeId);

	List<StoreSource> findAllByStoreIdUsingSpecs(Integer storeId);

	LocalDateTime getMaxSourceEditedDate();

	StoreSource findOneBySourceNativeIdUsingSpecs(String sourceName, String id);

	Page<StoreSource> findAllByQuery(Map<String, String> queryMap, Pageable page);

}

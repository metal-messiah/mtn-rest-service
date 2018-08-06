package com.mtn.service;

import com.mtn.model.domain.StoreSource;
import com.mtn.model.domain.StoreStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface StoreSourceService extends EntityService<StoreSource>, StoreChildService<StoreSource> {

	LocalDateTime getMaxSourceEditedDate(String sourceName);

	Optional<StoreSource> findOneBySourceNativeIdUsingSpecs(String sourceName, String id);

	Page<StoreSource> findAllByQuery(Map<String, String> queryMap, Pageable page);

}

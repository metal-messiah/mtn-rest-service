package com.mtn.service;

import com.mtn.model.domain.*;
import com.mtn.repository.StoreSourceRepository;
import com.mtn.validators.StoreSourceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

import static com.mtn.repository.specification.StoreSourceSpecifications.*;
import static java.lang.Boolean.parseBoolean;
import static org.springframework.data.jpa.domain.Specifications.where;

@Service
public class StoreSourceServiceImpl extends EntityServiceImpl<StoreSource> implements StoreSourceService {

	@Autowired
	private StoreSourceRepository storeSourceRepository;
	@Autowired
	private StoreSourceValidator storeSourceValidator;


	@Value("${planned-grocery.client_id}")
	private String pgClientId;

	@Value("${planned-grocery.client_secret}")
	private String pgClientSecret;


	@Override
	public List<StoreSource> findAllByStoreId(Integer storeId) {
		return getEntityRepository().findAll(where(storeIdEquals(storeId)));
	}

	@Override
	public List<StoreSource> findAllByStoreIdUsingSpecs(Integer storeId) {
		return getEntityRepository().findAll(
				where(storeIdEquals(storeId))
						.and(isNotDeleted())
		);
	}

	@Override
	public LocalDateTime getMaxSourceEditedDate() {
		return getEntityRepository().getMaxSourceEditedDate();
	}

	@Override
	public Page<StoreSource> findAllUsingSpecs(Pageable page) {
		return getEntityRepository().findAll(where(isNotDeleted()), page);
	}

	@Override
	public StoreSource findOneUsingSpecs(Integer id) {
		return getEntityRepository().findOne(
				where(idEquals(id))
						.and(isNotDeleted())
		);
	}

	@Override
	public StoreSource findOneBySourceNativeIdUsingSpecs(String sourceName, String id) {
		return getEntityRepository().findOne(
				where(sourceNativeIdEquals(id))
						.and(sourceNameEquals(sourceName))
						.and(isNotDeleted())
		);
	}

	@Override
	public Page<StoreSource> findAllByQuery(Map<String, String> queryMap, Pageable page) {
		Specifications<StoreSource> specs = where(isNotDeleted());
		if (queryMap.containsKey("source-name")) {
			specs = specs.and(sourceNameEquals(queryMap.get("source-name")));
		}
		if (queryMap.containsKey("validated")) {
			boolean validated = parseBoolean(queryMap.get("validated"));
			if (validated) {
				specs = specs.and(isValidated());
			} else {
				specs = specs.and(isNotValidated());
			}
		}

		return getEntityRepository().findAll(specs, page);
	}

	@Override
	public StoreSource updateEntityAttributes(StoreSource existing, StoreSource request) {
		// TODO

		return existing;
	}

	@Override
	public String getEntityName() {
		return "StoreSource";
	}

	@Override
	public void handleAssociationsOnDeletion(StoreSource existing) {
		// TODO - Handle Store
	}

	@Override
	public void handleAssociationsOnCreation(StoreSource request) {
		// TODO - Handle Store
	}

	@Override
	public StoreSourceRepository getEntityRepository() {
		return storeSourceRepository;
	}

	@Override
	public StoreSourceValidator getValidator() {
		return storeSourceValidator;
	}

}

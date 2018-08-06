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
public class StoreSourceServiceImpl extends StoreChildServiceImpl<StoreSource> implements StoreSourceService {

	@Autowired
	private StoreSourceRepository storeSourceRepository;
	@Autowired
	private StoreSourceValidator storeSourceValidator;

	@Value("${planned-grocery.client_id}")
	private String pgClientId;

	@Value("${planned-grocery.client_secret}")
	private String pgClientSecret;

	@Override
	public LocalDateTime getMaxSourceEditedDate(String sourceName) {
		return storeSourceRepository.getMaxSourceEditedDate(sourceName);
	}

	@Override
	public Optional<StoreSource> findOneBySourceNativeIdUsingSpecs(String sourceName, String id) {
		return Optional.ofNullable(storeSourceRepository.findOne(
				where(sourceNativeIdEquals(id))
						.and(sourceNameEquals(sourceName))
						.and(isNotDeleted())
		));
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

		return storeSourceRepository.findAll(specs, page);
	}

	@Override
	public StoreSource updateEntityAttributes(StoreSource existing, StoreSource request) {
		existing.setSourceCreatedDate(request.getSourceCreatedDate());
		existing.setSourceEditedDate(request.getSourceEditedDate());
		existing.setSourceName(request.getSourceName());
		existing.setSourceNativeId(request.getSourceNativeId());
		existing.setSourceStoreName(request.getSourceStoreName());
		existing.setSourceUrl(request.getSourceUrl());
		existing.setValidatedBy(request.getValidatedBy());
		existing.setValidatedDate(request.getValidatedDate());

		return existing;
	}

	@Override
	public String getEntityName() {
		return "StoreSource";
	}

	@Override
	public StoreSourceValidator getValidator() {
		return storeSourceValidator;
	}

}

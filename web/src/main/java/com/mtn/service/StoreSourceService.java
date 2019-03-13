package com.mtn.service;

import com.mtn.model.domain.*;
import com.mtn.model.view.StoreSourceView;
import com.mtn.repository.StoreSourceRepository;
import com.mtn.repository.specification.StoreSourceSpecifications;
import com.mtn.validators.StoreSourceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

import static java.lang.Boolean.parseBoolean;
import static org.springframework.data.jpa.domain.Specifications.not;
import static org.springframework.data.jpa.domain.Specifications.where;

@Service
public class StoreSourceService extends StoreChildService<StoreSource, StoreSourceView> {

	private final StoreService storeService;
	private final BannerSourceService bannerSourceService;
	private final UserProfileService userProfileService;

	@Autowired
	public StoreSourceService(SecurityService securityService,
							  StoreSourceRepository repository,
							  StoreSourceValidator validator,
							  StoreService storeService,
							  BannerSourceService bannerSourceService,
							  UserProfileService userProfileService) {
		super(securityService, repository, validator, StoreSource::new);
		this.storeService = storeService;
		this.bannerSourceService = bannerSourceService;
		this.userProfileService = userProfileService;
	}

	public LocalDateTime getMaxSourceEditedDate(String sourceName) {
		return ((StoreSourceRepository) this.repository).getMaxSourceEditedDate(sourceName);
	}

	public Optional<StoreSource> findOneBySourceNativeIdUsingSpecs(String sourceName, String id) {
		return Optional.ofNullable(this.repository.findOne(
				where(StoreSourceSpecifications.sourceNativeIdEquals(id))
						.and(StoreSourceSpecifications.sourceNameEquals(sourceName))
						.and(StoreSourceSpecifications.isNotDeleted())
		));
	}

	public List<StoreSource> findAllOfBannerSourceWhereNativeIdNotInSet(Integer bannerSourceId, Set<String> hashIds) {
		return ((StoreSourceRepository) this.repository).findAllOfBannerSourceWhereNativeIdNotInSet(bannerSourceId, hashIds);
	}

	public Page<StoreSource> findAllByQuery(Map<String, String> queryMap, Pageable page) {
		Specifications<StoreSource> specs = where(StoreSourceSpecifications.isNotDeleted());
		if (queryMap.containsKey("source-name")) {
			specs = specs.and(StoreSourceSpecifications.sourceNameEquals(queryMap.get("source-name")));
		}
		if (queryMap.containsKey("banner-source-id")) {
			Integer bannerSourceId = Integer.parseInt(queryMap.get("banner-source-id"));
			specs = specs.and(StoreSourceSpecifications.bannerSourceIdEquals(bannerSourceId));
		}
		if (queryMap.containsKey("validated")) {
			boolean validated = parseBoolean(queryMap.get("validated"));
			if (validated) {
				specs = specs.and(StoreSourceSpecifications.isValidated());
			} else {
				specs = specs.and(not(StoreSourceSpecifications.isValidated()));
			}
		}

		return this.repository.findAll(specs, page);
	}

	@Override
	protected void setEntityAttributesFromRequest(StoreSource source, StoreSourceView request) {
		source.setSourceName(request.getSourceName());
		source.setSourceNativeId(request.getSourceNativeId());
		source.setSourceUrl(request.getSourceUrl());
		source.setSourceStoreName(request.getSourceStoreName());
		source.setSourceCreatedDate(request.getSourceCreatedDate());
		source.setSourceEditedDate(request.getSourceEditedDate());
		source.setSourceDeletedDate(request.getSourceDeletedDate());

		if (request.getValidatedBy() != null) {
			UserProfile validator = userProfileService.findOneUsingSpecs(request.getValidatedBy().getId());
			source.setValidatedBy(validator);
			source.setValidatedDate(request.getValidatedDate());
		} else {
			source.setValidatedBy(null);
			source.setValidatedDate(null);
		}

		if (request.getStore() != null) {
			Store store = storeService.findOneUsingSpecs(request.getStore().getId());
			source.setStore(store);
		} else {
			source.setStore(null);
		}

		if (request.getBannerSource() != null) {
			BannerSource bannerSource = bannerSourceService.findOneUsingSpecs(request.getBannerSource().getId());
			source.setBannerSource(bannerSource);
		} else {
			source.setBannerSource(null);
		}

	}

	@Override
	public void handleAssociationsOnDeletion(StoreSource existing) {
		// Do nothing
	}
}

package com.mtn.service;

import com.mtn.model.domain.BannerSource;
import com.mtn.model.domain.Store;
import com.mtn.model.domain.StoreSource;
import com.mtn.model.domain.UserProfile;
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
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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
	}

	public StoreSource setStore(Integer sourceId, Store store, Boolean validate) {
		StoreSource storeSource = this.findOne(sourceId);
		storeSource.setStore(store);

		if (validate) {
			storeSource.setValidatedDate(LocalDateTime.now());
			storeSource.setValidatedBy(this.securityService.getCurrentUser());
		}
		return this.updateOne(storeSource);
	}

	public StoreSource removeStore(Integer sourceId) {
		StoreSource storeSource = this.findOne(sourceId);
		storeSource.setStore(null);

		storeSource.setValidatedDate(null);
		storeSource.setValidatedBy(null);

		return this.updateOne(storeSource);
	}

	public StoreSource setBannerSource(Integer sourceId, BannerSource bannerSource) {
		StoreSource storeSource = this.findOne(sourceId);
		storeSource.setBannerSource(bannerSource);
		return this.updateOne(storeSource);
	}

	public StoreSource validate(Integer sourceId) {
		StoreSource storeSource = this.findOne(sourceId);
		storeSource.setValidatedBy(this.securityService.getCurrentUser());
		storeSource.setValidatedDate(LocalDateTime.now());
		return this.updateOne(storeSource);
	}

	public StoreSource invalidate(Integer sourceId) {
		StoreSource storeSource = this.findOne(sourceId);
		storeSource.setValidatedBy(null);
		storeSource.setValidatedDate(null);
		return this.updateOne(storeSource);
	}

	@Override
	public void handleAssociationsOnDeletion(StoreSource existing) {
		// Do nothing
	}
}

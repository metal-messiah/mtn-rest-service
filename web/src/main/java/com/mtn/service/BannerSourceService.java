package com.mtn.service;

import com.mtn.model.domain.*;
import com.mtn.model.view.BannerSourceView;
import com.mtn.repository.BannerSourceRepository;
import com.mtn.repository.specification.BannerSourceSpecifications;
import com.mtn.validators.BannerSourceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import static java.lang.Boolean.parseBoolean;
import static org.springframework.data.jpa.domain.Specifications.where;

@Service
public class BannerSourceService extends EntityService<BannerSource, BannerSourceView> {

	private final BannerService bannerService;
	private final UserProfileService userProfileService;

	@Autowired
	public BannerSourceService(SecurityService securityService,
							   BannerSourceRepository repository,
							   BannerSourceValidator validator,
							   BannerService bannerService,
							   UserProfileService userProfileService) {
		super(securityService, repository, validator, BannerSource::new);
		this.bannerService = bannerService;
		this.userProfileService = userProfileService;
	}

	public LocalDateTime getMaxSourceEditedDate(String sourceName) {
		return ((BannerSourceRepository) this.repository).getMaxSourceEditedDate(sourceName);
	}

	public Optional<BannerSource> findOneBySourceNativeIdUsingSpecs(String sourceName, String id) {
		return Optional.ofNullable(this.repository.findOne(
				where(BannerSourceSpecifications.sourceNativeIdEquals(id))
						.and(BannerSourceSpecifications.sourceNameEquals(sourceName))
						.and(BannerSourceSpecifications.isNotDeleted())
		));
	}

	public Page<BannerSource> findAllByQuery(Map<String, String> queryMap, Pageable page) {
		Specifications<BannerSource> specs = where(BannerSourceSpecifications.isNotDeleted());
		if (queryMap.containsKey("source-name")) {
			specs = specs.and(BannerSourceSpecifications.sourceNameEquals(queryMap.get("source-name")));
		}
		if (queryMap.containsKey("validated")) {
			boolean validated = parseBoolean(queryMap.get("validated"));
			if (validated) {
				specs = specs.and(BannerSourceSpecifications.isValidated());
			} else {
				specs = specs.and(BannerSourceSpecifications.isNotValidated());
			}
		}

		return this.repository.findAll(specs, page);
	}

	@Override
	protected void setEntityAttributesFromRequest(BannerSource source, BannerSourceView request) {
		source.setSourceName(request.getSourceName());
		source.setSourceNativeId(request.getSourceNativeId());
		source.setSourceUrl(request.getSourceUrl());
		source.setSourceBannerName(request.getSourceBannerName());
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

		if (request.getBanner() != null) {
			Banner banner= bannerService.findOneUsingSpecs(request.getBanner().getId());
			source.setBanner(banner);
		} else {
			source.setBanner(null);
		}

	}

	@Override
	public void handleAssociationsOnDeletion(BannerSource existing) {
		// Do nothing
	}
}

package com.mtn.service;

import com.mtn.model.domain.Banner;
import com.mtn.model.domain.BannerSource;
import com.mtn.model.domain.BannerSourceSummary;
import com.mtn.model.domain.UserProfile;
import com.mtn.model.view.BannerSourceView;
import com.mtn.repository.BannerSourceRepository;
import com.mtn.repository.BannerSourceSummaryRepository;
import com.mtn.repository.specification.BannerSourceSpecifications;
import com.mtn.repository.specification.BannerSourceSummarySpecifications;
import com.mtn.validators.BannerSourceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import static java.lang.Boolean.parseBoolean;
import static org.springframework.data.jpa.domain.Specifications.not;
import static org.springframework.data.jpa.domain.Specifications.where;

@Service
public class BannerSourceService extends EntityService<BannerSource, BannerSourceView> {

	private final BannerService bannerService;
	private final UserProfileService userProfileService;
	private final BannerSourceSummaryRepository bannerSourceSummaryRepository;

	@Autowired
	public BannerSourceService(SecurityService securityService,
							   BannerSourceRepository repository,
							   BannerSourceValidator validator,
							   BannerService bannerService,
							   BannerSourceSummaryRepository bannerSourceSummaryRepository,
							   UserProfileService userProfileService) {
		super(securityService, repository, validator, BannerSource::new);
		this.bannerService = bannerService;
		this.bannerSourceSummaryRepository = bannerSourceSummaryRepository;
		this.userProfileService = userProfileService;
	}

	public Optional<BannerSource> findOneBySourceNativeIdUsingSpecs(String sourceName, String id) {
		return Optional.ofNullable(this.repository.findOne(
				where(BannerSourceSpecifications.sourceNativeIdEquals(id))
						.and(BannerSourceSpecifications.sourceNameEquals(sourceName))
						.and(BannerSourceSpecifications.isNotDeleted())
		));
	}

	public Page<BannerSourceSummary> findAllSummariesByQuery(Map<String, String> queryMap, Pageable page) {
		Specifications<BannerSourceSummary> specs = where(not(BannerSourceSummarySpecifications.isDeleted()));
		if (queryMap.containsKey("source-name")) {
			specs = specs.and(BannerSourceSummarySpecifications.sourceNameEquals(queryMap.get("source-name")));
		}
		if (queryMap.containsKey("validated")) {
			boolean validated = parseBoolean(queryMap.get("validated"));
			if (validated) {
				specs = specs.and(BannerSourceSummarySpecifications.isValidated());
			} else {
				specs = specs.and(BannerSourceSummarySpecifications.isNotValidated());
			}
		}

		return this.bannerSourceSummaryRepository.findAll(specs, page);
	}

	public BannerSource assignBanner(Integer bannerSourceId, Integer bannerId) {
		BannerSource bannerSource = this.findOne(bannerSourceId);

		Banner banner = this.bannerService.findOne(bannerId);

		bannerSource.setBanner(banner);

		UserProfile validator = this.securityService.getCurrentUser();
		bannerSource.setValidatedBy(validator);
		bannerSource.setValidatedDate(LocalDateTime.now());

		return this.updateOne(bannerSource);
	}

	public BannerSource unassignBanner(Integer bannerSourceId) {
		BannerSource bannerSource = this.findOne(bannerSourceId);

		bannerSource.setBanner(null);

		bannerSource.setValidatedBy(null);
		bannerSource.setValidatedDate(null);

		return this.updateOne(bannerSource);
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



	}

	@Override
	public void handleAssociationsOnDeletion(BannerSource existing) {
		// Do nothing
	}
}

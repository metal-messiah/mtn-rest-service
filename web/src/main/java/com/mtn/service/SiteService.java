package com.mtn.service;

import com.mtn.model.domain.ShoppingCenter;
import com.mtn.model.domain.Site;
import com.mtn.model.domain.UserProfile;
import com.mtn.model.view.SiteView;
import com.mtn.repository.SiteRepository;
import com.mtn.repository.specification.SiteSpecifications;
import com.mtn.validators.SiteValidator;
import com.vividsolutions.jts.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.springframework.data.jpa.domain.Specifications.where;

@Service
public class SiteService extends EntityService<Site, SiteView> {

	private final UserProfileService userProfileService;

	@Autowired
	public SiteService(SecurityService securityService,
					   SiteRepository repository,
					   SiteValidator validator, UserProfileService userProfileService) {
		super(securityService, repository, validator, Site::new);
		this.userProfileService = userProfileService;
	}

	public List<Site> findAllByShoppingCenterIdUsingSpecs(Integer shoppingCenterId) {
		return this.repository.findAll(
				where(SiteSpecifications.shoppingCenterIdEquals(shoppingCenterId))
						.and(SiteSpecifications.isNotDeleted())
		);
	}

	public List<Site> findAllInGeoJson(String geoJson) {
		return ((SiteRepository) this.repository).findWithinGeoJson(geoJson);
	}

	public Page<Site> findAllDuplicatesUsingSpecs(Pageable page) {
		return this.repository.findAll(where(SiteSpecifications.isDuplicate())
				.and(SiteSpecifications.isNotDeleted()), page);
	}

	public List<Site> findAllInBoundsUsingSpecs(Float north, Float south, Float east, Float west) {
		Integer assigneeId = securityService.getCurrentUser().getId();
		return this.repository.findAll(
				where(SiteSpecifications.withinBoundingBoxOrAssignedTo(north, south, east, west, assigneeId))
						.and(SiteSpecifications.isNotDeleted()));
	}

	public List<Site> findAllInShape(Geometry shape) {
		return ((SiteRepository) this.repository).findWithinGeometry(shape);
	}

	public Page<Site> findAllInBoundsWithoutStoresUsingSpecs(Float north, Float south, Float east, Float west, boolean noStores, Pageable page) {
		Specifications<Site> spec = where(SiteSpecifications.withinBoundingBox(north, south, east, west));
		if (noStores) {
			spec = spec.and(SiteSpecifications.hasNoStore());
		}
		spec = spec.and(SiteSpecifications.isNotDeleted());

		return this.repository.findAll(spec, page);
	}

	@Transactional
	public List<Site> assignSitesToUser(Integer[] siteIds, Integer userId) {
		final UserProfile selectedUser = (userId != null) ? userProfileService.findOne(userId) : null;
		List<Site> sites = this.repository.findAll(Specifications.where(SiteSpecifications.idIn(Arrays.asList(siteIds))));
		sites.forEach(site -> site.setAssignee(selectedUser));
		return sites;
	}

	@Transactional
	public Site addOne(SiteView request, ShoppingCenter shoppingCenter) {
		this.validator.validateForInsert(request);

		UserProfile currentUser = securityService.getCurrentUser();

		Site site = createNewEntityFromRequest(request);
		site.setShoppingCenter(shoppingCenter);
		site.setCreatedBy(currentUser);
		site.setUpdatedBy(currentUser);

		return site;
	}

	@Transactional
	public Site createOne(ShoppingCenter sc, Float latitude, Float longitude) {
		UserProfile currentUser = securityService.getCurrentUser();

		Site site = new Site();
		site.setCreatedBy(currentUser);
		site.setUpdatedBy(currentUser);

		site.setShoppingCenter(sc);
		site.setLatitude(latitude);
		site.setLongitude(longitude);

		return site;
	}

	@Override
	protected void setEntityAttributesFromRequest(Site site, SiteView request) {
		site.setLatitude(request.getLatitude());
		site.setLongitude(request.getLongitude());
		site.setType(request.getType());
		site.setFootprintSqft(request.getFootprintSqft());
		site.setPositionInCenter(request.getPositionInCenter());
		site.setAddress1(request.getAddress1());
		site.setAddress2(request.getAddress2());
		site.setCity(request.getCity());
		site.setCounty(request.getCounty());
		site.setState(request.getState());
		site.setPostalCode(request.getPostalCode());
		site.setCountry(request.getCountry());
		site.setIntersectionType(request.getIntersectionType());
		site.setQuad(request.getQuad());
		site.setIntersectionStreetPrimary(request.getIntersectionStreetPrimary());
		site.setIntersectionStreetSecondary(request.getIntersectionStreetSecondary());
		site.setDuplicate(request.getDuplicate());
	}

	@Override
	public void handleAssociationsOnDeletion(Site existing) {
		existing.setShoppingCenter(null);
		existing.setAssignee(null);
	}
}

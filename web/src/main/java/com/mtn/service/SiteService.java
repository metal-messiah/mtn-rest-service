package com.mtn.service;

import com.mtn.model.domain.ShoppingCenter;
import com.mtn.model.domain.Site;
import com.mtn.model.domain.UserProfile;
import com.mtn.model.view.SiteView;
import com.mtn.repository.SiteRepository;
import com.mtn.repository.specification.SiteSpecifications;
import com.mtn.validators.SiteValidator;
import com.vividsolutions.jts.geom.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
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

	public Page<Site> findAllInRadius(Pageable page, Float latitude, Float longitude, Float radiusMeters) {
		Point circleCenter = new GeometryFactory(new PrecisionModel(100000), 4326)
				.createPoint(new Coordinate(longitude, latitude));
		Specifications<Site> specs = where(SiteSpecifications.withinSphericalDistance(circleCenter, radiusMeters));
		return this.repository.findAll(this.excludeRestrictedAndDeleted(specs), page);
	}

	public List<Site> findAllInGeoJson(String geoJson) {
		return this.repository.findAll(this.getSpecsForFindAllInGeoJson(geoJson));
	}

	public Page<Site> findAllInGeoJson(Pageable page, String geoJson) {
		return this.repository.findAll(this.getSpecsForFindAllInGeoJson(geoJson), page);
	}

	/**
	 * Finds all sites that are in the new bounding box (that are not in the previous bounding box, PLUS all sites
	 * that are in the intersection of the new and previous bounding boxes that have been updated since the provided
	 * updatedAt LocalDateTime
	 *
	 * @param north boundary of new view
	 * @param south boundary of new view
	 * @param east boundary of new view
	 * @param west boundary of new view
	 * @param prevNorth boundary of previous view
	 * @param prevSouth boundary of previous view
	 * @param prevEast boundary of previous view
	 * @param prevWest boundary of previous view
	 * @param updatedAt date of previous pull
	 * @return a list of sites including ones in new view and updated ones in old intersecting view
	 */
	public List<Site> findAllForView(Float north, Float south, Float east, Float west,
									 Float prevNorth, Float prevSouth, Float prevEast, Float prevWest,
									 ZonedDateTime updatedAt) {
		// Get sites in new area, excluding old area
		Specifications<Site> newSiteSpecs = where(SiteSpecifications.withinBoundingBox(north, south, east, west));
		newSiteSpecs = newSiteSpecs.and(Specifications.not(SiteSpecifications.withinBoundingBox(prevNorth, prevSouth, prevEast, prevWest)));
		List<Site> sites = this.repository.findAll(this.excludeRestrictedAndDeleted(newSiteSpecs));

		// Where new area intersects old area, get only those that have been updated
		Specifications<Site> updateSiteSpecs = where(SiteSpecifications.withinBoundingBox(north, south, east, west));
		updateSiteSpecs = updateSiteSpecs.and(SiteSpecifications.withinBoundingBox(prevNorth, prevSouth, prevEast, prevWest));

		LocalDateTime dt = updatedAt.withZoneSameInstant(OffsetDateTime.now().getOffset()).toLocalDateTime();
		updateSiteSpecs = updateSiteSpecs.and(SiteSpecifications.updatedSince(dt));
		List<Site> updatedSites = this.repository.findAll(this.excludeRestrictedAndDeleted(updateSiteSpecs));

		sites.addAll(updatedSites);

		return sites;
	}

	public Page<Site> findAllInBoundsUsingSpecs(Pageable page, Float north, Float south, Float east, Float west) {
		return this.repository.findAll(this.getSpecFindAllBoundaryBox(north, south, east, west), page);
	}

	public List<Site> findAllInBoundsUsingSpecs(Float north, Float south, Float east, Float west) {
		return this.repository.findAll(this.getSpecFindAllBoundaryBox(north, south, east, west));
	}

	public List<Site> findAllInShape(Geometry shape) {
		Specifications<Site> specs = where(SiteSpecifications.withinGeometry(shape));
		return this.repository.findAll(this.excludeRestrictedAndDeleted(specs));
	}

	@Transactional
	public List<Site> assignSitesToUser(List<Integer> siteIds, Integer userId) {
		final UserProfile selectedUser = (userId != null) ? userProfileService.findOne(userId) : null;
		List<Site> sites = this.repository.findAll(Specifications.where(SiteSpecifications.idIn(siteIds)));
		sites.forEach(site -> site.setAssignee(selectedUser));
		return sites;
	}

	@Transactional
	public Site assignSiteToUser(Integer siteId, Integer userId) {
		final UserProfile selectedUser = (userId != null) ? userProfileService.findOne(userId) : null;
		Site site = this.findOne(siteId);
		site.setAssignee(selectedUser);
		return this.repository.save(site);
	}

	@Transactional
	public Site addOne(SiteView request, ShoppingCenter shoppingCenter) {
		this.validator.validateForInsert(request);

		UserProfile currentUser = securityService.getCurrentUser();

		Site site = createNewEntityFromRequest(request);
		site.setShoppingCenter(shoppingCenter);
		site.setCreatedBy(currentUser);
		site.setUpdatedBy(currentUser);

		return this.repository.save(site);
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

		return this.repository.save(site);
	}

	@Override
	protected void setEntityAttributesFromRequest(Site site, SiteView request) {
		site.setLatitude(request.getLatitude());
		site.setLongitude(request.getLongitude());
		site.setType(request.getType());
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
		site.setBackfilledNonGrocery(request.getBackfilledNonGrocery());

		if (request.getAssignee() != null) {
			UserProfile assignee = this.userProfileService.findOneUsingSpecs(request.getAssignee().getId());
			site.setAssignee(assignee);
		} else {
			site.setAssignee(null);
		}
	}

	@Override
	public void handleAssociationsOnDeletion(Site existing) {
		existing.setAssignee(null);
	}

	/**********************************
	 * Private helper Methods
	 **********************************/

	private Specifications<Site> getSpecsForFindAllInGeoJson(String geoJson) {
		Specifications<Site> specs = where(SiteSpecifications.withinGeoJson(geoJson));
		return this.excludeRestrictedAndDeleted(specs);
	}

	private Specifications<Site> getSpecFindAllBoundaryBox(Float north, Float south, Float east, Float west) {
		Specifications<Site> specs = where(SiteSpecifications.withinBoundingBox(north, south, east, west));
		return this.excludeRestrictedAndDeleted(specs);
	}

	private Specifications<Site> excludeRestrictedAndDeleted(Specifications<Site> specs) {
		UserProfile currentUser = this.securityService.getCurrentUser();
		Geometry restriction = (currentUser.getRestrictionBoundary() != null) ? currentUser.getRestrictionBoundary().getBoundary() : null;

		specs = specs.and(SiteSpecifications.isNotDeleted());
		if (restriction != null) {
			specs = specs.and(SiteSpecifications.withinGeometry(restriction));
		}
		return specs;
	}

}

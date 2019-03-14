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
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	public List<Site> findAllInGeoJson(String geoJson) {
		UserProfile currentUser = this.securityService.getCurrentUser();
		Geometry restriction = (currentUser.getRestrictionBoundary() != null) ? currentUser.getRestrictionBoundary().getBoundary() : null;
		return ((SiteRepository) this.repository).findWithinGeoJson(geoJson, restriction);
	}

	public List<Site> findAllDuplicatesUsingSpecs() {
		return this.repository.findAll(where(SiteSpecifications.isDuplicate())
				.and(SiteSpecifications.isNotDeleted()));
	}

	public List<Site> findAllInBoundsUsingSpecs(Float north, Float south, Float east, Float west) {
		UserProfile currentUser = this.securityService.getCurrentUser();
		Geometry restriction = (currentUser.getRestrictionBoundary() != null) ? currentUser.getRestrictionBoundary().getBoundary() : null;
		return ((SiteRepository) this.repository).findAllInBounds(restriction, north, south, east, west);
	}

	public List<Site> findAllInShape(Geometry shape) {
		UserProfile currentUser = this.securityService.getCurrentUser();
		Geometry restriction = (currentUser.getRestrictionBoundary() != null) ? currentUser.getRestrictionBoundary().getBoundary() : null;
		return ((SiteRepository) this.repository).findWithinGeometry(shape, restriction);
	}

	public List<Site> findAllInBoundsWithoutStoresUsingSpecs(Float north, Float south, Float east, Float west, boolean noStores, Pageable page) {
		UserProfile currentUser = this.securityService.getCurrentUser();
		Geometry restriction = (currentUser.getRestrictionBoundary() != null) ? currentUser.getRestrictionBoundary().getBoundary() : null;
		return ((SiteRepository) this.repository).findAllInBoundsWithoutStores(restriction, north, south, east, west);
	}

	@Transactional
	public List<Site> assignSitesToUser(List<Integer> siteIds, Integer userId) {
		final UserProfile selectedUser = (userId != null) ? userProfileService.findOne(userId) : null;
		List<Site> sites = this.repository.findAll(Specifications.where(SiteSpecifications.idIn(siteIds)));
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
}

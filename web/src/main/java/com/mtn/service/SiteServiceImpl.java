package com.mtn.service;

import com.mtn.constant.StoreType;
import com.mtn.model.domain.*;
import com.mtn.repository.SiteRepository;
import com.mtn.repository.specification.AuditingEntitySpecifications;
import com.mtn.repository.specification.SiteSpecifications;
import com.mtn.validators.SiteValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;

import static com.mtn.repository.specification.SiteSpecifications.*;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Created by Allen on 4/24/2017.
 */
@Service
public class SiteServiceImpl extends EntityServiceImpl<Site> implements SiteService {

    @Autowired
    private SiteRepository siteRepository;
    @Autowired
    private StoreService storeService;
    @Autowired
    private ShoppingCenterService shoppingCenterService;
    @Autowired
    private SiteValidator siteValidator;
    @Autowired
    private ProjectService projectService;

    @Override
    @Transactional
    public Store addOneStoreToSite(Integer siteId, Store request, boolean overrideActiveStore) {
        Site existing = findOneUsingSpecs(siteId);
        getValidator().validateNotNull(existing);
        request.setSite(existing);

        //If the new store is ACTIVE, we have some special handling to do
        if (request.getStoreType() == StoreType.ACTIVE) {
            //First, ensure the request is valid for insert
            storeService.getValidator().validateForInsert(request);

            //Then, check for another existing ACTIVE store
            Store existingActiveStore = SiteService.findActiveStore(existing);

            //If one exists, and no override is provided, throw an error
            if (existingActiveStore != null && !overrideActiveStore) {
                throw new IllegalArgumentException(String.format("A Site may only have one Active Store at a time. Store ID %d is currently set as this Site's Active Store.", existingActiveStore.getId()));
            }
            //Else if one exists, set it to historical before proceeding
            else if (existingActiveStore != null) {
                existingActiveStore.setStoreType(StoreType.HISTORICAL);
                existingActiveStore.setUpdatedBy(securityService.getCurrentUser());
            }
        }

        existing.setUpdatedBy(securityService.getCurrentUser());

        return storeService.addOne(request);
    }

    @Override
    public List<Site> findAllByShoppingCenterIdUsingSpecs(Integer shoppingCenterId) {
        return siteRepository.findAll(
                where(shoppingCenterIdEquals(shoppingCenterId))
                        .and(isNotDeleted())
        );
    }

    @Override
    public List<Site> findAllInGeoJson(String geoJson) {
        return siteRepository.findWithinGeoJson(geoJson);
    }

    @Override
    public Page<Site> findAllDuplicatesUsingSpecs(Pageable page) {
        return siteRepository.findAll(where(isDuplicate()).and(isNotDeleted()), page);
    }

    @Override
    public List<Site> findAllInBoundsUsingSpecs(Float north, Float south, Float east, Float west) {
        Integer assigneeId = securityService.getCurrentUser().getId();
        return siteRepository.findAll(
                where(SiteSpecifications.withinBoundingBoxOrAssignedTo(north, south, east, west, assigneeId))
                .and(AuditingEntitySpecifications.isNotDeleted()));
    }

    @Override
    public List<Site> findAllInProjectBoundary(Integer projectId) {
        Project project = this.projectService.findOne(projectId);
        if (project != null && project.getBoundary() != null) {
            return this.siteRepository.findWithinGeometry(project.getBoundary().getBoundary());
        } else {
            throw new EntityNotFoundException(String.format("Project does not have a boundary", projectId));
        }
    }

    @Override
    public Page<Site> findAllInBoundsWithoutStoresUsingSpecs(Float north, Float south, Float east, Float west, boolean noStores, Pageable page) {
        Specifications<Site> spec = where(SiteSpecifications.withinBoundingBox(north, south, east, west));
        if (noStores) {
            spec = spec.and(SiteSpecifications.hasNoStore());
        }
        spec = spec.and(AuditingEntitySpecifications.isNotDeleted());

        return siteRepository.findAll(spec, page);
    }

    @Override
    public Site updateEntityAttributes(Site existing, Site request) {
        existing.setLatitude(request.getLatitude());
        existing.setLongitude(request.getLongitude());
        existing.setType(request.getType());
        existing.setIntersectionType(request.getIntersectionType());
        existing.setAddress1(request.getAddress1());
        existing.setAddress2(request.getAddress2());
        existing.setCity(request.getCity());
        existing.setState(request.getState());
        existing.setPostalCode(request.getPostalCode());
        existing.setCounty(request.getCounty());
        existing.setCountry(request.getCountry());
        existing.setFootprintSqft(request.getFootprintSqft());
        existing.setIntersectionStreetPrimary(request.getIntersectionStreetPrimary());
        existing.setIntersectionStreetSecondary(request.getIntersectionStreetSecondary());
        existing.setQuad(request.getQuad());
        existing.setPositionInCenter(request.getPositionInCenter());
        existing.setDuplicate(request.getDuplicate());

        return existing;
    }

    @Override
    public String getEntityName() {
        return "Site";
    }

    @Override
    public void handleAssociationsOnCreation(Site request) {
        if (request.getShoppingCenter() == null) {
            ShoppingCenter newSC = this.shoppingCenterService.addOne(new ShoppingCenter());
            request.setShoppingCenter(newSC);
        }
    }

    @Override
    public SiteValidator getValidator() {
        return siteValidator;
    }

    @Override
    @Transactional
    public List<Site> assignSitesToUser(Integer[] siteIds, Integer userId) {
        final UserProfile selectedUser = (userId != null) ? userProfileService.findOne(userId) : null;
        List<Site> sites = this.siteRepository.findAll(Specifications.where(SiteSpecifications.idIn(Arrays.asList(siteIds))));
        sites.forEach(site -> {
            site.setAssignee(selectedUser);
        });
        return sites;
    }
}

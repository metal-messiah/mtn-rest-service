package com.mtn.service;

import com.mtn.constant.StoreType;
import com.mtn.model.domain.*;
import com.mtn.repository.SiteRepository;
import com.mtn.validators.SiteValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
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
        return getEntityRepository().findAll(
                where(shoppingCenterIdEquals(shoppingCenterId))
                        .and(isNotDeleted())
        );
    }

    @Override
    public Page<Site> findAllUsingSpecs(Pageable page) {
        return getEntityRepository().findAllByDeletedDateIsNull(page);
    }

    @Override
    public Page<Site> findAllDuplicatesUsingSpecs(Pageable page) {
        return getEntityRepository().findAll(where(isNotDeleted()).and(isDuplicate()), page);
    }

    @Override
    public List<Site> findAllInBoundsUsingSpecs(Float north, Float south, Float east, Float west) {
        Integer assigneeId = securityService.getCurrentUser().getId();
        Specification<Site> spec = where(isNotDeleted()).and(withinBoundingBoxOrAssignedTo(north, south, east, west, assigneeId));

        return getEntityRepository().findAll(spec);
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
        Specification<Site> spec = where(isNotDeleted()).and(withinBoundingBox(north, south, east, west));

        if (noStores) {
            spec = ((Specifications<Site>) spec).and(hasNoStore());
        }

        return getEntityRepository().findAll(spec, page);
    }

    @Override
    public Site findOneUsingSpecs(Integer id) {
        return getEntityRepository().findByIdAndDeletedDateIsNull(id);
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
    public void handleAssociationsOnDeletion(Site existing) {
        // TODO - Handle Stores
    }

    @Override
    public void handleAssociationsOnCreation(Site request) {
        ShoppingCenter newSC = this.shoppingCenterService.addOne(new ShoppingCenter());
        request.setShoppingCenter(newSC);
    }

    @Override
    public SiteRepository getEntityRepository() {
        return siteRepository;
    }

    @Override
    public SiteValidator getValidator() {
        return siteValidator;
    }

    @Override
    @Transactional
    public List<Site> assignSitesToUser(Integer[] siteIds, Integer userId) {
        final UserProfile selectedUser = (userId != null) ? userProfileService.findOne(userId) : null;
        List<Site> sites = this.siteRepository.findAllByIdIn(siteIds);
        sites.forEach(site -> {
            site.setAssignee(selectedUser);
        });
        return sites;
    }
}

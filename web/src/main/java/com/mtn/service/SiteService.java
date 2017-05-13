package com.mtn.service;

import com.mtn.exception.VersionConflictException;
import com.mtn.model.domain.Site;
import com.mtn.model.domain.Store;
import com.mtn.model.domain.UserProfile;
import com.mtn.model.view.SiteView;
import com.mtn.repository.SiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.mtn.repository.specification.SiteSpecifications.*;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Created by Allen on 4/24/2017.
 */
@Service
public class SiteService extends ValidatingDataService<Site> {

    @Autowired
    private SiteRepository siteRepository;
    @Autowired
    private StoreService storeService;
    @Autowired
    private SecurityService securityService;

    @Transactional
    public Site addOne(Site request) {
        validateForInsert(request);

        UserProfile currentUser = securityService.getCurrentPersistentUser();
        request.setCreatedBy(currentUser);
        request.setUpdatedBy(currentUser);

        return siteRepository.save(request);
    }

    @Transactional
    public Store addOneStoreToSite(Integer siteId, Store request) {
        Site existing = findOneUsingSpecs(siteId);
        validateNotNull(existing);

        request.setSite(existing);
        existing.setUpdatedBy(securityService.getCurrentPersistentUser());

        return storeService.addOne(request);
    }

    @Transactional
    public void deleteOne(Integer id) {
        Site existing = findOneUsingSpecs(id);
        if (existing == null) {
            throw new IllegalArgumentException("No Site found with this id");
        }

        existing.setDeletedBy(securityService.getCurrentPersistentUser());
    }

    public List<Site> findAllByShoppingCenterIdUsingSpecs(Integer shoppingCenterId) {
        return siteRepository.findAll(
                where(shoppingCenterIdEquals(shoppingCenterId))
                        .and(isNotDeleted())
        );
    }

    public Site findOneUsingSpecs(Integer id) {
        return siteRepository.findOne(
                where(idEquals(id))
                        .and(isNotDeleted())
        );
    }

    @Transactional
    public Site updateOne(Integer id, Site request) {
        validateForUpdate(request);

        Site existing = findOneUsingSpecs(id);
        if (existing == null) {
            throw new IllegalArgumentException("No Site found with this id");
        }
        if (!request.getVersion().equals(existing.getVersion())) {
            throw new VersionConflictException(new SiteView(existing));
        }

        existing.setLocation(request.getLocation());
        existing.setType(request.getType());
        existing.setLocationType(request.getLocationType());
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
        existing.setIntersectionQuad(request.getIntersectionQuad());
        existing.setPositionInCenter(request.getPositionInCenter());
        existing.setUpdatedBy(securityService.getCurrentPersistentUser());

        return existing;
    }

    @Override
    public String getEntityName() {
        return "Site";
    }

    @Override
    public void validateBusinessRules(Site object) {
        if (object.getLocation() == null) {
            throw new IllegalArgumentException("Site location must be provided");
        } else if (object.getShoppingCenter() == null) {
            throw new IllegalStateException("Site ShoppingCenter should have been set by now");
        } else if (object.getType() == null) {
            throw new IllegalArgumentException("Site type must be provided");
        }
    }

    @Override
    public void validateDoesNotExist(Site object) {
        //No unique constraints to enforce yet
    }
}

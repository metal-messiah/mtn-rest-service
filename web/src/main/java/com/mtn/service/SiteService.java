package com.mtn.service;

import com.mtn.exception.VersionConflictException;
import com.mtn.model.domain.Site;
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
    private UserProfileService userProfileService;

    @Transactional
    public Site addOne(Site request) {
        validateForInsert(request);

        UserProfile systemAdministrator = userProfileService.findSystemAdministrator();
        request.setCreatedBy(systemAdministrator);
        request.setUpdatedBy(systemAdministrator);

        return siteRepository.save(request);
    }

    @Transactional
    public void deleteOne(Integer id) {
        Site existing = findOneUsingSpecs(id);
        if (existing == null) {
            throw new IllegalArgumentException("No Site found with this id");
        }

        existing.setDeletedBy(userProfileService.findSystemAdministrator());
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
        validateNotNull(request);
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
        existing.setUpdatedBy(userProfileService.findSystemAdministrator());

        return existing;
    }

    @Override
    public void validateForInsert(Site object) {
        validateNotNull(object);
        validateDoesNotExist(object);

        if (object.getId() != null) {
            throw new IllegalArgumentException("Site id must be null");
        }

        validateBusinessRules(object);
    }

    @Override
    public void validateForUpdate(Site object) {
        validateNotNull(object);
        if (object.getId() == null) {
            throw new IllegalArgumentException("Site id must be provided");
        }

        validateBusinessRules(object);
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
    public void validateNotNull(Site object) {
        if (object == null) {
            throw new IllegalArgumentException("Site must not be null");
        }
    }

    @Override
    public void validateDoesNotExist(Site object) {
        //No unique constraints to enforce yet
    }
}

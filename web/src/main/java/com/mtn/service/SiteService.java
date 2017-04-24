package com.mtn.service;

import com.mtn.model.domain.Site;
import com.mtn.model.domain.UserProfile;
import com.mtn.repository.SiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.mtn.repository.specification.SiteSpecifications.isNotDeleted;
import static com.mtn.repository.specification.SiteSpecifications.shoppingCenterIdEquals;
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

    public List<Site> findAllByShoppingCenterIdUsingSpecs(Integer shoppingCenterId) {
        return siteRepository.findAll(
                where(shoppingCenterIdEquals(shoppingCenterId))
                        .and(isNotDeleted())
        );
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

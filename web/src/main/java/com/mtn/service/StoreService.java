package com.mtn.service;

import com.mtn.exception.VersionConflictException;
import com.mtn.model.domain.Store;
import com.mtn.model.domain.UserProfile;
import com.mtn.model.view.StoreView;
import com.mtn.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.mtn.repository.specification.StoreSpecifications.*;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Created by Allen on 4/26/2017.
 */
@Service
public class StoreService extends ValidatingDataService<Store> {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private UserProfileService userProfileService;

    @Transactional
    public Store addOne(Store request) {
        validateForInsert(request);

        UserProfile systemAdministrator = userProfileService.findSystemAdministrator();
        request.setCreatedBy(systemAdministrator);
        request.setUpdatedBy(systemAdministrator);

        return storeRepository.save(request);
    }

    @Transactional
    public void deleteOne(Integer id) {
        Store existing = findOneUsingSpecs(id);
        if (existing == null) {
            throw new IllegalArgumentException("No Site found with this id");
        }

        existing.setDeletedBy(userProfileService.findSystemAdministrator());
    }

    public List<Store> findAllBySiteIdUsingSpecs(Integer siteId) {
        return storeRepository.findAll(
                where(siteIdEquals(siteId))
                        .and(isNotDeleted())
        );
    }

    public Store findOneUsingSpecs(Integer id) {
        return storeRepository.findOne(
                where(idEquals(id))
                        .and(isNotDeleted())
        );
    }

    @Transactional
    public Store updateOne(Integer id, Store request) {
        validateForUpdate(request);

        Store existing = findOneUsingSpecs(id);
        if (existing == null) {
            throw new IllegalArgumentException("No Store found with this id");
        }
        if (!request.getVersion().equals(existing.getVersion())) {
            throw new VersionConflictException(new StoreView(existing));
        }

        existing.setName(request.getName());
        existing.setFit(request.getFit());
        existing.setFormat(request.getFormat());
        existing.setActive(request.getActive());
        existing.setAreaSales(request.getAreaSales());
        existing.setAreaSalesPercentOfTotal(request.getAreaSalesPercentOfTotal());
        existing.setAreaTotal(request.getAreaTotal());
        existing.setAreaIsEstimate(request.getAreaIsEstimate());
        existing.setOpenedDate(request.getOpenedDate());
        existing.setClosedDate(request.getClosedDate());
        existing.setUpdatedBy(userProfileService.findSystemAdministrator());

        return existing;
    }

    @Override
    public void validateForInsert(Store object) {
        validateNotNull(object);
        validateDoesNotExist(object);

        if (object.getId() != null) {
            throw new IllegalArgumentException("Store id must be null");
        }

        validateBusinessRules(object);
    }

    @Override
    public void validateForUpdate(Store object) {
        validateNotNull(object);
        if (object.getId() == null) {
            throw new IllegalArgumentException("Store id must be provided");
        }

        validateBusinessRules(object);
    }

    @Override
    public void validateBusinessRules(Store object) {
        if (object.getSite() == null) {
            throw new IllegalStateException("Store Site should have been set by now");
        } else if (object.getActive() == null) {
            throw new IllegalArgumentException("Store active must be true or false");
        } else if (object.getAreaIsEstimate() == null) {
            throw new IllegalArgumentException("Store areaIsEstimate must be true or false");
        }
    }

    @Override
    public void validateNotNull(Store object) {
        if (object == null) {
            throw new IllegalArgumentException("Store must not be null");
        }
    }

    @Override
    public void validateDoesNotExist(Store object) {
        //No unique constraints to enforce yet
    }
}
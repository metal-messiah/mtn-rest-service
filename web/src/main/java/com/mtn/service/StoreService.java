package com.mtn.service;

import com.mtn.constant.StoreType;
import com.mtn.exception.VersionConflictException;
import com.mtn.model.domain.Store;
import com.mtn.model.domain.UserProfile;
import com.mtn.model.view.StoreView;
import com.mtn.repository.StoreRepository;
import org.apache.commons.lang3.StringUtils;
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
    private SecurityService securityService;

    @Transactional
    public Store addOne(Store request) {
        validateForInsert(request);

        UserProfile currentUser = securityService.getCurrentPersistentUser();
        request.setCreatedBy(currentUser);
        request.setUpdatedBy(currentUser);

        return storeRepository.save(request);
    }

    @Transactional
    public void deleteOne(Integer id) {
        Store existing = findOneUsingSpecs(id);
        if (existing == null) {
            throw new IllegalArgumentException("No Site found with this id");
        }

        existing.setDeletedBy(securityService.getCurrentPersistentUser());
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
        existing.setType(request.getType());
        existing.setAreaSales(request.getAreaSales());
        existing.setAreaSalesPercentOfTotal(request.getAreaSalesPercentOfTotal());
        existing.setAreaTotal(request.getAreaTotal());
        existing.setAreaIsEstimate(request.getAreaIsEstimate());
        existing.setOpenedDate(request.getOpenedDate());
        existing.setClosedDate(request.getClosedDate());
        existing.setUpdatedBy(securityService.getCurrentPersistentUser());

        return existing;
    }

    @Override
    public String getEntityName() {
        return "Store";
    }

    @Override
    public void validateBusinessRules(Store object) {
        if (object.getSite() == null) {
            throw new IllegalStateException("Store Site should have been set by now");
        } else if (object.getType() == null) {
            throw new IllegalArgumentException(String.format("Store type must be one of: %s", StringUtils.join(StoreType.values(), ", ")));
        } else if (object.getAreaIsEstimate() == null) {
            throw new IllegalArgumentException("Store areaIsEstimate must be true or false");
        }
    }

    @Override
    public void validateDoesNotExist(Store object) {
        //No unique constraints to enforce yet
    }
}

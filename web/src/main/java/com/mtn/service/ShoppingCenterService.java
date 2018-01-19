package com.mtn.service;

import com.mtn.exception.VersionConflictException;
import com.mtn.model.domain.*;
import com.mtn.model.view.ShoppingCenterView;
import com.mtn.repository.ShoppingCenterRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.mtn.repository.specification.ShoppingCenterSpecifications.*;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Created by Allen on 4/23/2017.
 */
@Service
public class ShoppingCenterService extends ValidatingDataService<ShoppingCenter> {

    @Autowired
    private ShoppingCenterRepository shoppingCenterRepository;
    @Autowired
    private SiteService siteService;
    @Autowired
    private ShoppingCenterSurveyService surveyService;
    @Autowired
    private ShoppingCenterCasingService casingService;
    @Autowired
    private SecurityService securityService;

    @Transactional
    public ShoppingCenter addOne(ShoppingCenter request) {
        validateForInsert(request);

        UserProfile currentUser = securityService.getCurrentUser();
        request.setCreatedBy(currentUser);
        request.setUpdatedBy(currentUser);

        return shoppingCenterRepository.save(request);
    }

    @Transactional
    public ShoppingCenterCasing addOneCasingToShoppingCenter(Integer shoppingCenterId, ShoppingCenterCasing request) {
        ShoppingCenter existing = findOneUsingSpecs(shoppingCenterId);
        validateNotNull(existing);

        request.setShoppingCenter(existing);
        existing.setUpdatedBy(securityService.getCurrentUser());

        return casingService.addOne(request);
    }

    @Transactional
    public Site addOneSiteToShoppingCenter(Integer shoppingCenterId, Site request) {
        ShoppingCenter existing = findOneUsingSpecs(shoppingCenterId);
        validateNotNull(existing);

        request.setShoppingCenter(existing);
        existing.setUpdatedBy(securityService.getCurrentUser());

        return siteService.addOne(request);
    }

    @Transactional
    public ShoppingCenterSurvey addOneSurveyToShoppingCenter(Integer shoppingCenterId, ShoppingCenterSurvey request) {
        ShoppingCenter existing = findOneUsingSpecs(shoppingCenterId);
        validateNotNull(existing);

        request.setShoppingCenter(existing);
        existing.setUpdatedBy(securityService.getCurrentUser());

        return surveyService.addOne(request);
    }

    @Transactional
    public void deleteOne(Integer id) {
        ShoppingCenter existing = findOneUsingSpecs(id);
        if (existing == null) {
            throw new IllegalArgumentException("No ShoppingCenter found with this id");
        }

        existing.setDeletedBy(securityService.getCurrentUser());
    }

    public Page<ShoppingCenter> findAllUsingSpecs(Pageable page) {
        return shoppingCenterRepository.findAll(
                where(isNotDeleted())
                , page
        );
    }

    public List<ShoppingCenter> findAllByProjectId(Integer id) {
        return shoppingCenterRepository.findAllByInteractionsProjectIdAndDeletedDateIsNull(id);
    }

    public Page<ShoppingCenter> findAllByNameUsingSpecs(String name, Pageable page) {
        return shoppingCenterRepository.findAll(
                where(nameContains(name))
                        .and(isNotDeleted())
                , page
        );
    }

    public Page<ShoppingCenter> findAllByOwnerUsingSpecs(String owner, Pageable page) {
        return shoppingCenterRepository.findAll(
                where(ownerContains(owner))
                        .and(isNotDeleted())
                , page
        );
    }

    public Page<ShoppingCenter> findAllByNameOrOwnerUsingSpecs(String q, Pageable page) {
        return shoppingCenterRepository.findAll(
                where(
                        where(nameContains(q))
                                .or(ownerContains(q)))
                        .and(isNotDeleted())
                , page
        );
    }

    public ShoppingCenter findOne(Integer id) {
        return shoppingCenterRepository.findOne(id);
    }

    public ShoppingCenter findOneUsingSpecs(Integer id) {
        return shoppingCenterRepository.findOne(
                where(idEquals(id))
                        .and(isNotDeleted())
        );
    }

    @Transactional
    public ShoppingCenter updateOne(Integer id, ShoppingCenter request) {
        validateNotNull(request);
        validateForUpdate(request);

        ShoppingCenter existing = findOneUsingSpecs(id);
        if (existing == null) {
            throw new IllegalArgumentException("No ShoppingCenter found with this id");
        }
        if (!request.getVersion().equals(existing.getVersion())) {
            throw new VersionConflictException(new ShoppingCenterView(existing));
        }

        existing.setName(request.getName());
        existing.setOwner(request.getOwner());
        existing.setUpdatedBy(securityService.getCurrentUser());

        return existing;
    }

    @Override
    public String getEntityName() {
        return "ShoppingCenter";
    }

    @Override
    public void validateForUpdate(ShoppingCenter object) {
        super.validateForUpdate(object);

        if (object.getVersion() == null) {
            throw new IllegalArgumentException("ShoppingCenter version must be provided");
        }
    }

    @Override
    public void validateBusinessRules(ShoppingCenter object) {
        if (StringUtils.isBlank(object.getName())) {
            throw new IllegalArgumentException("ShoppingCenter name must be provided");
        }
    }

    @Override
    public void validateUnique(ShoppingCenter object) {
        //No unique constraint (thus far) to enforce
    }
}

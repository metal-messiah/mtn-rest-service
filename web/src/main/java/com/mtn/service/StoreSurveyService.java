package com.mtn.service;

import com.mtn.model.domain.StoreSurvey;
import com.mtn.model.domain.UserProfile;
import com.mtn.repository.StoreSurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.mtn.repository.specification.StoreSurveySpecifications.idEquals;
import static com.mtn.repository.specification.StoreSurveySpecifications.isNotDeleted;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Created by Allen on 5/6/2017.
 */
@Service
public class StoreSurveyService extends ValidatingDataService<StoreSurvey> {

    @Autowired
    private StoreSurveyRepository surveyRepository;
    @Autowired
    private SecurityService securityService;

    @Transactional
    public StoreSurvey addOne(StoreSurvey request) {
        validateForInsert(request);

        UserProfile currentUser = securityService.getCurrentPersistentUser();
        request.setCreatedBy(currentUser);
        request.setUpdatedBy(currentUser);

        return surveyRepository.save(request);
    }

    @Transactional
    public void deleteOne(Integer id) {
        StoreSurvey existing = findOneUsingSpecs(id);
        if (existing == null) {
            throw new IllegalArgumentException("No StoreSurvey found with this id");
        }

        existing.setDeletedBy(securityService.getCurrentPersistentUser());
    }

    public List<StoreSurvey> findAllByStoreId(Integer storeId) {
        return surveyRepository.findAllByStoreId(storeId);
    }

    public StoreSurvey findOne(Integer id) {
        return surveyRepository.findOne(id);
    }

    public StoreSurvey findOneUsingSpecs(Integer id) {
        return surveyRepository.findOne(
                where(idEquals(id))
                        .and(isNotDeleted())
        );
    }

    @Transactional
    public StoreSurvey updateOne(Integer id, StoreSurvey request) {
        validateNotNull(request);
        validateForUpdate(request);

        StoreSurvey existing = findOneUsingSpecs(id);
        if (existing == null) {
            throw new IllegalArgumentException("No StoreSurvey found with this id");
        }

        existing.setAreaIsEstimate(request.getAreaIsEstimate());
        existing.setAreaSales(request.getAreaSales());
        existing.setAreaSalesPercentOfTotal(request.getAreaSalesPercentOfTotal());
        existing.setAreaTotal(request.getAreaTotal());
        existing.setFit(request.getFit());
        existing.setFormat(request.getFormat());
        existing.setUpdatedBy(securityService.getCurrentPersistentUser());

        return existing;
    }

    @Override
    public String getEntityName() {
        return "StoreSurvey";
    }

    @Override
    public void validateForInsert(StoreSurvey object) {
        super.validateForInsert(object);

        if (object.getStore() == null) {
            throw new IllegalStateException("StoreSurvey was not mapped to Store before saving!");
        }
    }

    @Override
    public void validateBusinessRules(StoreSurvey object) {
        //No special rules at this time
    }

    @Override
    public void validateDoesNotExist(StoreSurvey object) {
        //No unique contraints to enforce
    }
}

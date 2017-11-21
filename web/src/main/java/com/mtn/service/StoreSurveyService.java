package com.mtn.service;

import com.mtn.exception.VersionConflictException;
import com.mtn.model.domain.StoreSurvey;
import com.mtn.model.domain.UserProfile;
import com.mtn.model.view.SimpleStoreSurveyView;
import com.mtn.repository.StoreSurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.mtn.repository.specification.StoreSurveySpecifications.*;
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

        UserProfile currentUser = securityService.getCurrentUser();
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

        existing.setDeletedBy(securityService.getCurrentUser());
    }

    public List<StoreSurvey> findAllByProjectId(Integer id) {
        return surveyRepository.findAllByInteractionsProjectIdAndDeletedDateIsNull(id);
    }

    public List<StoreSurvey> findAllByStoreId(Integer storeId) {
        return surveyRepository.findAllByStoreId(storeId);
    }

    public List<StoreSurvey> findAllByStoreIdUsingSpecs(Integer storeId) {
        return surveyRepository.findAll(
                where(storeIdEquals(storeId))
                        .and(isNotDeleted())
        );
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
        if (!request.getVersion().equals(existing.getVersion())) {
            throw new VersionConflictException(new SimpleStoreSurveyView(existing));
        }

        existing.setAreaIsEstimate(request.getAreaIsEstimate());
        existing.setAreaSales(request.getAreaSales());
        existing.setAreaSalesPercentOfTotal(request.getAreaSalesPercentOfTotal());
        existing.setAreaTotal(request.getAreaTotal());
        existing.setFit(request.getFit());
        existing.setFormat(request.getFormat());
        existing.setNote(request.getNote());
        existing.setStoreIsOpen24(request.getStoreIsOpen24());
        existing.setNaturalFoodsAreIntegrated(request.getNaturalFoodsAreIntegrated());
        existing.setRegisterCountNormal(request.getRegisterCountNormal());
        existing.setRegisterCountExpress(request.getRegisterCountExpress());
        existing.setRegisterCountSelfCheckout(request.getRegisterCountSelfCheckout());
        existing.setFuelDispenserCount(request.getFuelDispenserCount());
        existing.setFuelIsOpen24(request.getFuelIsOpen24());
        existing.setPharmacyIsOpen24(request.getPharmacyIsOpen24());
        existing.setPharmacyHasDriveThrough(request.getPharmacyHasDriveThrough());
        existing.setDepartmentBakery(request.getDepartmentBakery());
        existing.setDepartmentBank(request.getDepartmentBank());
        existing.setDepartmentBeer(request.getDepartmentBeer());
        existing.setDepartmentBulk(request.getDepartmentBulk());
        existing.setDepartmentCheese(request.getDepartmentCheese());
        existing.setDepartmentCoffee(request.getDepartmentCoffee());
        existing.setDepartmentDeli(request.getDepartmentDeli());
        existing.setDepartmentExpandedGm(request.getDepartmentExpandedGm());
        existing.setDepartmentExtensivePreparedFoods(request.getDepartmentExtensivePreparedFoods());
        existing.setDepartmentFloral(request.getDepartmentFloral());
        existing.setDepartmentFuel(request.getDepartmentFuel());
        existing.setDepartmentHotBar(request.getDepartmentHotBar());
        existing.setDepartmentInStoreRestaurant(request.getDepartmentInStoreRestaurant());
        existing.setDepartmentLiquor(request.getDepartmentLiquor());
        existing.setDepartmentMeat(request.getDepartmentMeat());
        existing.setDepartmentNatural(request.getDepartmentNatural());
        existing.setDepartmentOliveBar(request.getDepartmentOliveBar());
        existing.setDepartmentPharmacy(request.getDepartmentPharmacy());
        existing.setDepartmentPreparedFoods(request.getDepartmentPreparedFoods());
        existing.setDepartmentSaladBar(request.getDepartmentSaladBar());
        existing.setDepartmentSeafood(request.getDepartmentSeafood());
        existing.setDepartmentSeating(request.getDepartmentSeating());
        existing.setDepartmentSushi(request.getDepartmentSushi());
        existing.setDepartmentWine(request.getDepartmentWine());
        existing.setAccessibilityFarthestFromEntrance(request.getAccessibilityFarthestFromEntrance());
        existing.setAccessibilityMainIntersectionHasTrafficLight(request.getAccessibilityMainIntersectionHasTrafficLight());
        existing.setAccessibilityMainIntersectionNeedsTrafficLight(request.getAccessibilityMainIntersectionNeedsTrafficLight());
        existing.setAccessibilityMultipleRetailersBeforeSite(request.getAccessibilityMultipleRetailersBeforeSite());
        existing.setAccessibilitySetBackTwiceParkingLength(request.getAccessibilitySetBackTwiceParkingLength());
        existing.setAccessibilityRating(request.getAccessibilityRating());
        existing.setParkingOutparcelsInterfereWithParking(request.getParkingOutparcelsInterfereWithParking());
        existing.setParkingDirectAccessToParking(request.getParkingDirectAccessToParking());
        existing.setParkingSmallParkingField(request.getParkingSmallParkingField());
        existing.setParkingHasTSpaces(request.getParkingHasTSpaces());
        existing.setParkingRating(request.getParkingRating());
        existing.setVisibilityHillDepressionBlocksView(request.getVisibilityHillDepressionBlocksView());
        existing.setVisibilityOutparcelsBlockView(request.getVisibilityOutparcelsBlockView());
        existing.setVisibilitySignOnMain(request.getVisibilitySignOnMain());
        existing.setVisibilityStoreFacesMainRoad(request.getVisibilityStoreFacesMainRoad());
        existing.setVisibilityRating(request.getVisibilityRating());
        existing.setSeasonalityJan(request.getSeasonalityJan());
        existing.setSeasonalityFeb(request.getSeasonalityFeb());
        existing.setSeasonalityMar(request.getSeasonalityMar());
        existing.setSeasonalityApr(request.getSeasonalityApr());
        existing.setSeasonalityMay(request.getSeasonalityMay());
        existing.setSeasonalityJun(request.getSeasonalityJun());
        existing.setSeasonalityJul(request.getSeasonalityJul());
        existing.setSeasonalityAug(request.getSeasonalityAug());
        existing.setSeasonalitySep(request.getSeasonalitySep());
        existing.setSeasonalityOct(request.getSeasonalityOct());
        existing.setSeasonalityNov(request.getSeasonalityNov());
        existing.setSeasonalityDec(request.getSeasonalityDec());
        existing.setLegacyCasingId(request.getLegacyCasingId());
        existing.setUpdatedBy(securityService.getCurrentUser());

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
        if (object.getVersion() == null) {
            throw new IllegalArgumentException("StoreSurvey version must be provided");
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

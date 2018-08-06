package com.mtn.service;

import com.mtn.model.domain.StoreSurvey;
import com.mtn.validators.StoreSurveyValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Allen on 5/6/2017.
 */
@Service
public class StoreSurveyServiceImpl extends StoreChildServiceImpl<StoreSurvey> implements StoreSurveyService {

	@Autowired
	private StoreSurveyValidator storeSurveyValidator;

	@Override
	public StoreSurvey updateEntityAttributes(StoreSurvey existing, StoreSurvey request) {
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
		existing.setDepartmentOnlinePickup(request.getDepartmentOnlinePickup());
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
		existing.setParkingHasAngledSpaces(request.getParkingHasAngledSpaces());
		existing.setParkingHasParkingHog(request.getParkingHasParkingHog());
		existing.setParkingIsPoorlyLit(request.getParkingIsPoorlyLit());
		existing.setParkingRating(request.getParkingRating());
		existing.setParkingSpaceCount(request.getParkingSpaceCount());
		existing.setVisibilityHillDepressionBlocksView(request.getVisibilityHillDepressionBlocksView());
		existing.setVisibilityTreesBlockView(request.getVisibilityTreesBlockView());
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
		existing.setSeasonalityNotes(request.getSeasonalityNotes());

		return existing;
	}

	@Override
	public String getEntityName() {
		return "StoreSurvey";
	}

	@Override
	public StoreSurveyValidator getValidator() {
		return storeSurveyValidator;
	}

}

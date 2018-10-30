package com.mtn.service;

import com.mtn.model.domain.Store;
import com.mtn.model.domain.StoreSurvey;
import com.mtn.model.domain.UserProfile;
import com.mtn.model.utils.StoreUtil;
import com.mtn.model.view.StoreSurveyView;
import com.mtn.repository.StoreSurveyRepository;
import com.mtn.validators.StoreSurveyValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class StoreSurveyService extends StoreChildService<StoreSurvey, StoreSurveyView> {

	@Autowired
	public StoreSurveyService(SecurityService securityService,
							  StoreSurveyRepository repository,
							  StoreSurveyValidator validator) {
		super(securityService, repository, validator, StoreSurvey::new);
	}

	@Transactional
	public StoreSurvey addOne(StoreSurvey storeCasing) {
		UserProfile currentUser = securityService.getCurrentUser();
		storeCasing.setCreatedBy(currentUser);
		storeCasing.setUpdatedBy(currentUser);
		return storeCasing;
	}

	@Transactional
	public StoreSurvey getCloneOfLatestForStore(Store store, LocalDateTime dateTime) {
		// If the store has a latest non-deleted survey, clone it, otherwise create a brand new one
		StoreSurvey survey = StoreUtil.getLatestSurveyAsOfDateTime(store, LocalDateTime.now())
				.map(StoreSurvey::new).orElseGet(StoreSurvey::new);
		survey.setSurveyDate(dateTime);

		UserProfile currentUser = securityService.getCurrentUser();
		survey.setCreatedBy(currentUser);
		survey.setUpdatedBy(currentUser);
		survey.setStore(store);

		return survey;
	}

	@Transactional
	public StoreSurvey addOneToStore(StoreSurveyView request, Store store) {
		this.validator.validateForInsert(request);

		UserProfile currentUser = this.securityService.getCurrentUser();

		StoreSurvey survey = createNewEntityFromRequest(request);
		survey.setCreatedBy(currentUser);
		survey.setUpdatedBy(currentUser);

		survey.setStore(store);

		return this.repository.save(survey);
	}

	@Override
	protected void setEntityAttributesFromRequest(StoreSurvey survey, StoreSurveyView request) {
		survey.setSurveyDate(request.getSurveyDate());
		survey.setNote(request.getNote());
		survey.setRegisterCountNormal(request.getRegisterCountNormal());
		survey.setRegisterCountExpress(request.getRegisterCountExpress());
		survey.setRegisterCountSelfCheckout(request.getRegisterCountSelfCheckout());
		survey.setFuelDispenserCount(request.getFuelDispenserCount());
		survey.setFuelIsOpen24(request.getFuelIsOpen24());
		survey.setPharmacyIsOpen24(request.getPharmacyIsOpen24());
		survey.setPharmacyHasDriveThrough(request.getPharmacyHasDriveThrough());
		survey.setPharmacyScriptsWeekly(request.getPharmacyScriptsWeekly());
		survey.setPharmacyAvgDollarsPerScript(request.getPharmacyAvgDollarsPerScript());
		survey.setPharmacyPharmacistCount(request.getPharmacyPharmacistCount());
		survey.setPharmacyTechnicianCount(request.getPharmacyTechnicianCount());
		survey.setDepartmentBakery(request.getDepartmentBakery());
		survey.setDepartmentBank(request.getDepartmentBank());
		survey.setDepartmentBeer(request.getDepartmentBeer());
		survey.setDepartmentBulk(request.getDepartmentBulk());
		survey.setDepartmentCheese(request.getDepartmentCheese());
		survey.setDepartmentCoffee(request.getDepartmentCoffee());
		survey.setDepartmentDeli(request.getDepartmentDeli());
		survey.setDepartmentExpandedGm(request.getDepartmentExpandedGm());
		survey.setDepartmentExtensivePreparedFoods(request.getDepartmentExtensivePreparedFoods());
		survey.setDepartmentFloral(request.getDepartmentFloral());
		survey.setDepartmentFuel(request.getDepartmentFuel());
		survey.setDepartmentHotBar(request.getDepartmentHotBar());
		survey.setDepartmentInStoreRestaurant(request.getDepartmentInStoreRestaurant());
		survey.setDepartmentLiquor(request.getDepartmentLiquor());
		survey.setDepartmentMeat(request.getDepartmentMeat());
		survey.setDepartmentNatural(request.getDepartmentNatural());
		survey.setDepartmentOliveBar(request.getDepartmentOliveBar());
		survey.setDepartmentOnlinePickup(request.getDepartmentOnlinePickup());
		survey.setDepartmentPharmacy(request.getDepartmentPharmacy());
		survey.setDepartmentPreparedFoods(request.getDepartmentPreparedFoods());
		survey.setDepartmentSaladBar(request.getDepartmentSaladBar());
		survey.setDepartmentSeafood(request.getDepartmentSeafood());
		survey.setDepartmentSeating(request.getDepartmentSeating());
		survey.setDepartmentSushi(request.getDepartmentSushi());
		survey.setDepartmentWine(request.getDepartmentWine());
		survey.setAccessibilityFarthestFromEntrance(request.getAccessibilityFarthestFromEntrance());
		survey.setAccessibilityMainIntersectionHasTrafficLight(request.getAccessibilityMainIntersectionHasTrafficLight());
		survey.setAccessibilityMultipleRetailersBeforeSite(request.getAccessibilityMultipleRetailersBeforeSite());
		survey.setAccessibilitySetBackTwiceParkingLength(request.getAccessibilitySetBackTwiceParkingLength());
		survey.setAccessibilityRating(request.getAccessibilityRating());
		survey.setParkingOutparcelsInterfereWithParking(request.getParkingOutparcelsInterfereWithParking());
		survey.setParkingDirectAccessToParking(request.getParkingDirectAccessToParking());
		survey.setParkingSmallParkingField(request.getParkingSmallParkingField());
		survey.setParkingHasTSpaces(request.getParkingHasTSpaces());
		survey.setParkingHasAngledSpaces(request.getParkingHasAngledSpaces());
		survey.setParkingHasParkingHog(request.getParkingHasParkingHog());
		survey.setParkingIsPoorlyLit(request.getParkingIsPoorlyLit());
		survey.setParkingRating(request.getParkingRating());
		survey.setParkingSpaceCount(request.getParkingSpaceCount());
		survey.setVisibilityHillDepressionBlocksView(request.getVisibilityHillDepressionBlocksView());
		survey.setVisibilityOutparcelsBlockView(request.getVisibilityOutparcelsBlockView());
		survey.setVisibilitySignOnMain(request.getVisibilitySignOnMain());
		survey.setVisibilityStoreFacesMainRoad(request.getVisibilityStoreFacesMainRoad());
		survey.setVisibilityTreesBlockView(request.getVisibilityTreesBlockView());
		survey.setVisibilityRating(request.getVisibilityRating());
		survey.setSeasonalityJan(request.getSeasonalityJan());
		survey.setSeasonalityFeb(request.getSeasonalityFeb());
		survey.setSeasonalityMar(request.getSeasonalityMar());
		survey.setSeasonalityApr(request.getSeasonalityApr());
		survey.setSeasonalityMay(request.getSeasonalityMay());
		survey.setSeasonalityJun(request.getSeasonalityJun());
		survey.setSeasonalityJul(request.getSeasonalityJul());
		survey.setSeasonalityAug(request.getSeasonalityAug());
		survey.setSeasonalitySep(request.getSeasonalitySep());
		survey.setSeasonalityOct(request.getSeasonalityOct());
		survey.setSeasonalityNov(request.getSeasonalityNov());
		survey.setSeasonalityDec(request.getSeasonalityDec());
		survey.setSeasonalityNotes(request.getSeasonalityNotes());
	}

	@Override
	public void handleAssociationsOnDeletion(StoreSurvey existing) {
		// Do nothing
	}
}

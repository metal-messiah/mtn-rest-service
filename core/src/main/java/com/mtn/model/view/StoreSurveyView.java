package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.constant.RatingType;
import com.mtn.model.domain.StoreSurvey;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StoreSurveyView extends AuditingEntityView {

    private LocalDateTime surveyDate;
    private String fit;
    private String format;
    private Integer areaSales;
    private Double areaSalesPercentOfTotal;
    private Integer areaTotal;
    private Boolean areaIsEstimate;
    private String note;
    private Boolean storeIsOpen24;
    private Boolean naturalFoodsAreIntegrated;
    private Integer registerCountNormal;
    private Integer registerCountExpress;
    private Integer registerCountSelfCheckout;
    private Integer fuelDispenserCount;
    private Boolean fuelIsOpen24;
    private Boolean pharmacyIsOpen24;
    private Boolean pharmacyHasDriveThrough;
    private Boolean departmentBakery;
    private Boolean departmentBank;
    private Boolean departmentBeer;
    private Boolean departmentBulk;
    private Boolean departmentCheese;
    private Boolean departmentCoffee;
    private Boolean departmentDeli;
    private Boolean departmentExpandedGm;
    private Boolean departmentExtensivePreparedFoods;
    private Boolean departmentFloral;
    private Boolean departmentFuel;
    private Boolean departmentHotBar;
    private Boolean departmentInStoreRestaurant;
    private Boolean departmentLiquor;
    private Boolean departmentMeat;
    private Boolean departmentNatural;
    private Boolean departmentOliveBar;
    private Boolean departmentOnlinePickup;
    private Boolean departmentPharmacy;
    private Boolean departmentPreparedFoods;
    private Boolean departmentSaladBar;
    private Boolean departmentSeafood;
    private Boolean departmentSeating;
    private Boolean departmentSushi;
    private Boolean departmentWine;
    private Boolean accessibilityFarthestFromEntrance;
    private Boolean accessibilityMainIntersectionHasTrafficLight;
    private Boolean accessibilityMainIntersectionNeedsTrafficLight;
    private Boolean accessibilityMultipleRetailersBeforeSite;
    private Boolean accessibilitySetBackTwiceParkingLength;
    private RatingType accessibilityRating;
    private Boolean parkingOutparcelsInterfereWithParking;
    private Boolean parkingDirectAccessToParking;
    private Boolean parkingSmallParkingField;
    private Boolean parkingHasTSpaces;
    private Boolean parkingHasAngledSpaces;
    private Boolean parkingHasParkingHog;
    private Boolean parkingIsPoorlyLit;
    private RatingType parkingRating;
    private Integer parkingSpaceCount;
    private Boolean visibilityHillDepressionBlocksView;
    private Boolean visibilityOutparcelsBlockView;
    private Boolean visibilitySignOnMain;
    private Boolean visibilityStoreFacesMainRoad;
    private Boolean visibilityTreesBlockView;
    private RatingType visibilityRating;
    private Integer seasonalityJan;
    private Integer seasonalityFeb;
    private Integer seasonalityMar;
    private Integer seasonalityApr;
    private Integer seasonalityMay;
    private Integer seasonalityJun;
    private Integer seasonalityJul;
    private Integer seasonalityAug;
    private Integer seasonalitySep;
    private Integer seasonalityOct;
    private Integer seasonalityNov;
    private Integer seasonalityDec;
    private Integer legacyCasingId;
    private String seasonalityNotes;

    public StoreSurveyView() {
    }

    public StoreSurveyView(StoreSurvey storeSurvey) {
        super(storeSurvey);

        this.surveyDate = storeSurvey.getSurveyDate();
        this.fit = storeSurvey.getFit();
        this.format = storeSurvey.getFormat();
        this.areaSales = storeSurvey.getAreaSales();
        this.areaSalesPercentOfTotal = storeSurvey.getAreaSalesPercentOfTotal();
        this.areaTotal = storeSurvey.getAreaTotal();
        this.areaIsEstimate = storeSurvey.getAreaIsEstimate();
        this.note = storeSurvey.getNote();
        this.storeIsOpen24 = storeSurvey.getStoreIsOpen24();
        this.naturalFoodsAreIntegrated = storeSurvey.getNaturalFoodsAreIntegrated();
        this.registerCountNormal = storeSurvey.getRegisterCountNormal();
        this.registerCountExpress = storeSurvey.getRegisterCountExpress();
        this.registerCountSelfCheckout = storeSurvey.getRegisterCountSelfCheckout();
        this.fuelDispenserCount = storeSurvey.getFuelDispenserCount();
        this.fuelIsOpen24 = storeSurvey.getFuelIsOpen24();
        this.pharmacyIsOpen24 = storeSurvey.getPharmacyIsOpen24();
        this.pharmacyHasDriveThrough = storeSurvey.getPharmacyHasDriveThrough();
        this.departmentBakery = storeSurvey.getDepartmentBakery();
        this.departmentBank = storeSurvey.getDepartmentBank();
        this.departmentBeer = storeSurvey.getDepartmentBeer();
        this.departmentBulk = storeSurvey.getDepartmentBulk();
        this.departmentCheese = storeSurvey.getDepartmentCheese();
        this.departmentCoffee = storeSurvey.getDepartmentCoffee();
        this.departmentDeli = storeSurvey.getDepartmentDeli();
        this.departmentExpandedGm = storeSurvey.getDepartmentExpandedGm();
        this.departmentExtensivePreparedFoods = storeSurvey.getDepartmentExtensivePreparedFoods();
        this.departmentFloral = storeSurvey.getDepartmentFloral();
        this.departmentFuel = storeSurvey.getDepartmentFuel();
        this.departmentHotBar = storeSurvey.getDepartmentHotBar();
        this.departmentInStoreRestaurant = storeSurvey.getDepartmentInStoreRestaurant();
        this.departmentLiquor = storeSurvey.getDepartmentLiquor();
        this.departmentMeat = storeSurvey.getDepartmentMeat();
        this.departmentNatural = storeSurvey.getDepartmentNatural();
        this.departmentOliveBar = storeSurvey.getDepartmentOliveBar();
        this.departmentOnlinePickup = storeSurvey.getDepartmentOnlinePickup();
        this.departmentPharmacy = storeSurvey.getDepartmentPharmacy();
        this.departmentPreparedFoods = storeSurvey.getDepartmentPreparedFoods();
        this.departmentSaladBar = storeSurvey.getDepartmentSaladBar();
        this.departmentSeafood = storeSurvey.getDepartmentSeafood();
        this.departmentSeating = storeSurvey.getDepartmentSeating();
        this.departmentSushi = storeSurvey.getDepartmentSushi();
        this.departmentWine = storeSurvey.getDepartmentWine();
        this.accessibilityFarthestFromEntrance = storeSurvey.getAccessibilityFarthestFromEntrance();
        this.accessibilityMainIntersectionHasTrafficLight = storeSurvey.getAccessibilityMainIntersectionHasTrafficLight();
        this.accessibilityMainIntersectionNeedsTrafficLight = storeSurvey.getAccessibilityMainIntersectionNeedsTrafficLight();
        this.accessibilityMultipleRetailersBeforeSite = storeSurvey.getAccessibilityMultipleRetailersBeforeSite();
        this.accessibilitySetBackTwiceParkingLength = storeSurvey.getAccessibilitySetBackTwiceParkingLength();
        this.accessibilityRating = storeSurvey.getAccessibilityRating();
        this.parkingOutparcelsInterfereWithParking = storeSurvey.getParkingOutparcelsInterfereWithParking();
        this.parkingDirectAccessToParking = storeSurvey.getParkingDirectAccessToParking();
        this.parkingSmallParkingField = storeSurvey.getParkingSmallParkingField();
        this.parkingHasTSpaces = storeSurvey.getParkingHasTSpaces();
        this.parkingHasAngledSpaces = storeSurvey.getParkingHasAngledSpaces();
        this.parkingHasParkingHog = storeSurvey.getParkingHasParkingHog();
        this.parkingIsPoorlyLit = storeSurvey.getParkingIsPoorlyLit();
        this.parkingRating = storeSurvey.getParkingRating();
        this.parkingSpaceCount = storeSurvey.getParkingSpaceCount();
        this.visibilityHillDepressionBlocksView = storeSurvey.getVisibilityHillDepressionBlocksView();
        this.visibilityOutparcelsBlockView = storeSurvey.getVisibilityOutparcelsBlockView();
        this.visibilitySignOnMain = storeSurvey.getVisibilitySignOnMain();
        this.visibilityStoreFacesMainRoad = storeSurvey.getVisibilityStoreFacesMainRoad();
        this.visibilityTreesBlockView = storeSurvey.getVisibilityTreesBlockView();
        this.visibilityRating = storeSurvey.getVisibilityRating();
        this.seasonalityJan = storeSurvey.getSeasonalityJan();
        this.seasonalityFeb = storeSurvey.getSeasonalityFeb();
        this.seasonalityMar = storeSurvey.getSeasonalityMar();
        this.seasonalityApr = storeSurvey.getSeasonalityApr();
        this.seasonalityMay = storeSurvey.getSeasonalityMay();
        this.seasonalityJun = storeSurvey.getSeasonalityJun();
        this.seasonalityJul = storeSurvey.getSeasonalityJul();
        this.seasonalityAug = storeSurvey.getSeasonalityAug();
        this.seasonalitySep = storeSurvey.getSeasonalitySep();
        this.seasonalityOct = storeSurvey.getSeasonalityOct();
        this.seasonalityNov = storeSurvey.getSeasonalityNov();
        this.seasonalityDec = storeSurvey.getSeasonalityDec();
        this.legacyCasingId = storeSurvey.getLegacyCasingId();
        this.seasonalityNotes = storeSurvey.getSeasonalityNotes();
    }

    public LocalDateTime getSurveyDate() {
        return surveyDate;
    }

    public void setSurveyDate(LocalDateTime surveyDate) {
        this.surveyDate = surveyDate;
    }

    public String getFit() {
        return fit;
    }

    public void setFit(String fit) {
        this.fit = fit;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Integer getAreaSales() {
        return areaSales;
    }

    public void setAreaSales(Integer areaSales) {
        this.areaSales = areaSales;
    }

    public Double getAreaSalesPercentOfTotal() {
        return areaSalesPercentOfTotal;
    }

    public void setAreaSalesPercentOfTotal(Double areaSalesPercentOfTotal) {
        this.areaSalesPercentOfTotal = areaSalesPercentOfTotal;
    }

    public Integer getAreaTotal() {
        return areaTotal;
    }

    public void setAreaTotal(Integer areaTotal) {
        this.areaTotal = areaTotal;
    }

    public Boolean getAreaIsEstimate() {
        return areaIsEstimate;
    }

    public void setAreaIsEstimate(Boolean areaIsEstimate) {
        this.areaIsEstimate = areaIsEstimate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Boolean getStoreIsOpen24() {
        return storeIsOpen24;
    }

    public void setStoreIsOpen24(Boolean storeIsOpen24) {
        this.storeIsOpen24 = storeIsOpen24;
    }

    public Boolean getNaturalFoodsAreIntegrated() {
        return naturalFoodsAreIntegrated;
    }

    public void setNaturalFoodsAreIntegrated(Boolean naturalFoodsAreIntegrated) {
        this.naturalFoodsAreIntegrated = naturalFoodsAreIntegrated;
    }

    public Integer getRegisterCountNormal() {
        return registerCountNormal;
    }

    public void setRegisterCountNormal(Integer registerCountNormal) {
        this.registerCountNormal = registerCountNormal;
    }

    public Integer getRegisterCountExpress() {
        return registerCountExpress;
    }

    public void setRegisterCountExpress(Integer registerCountExpress) {
        this.registerCountExpress = registerCountExpress;
    }

    public Integer getRegisterCountSelfCheckout() {
        return registerCountSelfCheckout;
    }

    public void setRegisterCountSelfCheckout(Integer registerCountSelfCheckout) {
        this.registerCountSelfCheckout = registerCountSelfCheckout;
    }

    public Integer getFuelDispenserCount() {
        return fuelDispenserCount;
    }

    public void setFuelDispenserCount(Integer fuelDispenserCount) {
        this.fuelDispenserCount = fuelDispenserCount;
    }

    public Boolean getFuelIsOpen24() {
        return fuelIsOpen24;
    }

    public void setFuelIsOpen24(Boolean fuelIsOpen24) {
        this.fuelIsOpen24 = fuelIsOpen24;
    }

    public Boolean getPharmacyIsOpen24() {
        return pharmacyIsOpen24;
    }

    public void setPharmacyIsOpen24(Boolean pharmacyIsOpen24) {
        this.pharmacyIsOpen24 = pharmacyIsOpen24;
    }

    public Boolean getPharmacyHasDriveThrough() {
        return pharmacyHasDriveThrough;
    }

    public void setPharmacyHasDriveThrough(Boolean pharmacyHasDriveThrough) {
        this.pharmacyHasDriveThrough = pharmacyHasDriveThrough;
    }

    public Boolean getDepartmentBakery() {
        return departmentBakery;
    }

    public void setDepartmentBakery(Boolean departmentBakery) {
        this.departmentBakery = departmentBakery;
    }

    public Boolean getDepartmentBank() {
        return departmentBank;
    }

    public void setDepartmentBank(Boolean departmentBank) {
        this.departmentBank = departmentBank;
    }

    public Boolean getDepartmentBeer() {
        return departmentBeer;
    }

    public void setDepartmentBeer(Boolean departmentBeer) {
        this.departmentBeer = departmentBeer;
    }

    public Boolean getDepartmentBulk() {
        return departmentBulk;
    }

    public void setDepartmentBulk(Boolean departmentBulk) {
        this.departmentBulk = departmentBulk;
    }

    public Boolean getDepartmentCheese() {
        return departmentCheese;
    }

    public void setDepartmentCheese(Boolean departmentCheese) {
        this.departmentCheese = departmentCheese;
    }

    public Boolean getDepartmentCoffee() {
        return departmentCoffee;
    }

    public void setDepartmentCoffee(Boolean departmentCoffee) {
        this.departmentCoffee = departmentCoffee;
    }

    public Boolean getDepartmentDeli() {
        return departmentDeli;
    }

    public void setDepartmentDeli(Boolean departmentDeli) {
        this.departmentDeli = departmentDeli;
    }

    public Boolean getDepartmentExpandedGm() {
        return departmentExpandedGm;
    }

    public void setDepartmentExpandedGm(Boolean departmentExpandedGm) {
        this.departmentExpandedGm = departmentExpandedGm;
    }

    public Boolean getDepartmentExtensivePreparedFoods() {
        return departmentExtensivePreparedFoods;
    }

    public void setDepartmentExtensivePreparedFoods(Boolean departmentExtensivePreparedFoods) {
        this.departmentExtensivePreparedFoods = departmentExtensivePreparedFoods;
    }

    public Boolean getDepartmentFloral() {
        return departmentFloral;
    }

    public void setDepartmentFloral(Boolean departmentFloral) {
        this.departmentFloral = departmentFloral;
    }

    public Boolean getDepartmentFuel() {
        return departmentFuel;
    }

    public void setDepartmentFuel(Boolean departmentFuel) {
        this.departmentFuel = departmentFuel;
    }

    public Boolean getDepartmentHotBar() {
        return departmentHotBar;
    }

    public void setDepartmentHotBar(Boolean departmentHotBar) {
        this.departmentHotBar = departmentHotBar;
    }

    public Boolean getDepartmentInStoreRestaurant() {
        return departmentInStoreRestaurant;
    }

    public void setDepartmentInStoreRestaurant(Boolean departmentInStoreRestaurant) {
        this.departmentInStoreRestaurant = departmentInStoreRestaurant;
    }

    public Boolean getDepartmentLiquor() {
        return departmentLiquor;
    }

    public void setDepartmentLiquor(Boolean departmentLiquor) {
        this.departmentLiquor = departmentLiquor;
    }

    public Boolean getDepartmentMeat() {
        return departmentMeat;
    }

    public void setDepartmentMeat(Boolean departmentMeat) {
        this.departmentMeat = departmentMeat;
    }

    public Boolean getDepartmentNatural() {
        return departmentNatural;
    }

    public void setDepartmentNatural(Boolean departmentNatural) {
        this.departmentNatural = departmentNatural;
    }

    public Boolean getDepartmentOliveBar() {
        return departmentOliveBar;
    }

    public void setDepartmentOliveBar(Boolean departmentOliveBar) {
        this.departmentOliveBar = departmentOliveBar;
    }

    public Boolean getDepartmentOnlinePickup() {
        return departmentOnlinePickup;
    }

    public void setDepartmentOnlinePickup(Boolean departmentOnlinePickup) {
        this.departmentOnlinePickup = departmentOnlinePickup;
    }

    public Boolean getDepartmentPharmacy() {
        return departmentPharmacy;
    }

    public void setDepartmentPharmacy(Boolean departmentPharmacy) {
        this.departmentPharmacy = departmentPharmacy;
    }

    public Boolean getDepartmentPreparedFoods() {
        return departmentPreparedFoods;
    }

    public void setDepartmentPreparedFoods(Boolean departmentPreparedFoods) {
        this.departmentPreparedFoods = departmentPreparedFoods;
    }

    public Boolean getDepartmentSaladBar() {
        return departmentSaladBar;
    }

    public void setDepartmentSaladBar(Boolean departmentSaladBar) {
        this.departmentSaladBar = departmentSaladBar;
    }

    public Boolean getDepartmentSeafood() {
        return departmentSeafood;
    }

    public void setDepartmentSeafood(Boolean departmentSeafood) {
        this.departmentSeafood = departmentSeafood;
    }

    public Boolean getDepartmentSeating() {
        return departmentSeating;
    }

    public void setDepartmentSeating(Boolean departmentSeating) {
        this.departmentSeating = departmentSeating;
    }

    public Boolean getDepartmentSushi() {
        return departmentSushi;
    }

    public void setDepartmentSushi(Boolean departmentSushi) {
        this.departmentSushi = departmentSushi;
    }

    public Boolean getDepartmentWine() {
        return departmentWine;
    }

    public void setDepartmentWine(Boolean departmentWine) {
        this.departmentWine = departmentWine;
    }

    public Boolean getAccessibilityFarthestFromEntrance() {
        return accessibilityFarthestFromEntrance;
    }

    public void setAccessibilityFarthestFromEntrance(Boolean accessibilityFarthestFromEntrance) {
        this.accessibilityFarthestFromEntrance = accessibilityFarthestFromEntrance;
    }

    public Boolean getAccessibilityMainIntersectionHasTrafficLight() {
        return accessibilityMainIntersectionHasTrafficLight;
    }

    public void setAccessibilityMainIntersectionHasTrafficLight(Boolean accessibilityMainIntersectionHasTrafficLight) {
        this.accessibilityMainIntersectionHasTrafficLight = accessibilityMainIntersectionHasTrafficLight;
    }

    public Boolean getAccessibilityMainIntersectionNeedsTrafficLight() {
        return accessibilityMainIntersectionNeedsTrafficLight;
    }

    public void setAccessibilityMainIntersectionNeedsTrafficLight(Boolean accessibilityMainIntersectionNeedsTrafficLight) {
        this.accessibilityMainIntersectionNeedsTrafficLight = accessibilityMainIntersectionNeedsTrafficLight;
    }

    public Boolean getAccessibilityMultipleRetailersBeforeSite() {
        return accessibilityMultipleRetailersBeforeSite;
    }

    public void setAccessibilityMultipleRetailersBeforeSite(Boolean accessibilityMultipleRetailersBeforeSite) {
        this.accessibilityMultipleRetailersBeforeSite = accessibilityMultipleRetailersBeforeSite;
    }

    public Boolean getAccessibilitySetBackTwiceParkingLength() {
        return accessibilitySetBackTwiceParkingLength;
    }

    public void setAccessibilitySetBackTwiceParkingLength(Boolean accessibilitySetBackTwiceParkingLength) {
        this.accessibilitySetBackTwiceParkingLength = accessibilitySetBackTwiceParkingLength;
    }

    public RatingType getAccessibilityRating() {
        return accessibilityRating;
    }

    public void setAccessibilityRating(RatingType accessibilityRating) {
        this.accessibilityRating = accessibilityRating;
    }

    public Boolean getParkingOutparcelsInterfereWithParking() {
        return parkingOutparcelsInterfereWithParking;
    }

    public void setParkingOutparcelsInterfereWithParking(Boolean parkingOutparcelsInterfereWithParking) {
        this.parkingOutparcelsInterfereWithParking = parkingOutparcelsInterfereWithParking;
    }

    public Boolean getParkingDirectAccessToParking() {
        return parkingDirectAccessToParking;
    }

    public void setParkingDirectAccessToParking(Boolean parkingDirectAccessToParking) {
        this.parkingDirectAccessToParking = parkingDirectAccessToParking;
    }

    public Boolean getParkingSmallParkingField() {
        return parkingSmallParkingField;
    }

    public void setParkingSmallParkingField(Boolean parkingSmallParkingField) {
        this.parkingSmallParkingField = parkingSmallParkingField;
    }

    public Boolean getParkingHasTSpaces() {
        return parkingHasTSpaces;
    }

    public void setParkingHasTSpaces(Boolean parkingHasTSpaces) {
        this.parkingHasTSpaces = parkingHasTSpaces;
    }

    public Boolean getParkingHasAngledSpaces() {
        return parkingHasAngledSpaces;
    }

    public void setParkingHasAngledSpaces(Boolean parkingHasAngledSpaces) {
        this.parkingHasAngledSpaces = parkingHasAngledSpaces;
    }

    public Boolean getParkingHasParkingHog() {
        return parkingHasParkingHog;
    }

    public void setParkingHasParkingHog(Boolean parkingHasParkingHog) {
        this.parkingHasParkingHog = parkingHasParkingHog;
    }

    public Boolean getParkingIsPoorlyLit() {
        return parkingIsPoorlyLit;
    }

    public void setParkingIsPoorlyLit(Boolean parkingIsPoorlyLit) {
        this.parkingIsPoorlyLit = parkingIsPoorlyLit;
    }

    public RatingType getParkingRating() {
        return parkingRating;
    }

    public void setParkingRating(RatingType parkingRating) {
        this.parkingRating = parkingRating;
    }

    public Integer getParkingSpaceCount() {
        return parkingSpaceCount;
    }

    public void setParkingSpaceCount(Integer parkingSpaceCount) {
        this.parkingSpaceCount = parkingSpaceCount;
    }

    public Boolean getVisibilityHillDepressionBlocksView() {
        return visibilityHillDepressionBlocksView;
    }

    public void setVisibilityHillDepressionBlocksView(Boolean visibilityHillDepressionBlocksView) {
        this.visibilityHillDepressionBlocksView = visibilityHillDepressionBlocksView;
    }

    public Boolean getVisibilityOutparcelsBlockView() {
        return visibilityOutparcelsBlockView;
    }

    public void setVisibilityOutparcelsBlockView(Boolean visibilityOutparcelsBlockView) {
        this.visibilityOutparcelsBlockView = visibilityOutparcelsBlockView;
    }

    public Boolean getVisibilitySignOnMain() {
        return visibilitySignOnMain;
    }

    public void setVisibilitySignOnMain(Boolean visibilitySignOnMain) {
        this.visibilitySignOnMain = visibilitySignOnMain;
    }

    public Boolean getVisibilityStoreFacesMainRoad() {
        return visibilityStoreFacesMainRoad;
    }

    public void setVisibilityStoreFacesMainRoad(Boolean visibilityStoreFacesMainRoad) {
        this.visibilityStoreFacesMainRoad = visibilityStoreFacesMainRoad;
    }

    public Boolean getVisibilityTreesBlockView() {
        return visibilityTreesBlockView;
    }

    public void setVisibilityTreesBlockView(Boolean visibilityTreesBlockView) {
        this.visibilityTreesBlockView = visibilityTreesBlockView;
    }

    public RatingType getVisibilityRating() {
        return visibilityRating;
    }

    public void setVisibilityRating(RatingType visibilityRating) {
        this.visibilityRating = visibilityRating;
    }

    public Integer getSeasonalityJan() {
        return seasonalityJan;
    }

    public void setSeasonalityJan(Integer seasonalityJan) {
        this.seasonalityJan = seasonalityJan;
    }

    public Integer getSeasonalityFeb() {
        return seasonalityFeb;
    }

    public void setSeasonalityFeb(Integer seasonalityFeb) {
        this.seasonalityFeb = seasonalityFeb;
    }

    public Integer getSeasonalityMar() {
        return seasonalityMar;
    }

    public void setSeasonalityMar(Integer seasonalityMar) {
        this.seasonalityMar = seasonalityMar;
    }

    public Integer getSeasonalityApr() {
        return seasonalityApr;
    }

    public void setSeasonalityApr(Integer seasonalityApr) {
        this.seasonalityApr = seasonalityApr;
    }

    public Integer getSeasonalityMay() {
        return seasonalityMay;
    }

    public void setSeasonalityMay(Integer seasonalityMay) {
        this.seasonalityMay = seasonalityMay;
    }

    public Integer getSeasonalityJun() {
        return seasonalityJun;
    }

    public void setSeasonalityJun(Integer seasonalityJun) {
        this.seasonalityJun = seasonalityJun;
    }

    public Integer getSeasonalityJul() {
        return seasonalityJul;
    }

    public void setSeasonalityJul(Integer seasonalityJul) {
        this.seasonalityJul = seasonalityJul;
    }

    public Integer getSeasonalityAug() {
        return seasonalityAug;
    }

    public void setSeasonalityAug(Integer seasonalityAug) {
        this.seasonalityAug = seasonalityAug;
    }

    public Integer getSeasonalitySep() {
        return seasonalitySep;
    }

    public void setSeasonalitySep(Integer seasonalitySep) {
        this.seasonalitySep = seasonalitySep;
    }

    public Integer getSeasonalityOct() {
        return seasonalityOct;
    }

    public void setSeasonalityOct(Integer seasonalityOct) {
        this.seasonalityOct = seasonalityOct;
    }

    public Integer getSeasonalityNov() {
        return seasonalityNov;
    }

    public void setSeasonalityNov(Integer seasonalityNov) {
        this.seasonalityNov = seasonalityNov;
    }

    public Integer getSeasonalityDec() {
        return seasonalityDec;
    }

    public void setSeasonalityDec(Integer seasonalityDec) {
        this.seasonalityDec = seasonalityDec;
    }

    public Integer getLegacyCasingId() {
        return legacyCasingId;
    }

    public void setLegacyCasingId(Integer legacyCasingId) {
        this.legacyCasingId = legacyCasingId;
    }

    public String getSeasonalityNotes() {
        return seasonalityNotes;
    }

    public void setSeasonalityNotes(String seasonalityNotes) {
        this.seasonalityNotes = seasonalityNotes;
    }
}

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

    public StoreSurveyView(StoreSurvey storeSurvey) {
        super(storeSurvey);

        this.id = storeSurvey.getId();
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

    public String getFit() {
        return fit;
    }

    public String getFormat() {
        return format;
    }

    public Integer getAreaSales() {
        return areaSales;
    }

    public Double getAreaSalesPercentOfTotal() {
        return areaSalesPercentOfTotal;
    }

    public Integer getAreaTotal() {
        return areaTotal;
    }

    public Boolean getAreaIsEstimate() {
        return areaIsEstimate;
    }

    public String getNote() {
        return note;
    }

    public Boolean getStoreIsOpen24() {
        return storeIsOpen24;
    }

    public Boolean getNaturalFoodsAreIntegrated() {
        return naturalFoodsAreIntegrated;
    }

    public Integer getRegisterCountNormal() {
        return registerCountNormal;
    }

    public Integer getRegisterCountExpress() {
        return registerCountExpress;
    }

    public Integer getRegisterCountSelfCheckout() {
        return registerCountSelfCheckout;
    }

    public Integer getFuelDispenserCount() {
        return fuelDispenserCount;
    }

    public Boolean getFuelIsOpen24() {
        return fuelIsOpen24;
    }

    public Boolean getPharmacyIsOpen24() {
        return pharmacyIsOpen24;
    }

    public Boolean getPharmacyHasDriveThrough() {
        return pharmacyHasDriveThrough;
    }

    public Boolean getDepartmentBakery() {
        return departmentBakery;
    }

    public Boolean getDepartmentBank() {
        return departmentBank;
    }

    public Boolean getDepartmentBeer() {
        return departmentBeer;
    }

    public Boolean getDepartmentBulk() {
        return departmentBulk;
    }

    public Boolean getDepartmentCheese() {
        return departmentCheese;
    }

    public Boolean getDepartmentCoffee() {
        return departmentCoffee;
    }

    public Boolean getDepartmentDeli() {
        return departmentDeli;
    }

    public Boolean getDepartmentExpandedGm() {
        return departmentExpandedGm;
    }

    public Boolean getDepartmentExtensivePreparedFoods() {
        return departmentExtensivePreparedFoods;
    }

    public Boolean getDepartmentFloral() {
        return departmentFloral;
    }

    public Boolean getDepartmentFuel() {
        return departmentFuel;
    }

    public Boolean getDepartmentHotBar() {
        return departmentHotBar;
    }

    public Boolean getDepartmentInStoreRestaurant() {
        return departmentInStoreRestaurant;
    }

    public Boolean getDepartmentLiquor() {
        return departmentLiquor;
    }

    public Boolean getDepartmentMeat() {
        return departmentMeat;
    }

    public Boolean getDepartmentNatural() {
        return departmentNatural;
    }

    public Boolean getDepartmentOliveBar() {
        return departmentOliveBar;
    }

    public Boolean getDepartmentOnlinePickup() {
        return departmentOnlinePickup;
    }

    public Boolean getDepartmentPharmacy() {
        return departmentPharmacy;
    }

    public Boolean getDepartmentPreparedFoods() {
        return departmentPreparedFoods;
    }

    public Boolean getDepartmentSaladBar() {
        return departmentSaladBar;
    }

    public Boolean getDepartmentSeafood() {
        return departmentSeafood;
    }

    public Boolean getDepartmentSeating() {
        return departmentSeating;
    }

    public Boolean getDepartmentSushi() {
        return departmentSushi;
    }

    public Boolean getDepartmentWine() {
        return departmentWine;
    }

    public Boolean getAccessibilityFarthestFromEntrance() {
        return accessibilityFarthestFromEntrance;
    }

    public Boolean getAccessibilityMainIntersectionHasTrafficLight() {
        return accessibilityMainIntersectionHasTrafficLight;
    }

    public Boolean getAccessibilityMainIntersectionNeedsTrafficLight() {
        return accessibilityMainIntersectionNeedsTrafficLight;
    }

    public Boolean getAccessibilityMultipleRetailersBeforeSite() {
        return accessibilityMultipleRetailersBeforeSite;
    }

    public Boolean getAccessibilitySetBackTwiceParkingLength() {
        return accessibilitySetBackTwiceParkingLength;
    }

    public RatingType getAccessibilityRating() {
        return accessibilityRating;
    }

    public Boolean getParkingOutparcelsInterfereWithParking() {
        return parkingOutparcelsInterfereWithParking;
    }

    public Boolean getParkingDirectAccessToParking() {
        return parkingDirectAccessToParking;
    }

    public Boolean getParkingSmallParkingField() {
        return parkingSmallParkingField;
    }

    public Boolean getParkingHasTSpaces() {
        return parkingHasTSpaces;
    }

    public Boolean getParkingHasAngledSpaces() {
        return parkingHasAngledSpaces;
    }

    public Boolean getParkingHasParkingHog() {
        return parkingHasParkingHog;
    }

    public Boolean getParkingIsPoorlyLit() {
        return parkingIsPoorlyLit;
    }

    public RatingType getParkingRating() {
        return parkingRating;
    }

    public Integer getParkingSpaceCount() {
        return parkingSpaceCount;
    }

    public Boolean getVisibilityHillDepressionBlocksView() {
        return visibilityHillDepressionBlocksView;
    }

    public Boolean getVisibilityOutparcelsBlockView() {
        return visibilityOutparcelsBlockView;
    }

    public Boolean getVisibilitySignOnMain() {
        return visibilitySignOnMain;
    }

    public Boolean getVisibilityStoreFacesMainRoad() {
        return visibilityStoreFacesMainRoad;
    }

    public Boolean getVisibilityTreesBlockView() {
        return visibilityTreesBlockView;
    }

    public RatingType getVisibilityRating() {
        return visibilityRating;
    }

    public Integer getSeasonalityJan() {
        return seasonalityJan;
    }

    public Integer getSeasonalityFeb() {
        return seasonalityFeb;
    }

    public Integer getSeasonalityMar() {
        return seasonalityMar;
    }

    public Integer getSeasonalityApr() {
        return seasonalityApr;
    }

    public Integer getSeasonalityMay() {
        return seasonalityMay;
    }

    public Integer getSeasonalityJun() {
        return seasonalityJun;
    }

    public Integer getSeasonalityJul() {
        return seasonalityJul;
    }

    public Integer getSeasonalityAug() {
        return seasonalityAug;
    }

    public Integer getSeasonalitySep() {
        return seasonalitySep;
    }

    public Integer getSeasonalityOct() {
        return seasonalityOct;
    }

    public Integer getSeasonalityNov() {
        return seasonalityNov;
    }

    public Integer getSeasonalityDec() {
        return seasonalityDec;
    }

    public Integer getLegacyCasingId() {
        return legacyCasingId;
    }

    public String getSeasonalityNotes() {
        return seasonalityNotes;
    }
}

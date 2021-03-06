package com.mtn.model.domain;

import com.mtn.constant.RatingType;
import com.mtn.model.StoreChild;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@AttributeOverride(name="id", column=@Column(name="store_survey_id"))
public class StoreSurvey extends AuditingEntity implements StoreChild {

    private LocalDateTime surveyDate;
    private String note;
    private Integer registerCountNormal;
    private Integer registerCountExpress;
    private Integer registerCountSelfCheckout;
    private Integer fuelDispenserCount;
    private Boolean pharmacyIsOpen24 = false;
    private Boolean pharmacyHasDriveThrough = false;
    private Integer pharmacyScriptsWeekly;
    private Double pharmacyAvgDollarsPerScript;
    private Integer pharmacyPharmacistCount;
    private Integer pharmacyTechnicianCount;
    private Boolean departmentBakery = false;
    private Boolean departmentBank = false;
    private Boolean departmentBeer = false;
    private Boolean departmentBulk = false;
    private Boolean departmentCheese = false;
    private Boolean departmentCoffee = false;
    private Boolean departmentDeli = false;
    private Boolean departmentExpandedGm = false;
    private Boolean departmentExtensivePreparedFoods = false;
    private Boolean departmentFloral = false;
    private Boolean departmentFuel = false;
    private Boolean departmentInStoreRestaurant = false;
    private Boolean departmentLiquor = false;
    private Boolean departmentMeat = false;
    private Boolean departmentNatural = false;
    private Boolean departmentOliveBar = false;
    private Boolean departmentOnlinePickup = false;
    private Boolean departmentPharmacy = false;
    private Boolean departmentPreparedFoods = false;
    private Boolean departmentSaladBar = false;
    private Boolean departmentSeafood = false;
    private Boolean departmentSeating = false;
    private Boolean departmentSushi = false;
    private Boolean departmentWine = false;
    private Boolean accessibilityFarthestFromEntrance = false;
    private Boolean accessibilityMainIntersectionHasTrafficLight = false;
    private Boolean accessibilityMultipleRetailersBeforeSite = false;
    private Boolean accessibilitySetBackTwiceParkingLength = false;
    private RatingType accessibilityRating;
    private Boolean parkingOutparcelsInterfereWithParking = false;
    private Boolean parkingDirectAccessToParking = false;
    private Boolean parkingSmallParkingField = false;
    private Boolean parkingHasTSpaces = false;
    private Boolean parkingHasAngledSpaces = false;
    private Boolean parkingHasParkingHog = false;
    private Boolean parkingIsPoorlyLit = false;
    private RatingType parkingRating;
    private Integer parkingSpaceCount;
    private Boolean visibilityHillDepressionBlocksView = false;
    private Boolean visibilityOutparcelsBlockView = false;
    private Boolean visibilitySignOnMain = false;
    private Boolean visibilityStoreFacesMainRoad = false;
    private Boolean visibilityTreesBlockView = false;
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

    private Store store;
    private List<StoreCasing> storeCasings = new ArrayList<>();

    public StoreSurvey() {
    }

    public StoreSurvey(StoreSurvey storeSurvey) {
        this.surveyDate = storeSurvey.surveyDate;
        this.note = storeSurvey.note;
        this.registerCountNormal = storeSurvey.registerCountNormal;
        this.registerCountExpress = storeSurvey.registerCountExpress;
        this.registerCountSelfCheckout = storeSurvey.registerCountSelfCheckout;
        this.fuelDispenserCount = storeSurvey.fuelDispenserCount;
        this.pharmacyIsOpen24 = storeSurvey.pharmacyIsOpen24;
        this.pharmacyHasDriveThrough = storeSurvey.pharmacyHasDriveThrough;
        this.pharmacyScriptsWeekly = storeSurvey.pharmacyScriptsWeekly;
        this.pharmacyAvgDollarsPerScript = storeSurvey.pharmacyAvgDollarsPerScript;
        this.pharmacyPharmacistCount = storeSurvey.pharmacyPharmacistCount;
        this.pharmacyTechnicianCount = storeSurvey.pharmacyTechnicianCount;
        this.departmentBakery = storeSurvey.departmentBakery;
        this.departmentBank = storeSurvey.departmentBank;
        this.departmentBeer = storeSurvey.departmentBeer;
        this.departmentBulk = storeSurvey.departmentBulk;
        this.departmentCheese = storeSurvey.departmentCheese;
        this.departmentCoffee = storeSurvey.departmentCoffee;
        this.departmentDeli = storeSurvey.departmentDeli;
        this.departmentExpandedGm = storeSurvey.departmentExpandedGm;
        this.departmentExtensivePreparedFoods = storeSurvey.departmentExtensivePreparedFoods;
        this.departmentFloral = storeSurvey.departmentFloral;
        this.departmentFuel = storeSurvey.departmentFuel;
        this.departmentInStoreRestaurant = storeSurvey.departmentInStoreRestaurant;
        this.departmentLiquor = storeSurvey.departmentLiquor;
        this.departmentMeat = storeSurvey.departmentMeat;
        this.departmentNatural = storeSurvey.departmentNatural;
        this.departmentOliveBar = storeSurvey.departmentOliveBar;
        this.departmentOnlinePickup = storeSurvey.departmentOnlinePickup;
        this.departmentPharmacy = storeSurvey.departmentPharmacy;
        this.departmentPreparedFoods = storeSurvey.departmentPreparedFoods;
        this.departmentSaladBar = storeSurvey.departmentSaladBar;
        this.departmentSeafood = storeSurvey.departmentSeafood;
        this.departmentSeating = storeSurvey.departmentSeating;
        this.departmentSushi = storeSurvey.departmentSushi;
        this.departmentWine = storeSurvey.departmentWine;
        this.accessibilityFarthestFromEntrance = storeSurvey.accessibilityFarthestFromEntrance;
        this.accessibilityMainIntersectionHasTrafficLight = storeSurvey.accessibilityMainIntersectionHasTrafficLight;
        this.accessibilityMultipleRetailersBeforeSite = storeSurvey.accessibilityMultipleRetailersBeforeSite;
        this.accessibilitySetBackTwiceParkingLength = storeSurvey.accessibilitySetBackTwiceParkingLength;
        this.accessibilityRating = storeSurvey.accessibilityRating;
        this.parkingOutparcelsInterfereWithParking = storeSurvey.parkingOutparcelsInterfereWithParking;
        this.parkingDirectAccessToParking = storeSurvey.parkingDirectAccessToParking;
        this.parkingSmallParkingField = storeSurvey.parkingSmallParkingField;
        this.parkingHasTSpaces = storeSurvey.parkingHasTSpaces;
        this.parkingHasAngledSpaces = storeSurvey.parkingHasAngledSpaces;
        this.parkingHasParkingHog = storeSurvey.parkingHasParkingHog;
        this.parkingIsPoorlyLit = storeSurvey.parkingIsPoorlyLit;
        this.parkingRating = storeSurvey.parkingRating;
        this.parkingSpaceCount = storeSurvey.parkingSpaceCount;
        this.visibilityHillDepressionBlocksView = storeSurvey.visibilityHillDepressionBlocksView;
        this.visibilityOutparcelsBlockView = storeSurvey.visibilityOutparcelsBlockView;
        this.visibilitySignOnMain = storeSurvey.visibilitySignOnMain;
        this.visibilityStoreFacesMainRoad = storeSurvey.visibilityStoreFacesMainRoad;
        this.visibilityTreesBlockView = storeSurvey.visibilityTreesBlockView;
        this.visibilityRating = storeSurvey.visibilityRating;
        this.seasonalityJan = storeSurvey.seasonalityJan;
        this.seasonalityFeb = storeSurvey.seasonalityFeb;
        this.seasonalityMar = storeSurvey.seasonalityMar;
        this.seasonalityApr = storeSurvey.seasonalityApr;
        this.seasonalityMay = storeSurvey.seasonalityMay;
        this.seasonalityJun = storeSurvey.seasonalityJun;
        this.seasonalityJul = storeSurvey.seasonalityJul;
        this.seasonalityAug = storeSurvey.seasonalityAug;
        this.seasonalitySep = storeSurvey.seasonalitySep;
        this.seasonalityOct = storeSurvey.seasonalityOct;
        this.seasonalityNov = storeSurvey.seasonalityNov;
        this.seasonalityDec = storeSurvey.seasonalityDec;
        this.seasonalityNotes = storeSurvey.seasonalityNotes;
    }

    @ManyToOne
    @JoinColumn(name = "store_id")
    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    @Column(name = "store_survey_note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    @Column(name = "pharmacy_is_open_24")
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

    public Integer getPharmacyScriptsWeekly() {
        return pharmacyScriptsWeekly;
    }

    public void setPharmacyScriptsWeekly(Integer pharmacyScriptsWeekly) {
        this.pharmacyScriptsWeekly = pharmacyScriptsWeekly;
    }

    public Double getPharmacyAvgDollarsPerScript() {
        return pharmacyAvgDollarsPerScript;
    }

    public void setPharmacyAvgDollarsPerScript(Double pharmacyAvgDollarsPerScript) {
        this.pharmacyAvgDollarsPerScript = pharmacyAvgDollarsPerScript;
    }

    public Integer getPharmacyPharmacistCount() {
        return pharmacyPharmacistCount;
    }

    public void setPharmacyPharmacistCount(Integer pharmacyPharmacistCount) {
        this.pharmacyPharmacistCount = pharmacyPharmacistCount;
    }

    public Integer getPharmacyTechnicianCount() {
        return pharmacyTechnicianCount;
    }

    public void setPharmacyTechnicianCount(Integer pharmacyTechnicianCount) {
        this.pharmacyTechnicianCount = pharmacyTechnicianCount;
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

    @Enumerated(EnumType.STRING)
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

    @Column(name = "parking_has_t_spaces")
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

    @Enumerated(EnumType.STRING)
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

    @Enumerated(EnumType.STRING)
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

    @Column(name = "store_survey_date")
    public LocalDateTime getSurveyDate() {
        return surveyDate;
    }

    public void setSurveyDate(LocalDateTime surveyDate) {
        this.surveyDate = surveyDate;
    }

    @OneToMany(mappedBy = "storeSurvey")
    public List<StoreCasing> getStoreCasings() {
        return storeCasings;
    }

    public void setStoreCasings(List<StoreCasing> storeCasings) {
        this.storeCasings = storeCasings;
    }

    public String getSeasonalityNotes() {
        return seasonalityNotes;
    }

    public void setSeasonalityNotes(String seasonalityNotes) {
        this.seasonalityNotes = seasonalityNotes;
    }
}

package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.constant.RatingType;
import com.mtn.model.domain.StoreSurvey;
import com.mtn.model.simpleView.SimpleInteractionView;

import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StoreSurveyView extends AuditingEntityView {

    private Integer id;
    private String fit;
    private String format;
    private Integer areaSales;
    private Double areaSalesPercentOfTotal;
    private Integer areaTotal;
    private Boolean areaIsEstimate;
    private String note;
    private Boolean storeIsOpen24 = false;
    private Boolean naturalFoodsAreIntegrated = false;
    private Integer registerCountNormal;
    private Integer registerCountExpress;
    private Integer registerCountSelfCheckout;
    private Integer fuelDispenserCount;
    private Boolean fuelIsOpen24 = false;
    private Boolean pharmacyIsOpen24 = false;
    private Boolean pharmacyHasDriveThrough = false;
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
    private Boolean departmentHotBar = false;
    private Boolean departmentInStoreRestaurant = false;
    private Boolean departmentLiquor = false;
    private Boolean departmentMeat = false;
    private Boolean departmentNatural = false;
    private Boolean departmentOliveBar = false;
    private Boolean departmentPharmacy = false;
    private Boolean departmentPreparedFoods = false;
    private Boolean departmentSaladBar = false;
    private Boolean departmentSeafood = false;
    private Boolean departmentSeating = false;
    private Boolean departmentSushi = false;
    private Boolean departmentWine = false;
    private Boolean accessibilityFarthestFromEntrance = false;
    private Boolean accessibilityMainIntersectionHasTrafficLight = false;
    private Boolean accessibilityMainIntersectionNeedsTrafficLight = false;
    private Boolean accessibilityMultipleRetailersBeforeSite = false;
    private Boolean accessibilitySetBackTwiceParkingLength = false;
    private RatingType accessibilityRating;
    private Boolean parkingOutparcelsInterfereWithParking = false;
    private Boolean parkingDirectAccessToParking = false;
    private Boolean parkingSmallParkingField = false;
    private Boolean parkingHasTSpaces = false;
    private RatingType parkingRating;
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
    private List<SimpleInteractionView> interactions;

    public StoreSurveyView(StoreSurvey storeSurvey) {
        super(storeSurvey);

        this.id = storeSurvey.getId();
        this.fit = storeSurvey.getFit();
        this.format = storeSurvey.getFormat();
        this.areaSales = storeSurvey.getAreaSales();
        this.areaSalesPercentOfTotal = storeSurvey.getAreaSalesPercentOfTotal();
        this.areaTotal = storeSurvey.getAreaTotal();
        this.areaIsEstimate = storeSurvey.getAreaIsEstimate();

        this.setNote(storeSurvey.getNote());
        this.setStoreIsOpen24(storeSurvey.getStoreIsOpen24());
        this.setNaturalFoodsAreIntegrated(storeSurvey.getNaturalFoodsAreIntegrated());
        this.setRegisterCountNormal(storeSurvey.getRegisterCountNormal());
        this.setRegisterCountExpress(storeSurvey.getRegisterCountExpress());
        this.setRegisterCountSelfCheckout(storeSurvey.getRegisterCountSelfCheckout());
        this.setFuelDispenserCount(storeSurvey.getFuelDispenserCount());
        this.setFuelIsOpen24(storeSurvey.getFuelIsOpen24());
        this.setPharmacyIsOpen24(storeSurvey.getPharmacyIsOpen24());
        this.setPharmacyHasDriveThrough(storeSurvey.getPharmacyHasDriveThrough());
        this.setDepartmentBakery(storeSurvey.getDepartmentBakery());
        this.setDepartmentBank(storeSurvey.getDepartmentBank());
        this.setDepartmentBeer(storeSurvey.getDepartmentBeer());
        this.setDepartmentBulk(storeSurvey.getDepartmentBulk());
        this.setDepartmentCheese(storeSurvey.getDepartmentCheese());
        this.setDepartmentCoffee(storeSurvey.getDepartmentCoffee());
        this.setDepartmentDeli(storeSurvey.getDepartmentDeli());
        this.setDepartmentExpandedGm(storeSurvey.getDepartmentExpandedGm());
        this.setDepartmentExtensivePreparedFoods(storeSurvey.getDepartmentExtensivePreparedFoods());
        this.setDepartmentFloral(storeSurvey.getDepartmentFloral());
        this.setDepartmentFuel(storeSurvey.getDepartmentFuel());
        this.setDepartmentHotBar(storeSurvey.getDepartmentHotBar());
        this.setDepartmentInStoreRestaurant(storeSurvey.getDepartmentInStoreRestaurant());
        this.setDepartmentLiquor(storeSurvey.getDepartmentLiquor());
        this.setDepartmentMeat(storeSurvey.getDepartmentMeat());
        this.setDepartmentNatural(storeSurvey.getDepartmentNatural());
        this.setDepartmentOliveBar(storeSurvey.getDepartmentOliveBar());
        this.setDepartmentPharmacy(storeSurvey.getDepartmentPharmacy());
        this.setDepartmentPreparedFoods(storeSurvey.getDepartmentPreparedFoods());
        this.setDepartmentSaladBar(storeSurvey.getDepartmentSaladBar());
        this.setDepartmentSeafood(storeSurvey.getDepartmentSeafood());
        this.setDepartmentSeating(storeSurvey.getDepartmentSeating());
        this.setDepartmentSushi(storeSurvey.getDepartmentSushi());
        this.setDepartmentWine(storeSurvey.getDepartmentWine());
        this.setAccessibilityFarthestFromEntrance(storeSurvey.getAccessibilityFarthestFromEntrance());
        this.setAccessibilityMainIntersectionHasTrafficLight(storeSurvey.getAccessibilityMainIntersectionHasTrafficLight());
        this.setAccessibilityMainIntersectionNeedsTrafficLight(storeSurvey.getAccessibilityMainIntersectionNeedsTrafficLight());
        this.setAccessibilityMultipleRetailersBeforeSite(storeSurvey.getAccessibilityMultipleRetailersBeforeSite());
        this.setAccessibilitySetBackTwiceParkingLength(storeSurvey.getAccessibilitySetBackTwiceParkingLength());
        this.setAccessibilityRating(storeSurvey.getAccessibilityRating());
        this.setParkingOutparcelsInterfereWithParking(storeSurvey.getParkingOutparcelsInterfereWithParking());
        this.setParkingDirectAccessToParking(storeSurvey.getParkingDirectAccessToParking());
        this.setParkingSmallParkingField(storeSurvey.getParkingSmallParkingField());
        this.setParkingHasTSpaces(storeSurvey.getParkingHasTSpaces());
        this.setParkingRating(storeSurvey.getParkingRating());
        this.setVisibilityHillDepressionBlocksView(storeSurvey.getVisibilityHillDepressionBlocksView());
        this.setVisibilityOutparcelsBlockView(storeSurvey.getVisibilityOutparcelsBlockView());
        this.setVisibilitySignOnMain(storeSurvey.getVisibilitySignOnMain());
        this.setVisibilityStoreFacesMainRoad(storeSurvey.getVisibilityStoreFacesMainRoad());
        this.setVisibilityRating(storeSurvey.getVisibilityRating());
        this.setSeasonalityJan(storeSurvey.getSeasonalityJan());
        this.setSeasonalityFeb(storeSurvey.getSeasonalityFeb());
        this.setSeasonalityMar(storeSurvey.getSeasonalityMar());
        this.setSeasonalityApr(storeSurvey.getSeasonalityApr());
        this.setSeasonalityMay(storeSurvey.getSeasonalityMay());
        this.setSeasonalityJun(storeSurvey.getSeasonalityJun());
        this.setSeasonalityJul(storeSurvey.getSeasonalityJul());
        this.setSeasonalityAug(storeSurvey.getSeasonalityAug());
        this.setSeasonalitySep(storeSurvey.getSeasonalitySep());
        this.setSeasonalityOct(storeSurvey.getSeasonalityOct());
        this.setSeasonalityNov(storeSurvey.getSeasonalityNov());
        this.setSeasonalityDec(storeSurvey.getSeasonalityDec());
        this.setLegacyCasingId(storeSurvey.getLegacyCasingId());
        this.interactions = storeSurvey.getInteractions().stream().filter(interaction -> interaction.getDeletedDate() == null).map(SimpleInteractionView::new).collect(Collectors.toList());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public RatingType getParkingRating() {
        return parkingRating;
    }

    public void setParkingRating(RatingType parkingRating) {
        this.parkingRating = parkingRating;
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

    public List<SimpleInteractionView> getInteractions() {
        return interactions;
    }

    public void setInteractions(List<SimpleInteractionView> interactions) {
        this.interactions = interactions;
    }
}

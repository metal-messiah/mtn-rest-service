package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.constant.StoreType;
import com.mtn.model.domain.Store;
import com.mtn.model.simpleView.*;
import com.mtn.model.utils.StoreUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StoreView extends AuditingEntityView {

	private String storeName;
	private StoreType storeType;
	private LocalDateTime dateOpened;
	private LocalDateTime dateClosed;
	private String fit;
	private String format;
	private Integer areaSales;
	private Double areaSalesPercentOfTotal;
	private Integer areaTotal;
	private Boolean areaIsEstimate;
	private Boolean storeIsOpen24;
	private Boolean naturalFoodsAreIntegrated;
	private String storeNumber;
	private Integer legacyLocationId;
	private Boolean floating;
	private SimpleUserProfileView validatedBy;
	private LocalDateTime validatedDate;

	private SimpleStoreStatusView currentStoreStatus;
	private SimpleStoreSurveyView currentStoreSurvey;

	private SimpleSiteView site;
	private SimpleBannerView banner;

	private List<SimpleStoreCasingView> storeCasings;
	private List<SimpleStoreModelView> storeModels;
	private List<SimpleStoreVolumeView> storeVolumes;
	private List<SimpleStoreStatusView> storeStatuses;
	private List<SimpleStoreSourceView> storeSources;

	private List<SimpleStoreListView> storeLists;

	public StoreView() {
	}

	public StoreView(Store store) {
		super(store);

		this.storeName = store.getStoreName();
		this.storeType = store.getStoreType();
		this.dateOpened = store.getDateOpened();
		this.dateClosed = store.getDateClosed();
		this.fit = store.getFit();
		this.format = store.getFormat();
		this.areaSales = store.getAreaSales();
		this.areaSalesPercentOfTotal = store.getAreaSalesPercentOfTotal();
		this.areaTotal = store.getAreaTotal();
		this.areaIsEstimate = store.getAreaIsEstimate();
		this.storeIsOpen24 = store.getStoreIsOpen24();
		this.naturalFoodsAreIntegrated = store.getNaturalFoodsAreIntegrated();
		this.legacyLocationId = store.getLegacyLocationId();
		this.storeNumber = store.getStoreNumber();
		this.floating = store.getFloating();
		if (store.getValidatedBy() != null) {
			this.validatedBy = new SimpleUserProfileView(store.getValidatedBy());
		}
		this.validatedDate = store.getValidatedDate();

		this.site = new SimpleSiteView(store.getSite());

		StoreUtil.getLatestStatusAsOfDateTime(store, LocalDateTime.now())
				.ifPresent(status -> this.currentStoreStatus = new SimpleStoreStatusView(status));

		StoreUtil.getLatestSurveyAsOfDateTime(store, LocalDateTime.now())
				.ifPresent(storeSurvey -> this.currentStoreSurvey = new SimpleStoreSurveyView(storeSurvey));
		if (store.getBanner() != null) {
			this.banner = new SimpleBannerView(store.getBanner());
		}

		if (store.getCasings() != null) {
			this.storeCasings = store.getCasings().stream().filter(casing -> casing.getDeletedDate() == null)
					.map(SimpleStoreCasingView::new).collect(Collectors.toList());
		}
		if (store.getModels() != null) {
			this.storeModels = store.getModels().stream().filter(model -> model.getDeletedDate() == null)
					.map(SimpleStoreModelView::new).collect(Collectors.toList());
		}
		if (store.getVolumes() != null) {
			this.storeVolumes = store.getVolumes().stream().filter(volume -> volume.getDeletedDate() == null)
					.map(SimpleStoreVolumeView::new).collect(Collectors.toList());
		}
		if (store.getStatuses() != null) {
			this.storeStatuses = store.getStatuses().stream().filter(status -> status.getDeletedDate() == null)
					.map(SimpleStoreStatusView::new).collect(Collectors.toList());
		}

		if (store.getStoreLists() != null) {
			this.storeLists = store.getStoreLists().stream()
					// .filter(storeList -> storeList.getDeletedDate() == null)
					.map(SimpleStoreListView::new).collect(Collectors.toList());
		}
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public StoreType getStoreType() {
		return storeType;
	}

	public void setStoreType(StoreType storeType) {
		this.storeType = storeType;
	}

	public LocalDateTime getDateOpened() {
		return dateOpened;
	}

	public void setDateOpened(LocalDateTime dateOpened) {
		this.dateOpened = dateOpened;
	}

	public LocalDateTime getDateClosed() {
		return dateClosed;
	}

	public void setDateClosed(LocalDateTime dateClosed) {
		this.dateClosed = dateClosed;
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

	public String getStoreNumber() {
		return storeNumber;
	}

	public void setStoreNumber(String storeNumber) {
		this.storeNumber = storeNumber;
	}

	public Integer getLegacyLocationId() {
		return legacyLocationId;
	}

	public void setLegacyLocationId(Integer legacyLocationId) {
		this.legacyLocationId = legacyLocationId;
	}

	public Boolean getFloating() {
		return floating;
	}

	public void setFloating(Boolean floating) {
		this.floating = floating;
	}

	public SimpleStoreStatusView getCurrentStoreStatus() {
		return currentStoreStatus;
	}

	public void setCurrentStoreStatus(SimpleStoreStatusView currentStoreStatus) {
		this.currentStoreStatus = currentStoreStatus;
	}

	public SimpleStoreSurveyView getCurrentStoreSurvey() {
		return currentStoreSurvey;
	}

	public void setCurrentStoreSurvey(SimpleStoreSurveyView currentStoreSurvey) {
		this.currentStoreSurvey = currentStoreSurvey;
	}

	public SimpleSiteView getSite() {
		return site;
	}

	public void setSite(SimpleSiteView site) {
		this.site = site;
	}

	public SimpleBannerView getBanner() {
		return banner;
	}

	public void setBanner(SimpleBannerView banner) {
		this.banner = banner;
	}

	public List<SimpleStoreCasingView> getStoreCasings() {
		return storeCasings;
	}

	public void setStoreCasings(List<SimpleStoreCasingView> storeCasings) {
		this.storeCasings = storeCasings;
	}

	public List<SimpleStoreModelView> getStoreModels() {
		return storeModels;
	}

	public void setStoreModels(List<SimpleStoreModelView> storeModels) {
		this.storeModels = storeModels;
	}

	public List<SimpleStoreVolumeView> getStoreVolumes() {
		return storeVolumes;
	}

	public void setStoreVolumes(List<SimpleStoreVolumeView> storeVolumes) {
		this.storeVolumes = storeVolumes;
	}

	public List<SimpleStoreStatusView> getStoreStatuses() {
		return storeStatuses;
	}

	public void setStoreStatuses(List<SimpleStoreStatusView> storeStatuses) {
		this.storeStatuses = storeStatuses;
	}

	public SimpleUserProfileView getValidatedBy() {
		return validatedBy;
	}

	public void setValidatedBy(SimpleUserProfileView validatedBy) {
		this.validatedBy = validatedBy;
	}

	public LocalDateTime getValidatedDate() {
		return validatedDate;
	}

	public void setValidatedDate(LocalDateTime validatedDate) {
		this.validatedDate = validatedDate;
	}

	public List<SimpleStoreListView> getStoreLists() {
		return storeLists;
	}

	public void setStoreLists(List<SimpleStoreListView> storeLists) {
		this.storeLists = storeLists;
	}

	public List<SimpleStoreSourceView> getStoreSources() {
		return storeSources;
	}

	public void setStoreSources(List<SimpleStoreSourceView> storeSources) {
		this.storeSources = storeSources;
	}
}

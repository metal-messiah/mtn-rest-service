package com.mtn.service;

import com.mtn.config.SiteWiseSftpConfig;
import com.mtn.model.AvgCounty;
import com.mtn.model.domain.*;
import com.mtn.repository.*;
import com.mtn.util.MtnLogger;
import com.opencsv.CSVWriter;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class SiteWiseService {

	private final SiteWiseSftpConfig.SiteWiseSftpGateway gateway;
	private final ActiveAndFutureStoresRepository activeAndFutureStoresRepository;
	private final AvgByBannerInCountyRepository avgByBannerInCountyRepository;
	private final AvgByBannerRepository avgByBannerRepository;
	private final AvgByCountyRepository avgByCountyRepository;
	private final AvgByFitInCountyRepository avgByFitInCountyRepository;
	private final AvgByFitRepository avgByFitRepository;
	private final AvgSalesAreaPercentByBannerRepository avgSalesAreaPercentByBannerRepository;
	private final AvgSalesAreaPercentByFitRepository avgSalesAreaPercentByFitRepository;

	@Autowired
	public SiteWiseService(@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") SiteWiseSftpConfig.SiteWiseSftpGateway gateway,
						   ActiveAndFutureStoresRepository activeAndFutureStoresRepository,
						   AvgByBannerInCountyRepository avgByBannerInCountyRepository,
						   AvgByBannerRepository avgByBannerRepository,
						   AvgByCountyRepository avgByCountyRepository,
						   AvgByFitInCountyRepository avgByFitInCountyRepository,
						   AvgByFitRepository avgByFitRepository,
						   AvgSalesAreaPercentByBannerRepository avgSalesAreaPercentByBannerRepository,
						   AvgSalesAreaPercentByFitRepository avgSalesAreaPercentByFitRepository) {
		this.gateway = gateway;
		this.activeAndFutureStoresRepository = activeAndFutureStoresRepository;
		this.avgByBannerInCountyRepository = avgByBannerInCountyRepository;
		this.avgByBannerRepository = avgByBannerRepository;
		this.avgByCountyRepository = avgByCountyRepository;
		this.avgByFitInCountyRepository = avgByFitInCountyRepository;
		this.avgByFitRepository = avgByFitRepository;
		this.avgSalesAreaPercentByBannerRepository = avgSalesAreaPercentByBannerRepository;
		this.avgSalesAreaPercentByFitRepository = avgSalesAreaPercentByFitRepository;
	}

	public File getCsvFile() throws IOException {
		MtnLogger.info("Creating file: " + LocalDateTime.now());

		File temp = File.createTempFile("temp-file-name", ".tmp");

		CSVWriter csvWriter = new CSVWriter(new FileWriter(temp));
		getCsvData().forEach(csvWriter::writeNext);
		csvWriter.close();
		return temp;
	}

	@Async
	@Transactional(readOnly = true)
	public void buildAndTransmitExtraction() {
		try {
			File temp = this.getCsvFile();
			this.gateway.sendToSftp(temp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Boolean sameCounty(ActiveAndFutureStores a, AvgCounty c) {
		if (a.getCounty() == null || a.getState() == null || c.getCounty() == null || c.getState() == null) {
			return false;
		}
		return a.getCounty().toLowerCase().equals(c.getCounty().toLowerCase()) && a.getState().toLowerCase().equals(c.getState().toLowerCase());
	}

	private Integer nearestThousand(float number) {
		return Math.round(number / 1000f) * 1000;
	}

	private List<String[]> getCsvData() {
		List<AvgByBannerInCounty> avgByBannerInCounty = this.avgByBannerInCountyRepository.findAll();
		List<AvgByBanner> avgByBanner = avgByBannerRepository.findAll();
		List<AvgByCounty> avgByCounty = avgByCountyRepository.findAll();
		List<AvgByFitInCounty> avgByFitInCounty = avgByFitInCountyRepository.findAll();
		List<AvgByFit> avgByFit = avgByFitRepository.findAll();
		List<AvgSalesAreaPercentByBanner> avgSalesAreaPercentByBanner = avgSalesAreaPercentByBannerRepository.findAll();
		List<AvgSalesAreaPercentByFit> avgSalesAreaPercentByFit = avgSalesAreaPercentByFitRepository.findAll();

		Function<ActiveAndFutureStores, Pair<String, Integer>> getAreaSales = a -> {
			if (a.getAreaSales() != null) {
				return new Pair<>("DB", nearestThousand(a.getAreaSales()));
			}
			if (a.getBannerId() != null && a.getAreaTotal() != null) {
				Optional<AvgSalesAreaPercentByBanner> abo = avgSalesAreaPercentByBanner.stream().filter(ab -> ab.getBannerId().equals(a.getBannerId())).findAny();
				if (abo.isPresent() && abo.get().getAvgSalesPercent() != null) {
					Integer salesArea = nearestThousand(abo.get().getAvgSalesPercent() * a.getAreaTotal());
					return new Pair<>("Calculated % of total avg by banner", salesArea);
				}
			}
			if (a.getStoreFit() != null && a.getAreaTotal() != null) {
				Optional<AvgSalesAreaPercentByFit> abo = avgSalesAreaPercentByFit.stream().filter(ab -> ab.getStoreFit().equals(a.getStoreFit())).findAny();
				if (abo.isPresent() && abo.get().getAvgSalesPercent() != null) {
					Integer salesArea = nearestThousand(abo.get().getAvgSalesPercent() * a.getAreaTotal());
					return new Pair<>("Calculated % of total avg by fit", salesArea);
				}
			}
			if (a.getBannerId() != null) {
				Optional<AvgByBannerInCounty> abo = avgByBannerInCounty.stream().filter(ab -> ab.getBannerId().equals(a.getBannerId()) && sameCounty(a, ab)).findAny();
				if (abo.isPresent() && abo.get().getAreaSales() != null) {
					Integer salesArea = nearestThousand(abo.get().getAreaSales());
					return new Pair<>("Avg by Banner in County", salesArea);
				}
			}
			if (a.getBannerId() != null) {
				Optional<AvgByBanner> abo = avgByBanner.stream().filter(ab -> ab.getBannerId().equals(a.getBannerId())).findAny();
				if (abo.isPresent() && abo.get().getAreaSales() != null) {
					Integer salesArea = nearestThousand(abo.get().getAreaSales());
					return new Pair<>("Avg by Banner in Nation", salesArea);
				}
			}
			if (a.getStoreFit() != null) {
				Optional<AvgByFitInCounty> abo = avgByFitInCounty.stream().filter(ab -> ab.getStoreFit().equals(a.getStoreFit()) && sameCounty(a, ab)).findAny();
				if (abo.isPresent() && abo.get().getAreaSales() != null) {
					Integer salesArea = nearestThousand(abo.get().getAreaSales());
					return new Pair<>("Avg by Fit in County", salesArea);
				}
			}
			if (a.getStoreFit() != null) {
				Optional<AvgByFit> abo = avgByFit.stream().filter(ab -> ab.getStoreFit().equals(a.getStoreFit())).findAny();
				if (abo.isPresent() && abo.get().getAreaSales() != null) {
					Integer salesArea = nearestThousand(abo.get().getAreaSales());
					return new Pair<>("Avg by Fit in Nation", salesArea);
				}
			}
			Optional<AvgByCounty> abo = avgByCounty.stream().filter(ab -> sameCounty(a, ab)).findAny();
			if (abo.isPresent() && abo.get().getAreaSales() != null) {
				Integer salesArea = nearestThousand(abo.get().getAreaSales());
				return new Pair<>("Avg by County", salesArea);
			}
			return null;
		};

		MtnLogger.info("Building document: " + LocalDateTime.now());

		long start = System.currentTimeMillis();

		List<String[]> rows = new ArrayList<>();

		// Add the header row to the document
		rows.add(this.getHeaderRow());

		// Set page size
		Pageable pageRequest = new PageRequest(0, 50000);
		Page<ActiveAndFutureStores> page;

		while (true) {
			long pageStart = System.currentTimeMillis();

			// Gets a page of stores
			page = this.getPage(pageRequest);

			long pageRetrieved = System.currentTimeMillis();
			MtnLogger.info("Page " + (page.getNumber() + 1) + "/" + page.getTotalPages() + " retrieved in  " + ((pageRetrieved - pageStart) / 1000L) + " seconds");

//			page.forEach(store -> rows.add(this.getStoreRow(store, getStoreStrength)));
			page.forEach(store -> rows.add(this.getRow(store, getAreaSales)));

			long pageProcessed = System.currentTimeMillis();
			MtnLogger.info("Page " + (page.getNumber() + 1) + "/" + page.getTotalPages() + " processed in  " + ((pageProcessed - pageRetrieved) / 1000L) + " seconds");

			if (page.hasNext() && page.getNumber() < 1) {
				pageRequest = page.nextPageable();
			} else {
				break;
			}
		}

		long ellapsedSeconds = (System.currentTimeMillis() - start) / 1000L;
		MtnLogger.info("Data collected after: " + ellapsedSeconds + " s");

		return rows;
	}

	private Page<ActiveAndFutureStores> getPage(Pageable pageable) {
		return this.activeAndFutureStoresRepository.findAll(pageable);
	}

	private String[] getHeaderRow() {
		return new String[]{"store_id", "latitude", "longitude", "address_1", "address_2", "city", "county", "state", "postal_code", "quad", "intersection_street_primary", "intersection_street_secondary", "store_type", "store_name", "store_number", "store_status", "store_strength", "store_fit", "using_default_fit", "banner_id", "banner_name", "company_id", "company_name", "parent_company_id", "parent_company_name", "area_sales", "area_total", "volume_choice", "volume_total", "volume_date", "shopping_center_feature_score", "rating_synergy", "tenant_vacant_count", "tenant_occupied_count", "parking_space_count", "legacy_location_id", "cbsa_id", "sc_condition_score", "volume_confidence", "power", "power_date", "using_area_sales_from", "using_area_total_from", "pseudo_power", "dollars_per_sqft"};
	}

	private void addIntToRow(List<String> row, Integer value) {
		row.add(value != null ? String.valueOf(value) : null);
	}

	private void addFloatToRow(List<String> row, Float value) {
		row.add(value != null ? String.valueOf(value) : null);
	}

	private void addDateToRow(List<String> row, LocalDate value) {
		row.add(value != null ? value.toString() : null);
	}

	private String[] getRow(ActiveAndFutureStores s, Function<ActiveAndFutureStores, Pair<String, Integer>> getAreaSales) {
		Pair<String, Integer> salesArea = getAreaSales.apply(s);

		List<String> row = new ArrayList<>();
		row.add(s.getStoreId().toString());
		row.add(String.valueOf(s.getLatitude()));
		row.add(String.valueOf(s.getLongitude()));
		row.add(s.getAddress1());
		row.add(s.getAddress2());
		row.add(s.getCity());
		row.add(s.getCounty());
		row.add(s.getState());
		row.add(s.getPostalCode());
		row.add(s.getQuad());
		row.add(s.getIntersectionStreetPrimary());
		row.add(s.getIntersectionStreetSecondary());
		row.add(s.getStoreType());
		row.add(s.getStoreName());
		row.add(s.getStoreNumber());
		row.add(s.getStoreStatus());
		row.add(String.valueOf(s.getStoreStrength()));
		row.add(s.getStoreFit());
		row.add(s.getUsingDefaultFit());
		this.addIntToRow(row, s.getBannerId());
		row.add(s.getBannerName());
		this.addIntToRow(row, s.getCompanyId());
		row.add(s.getCompanyName());
		this.addIntToRow(row, s.getParentCompanyId());
		row.add(s.getParentCompanyName());
		if (salesArea != null) {
			this.addIntToRow(row, salesArea.getValue());
		} else {
			row.add(null);
		}
		this.addIntToRow(row, s.getAreaTotal());
		row.add(s.getVolumeChoice());
		this.addIntToRow(row, s.getVolumeTotal());
		this.addDateToRow(row, s.getVolumeDate());
		this.addFloatToRow(row, s.getShoppingCenterFeatureScore());
		row.add(s.getRatingSynergy());
		this.addIntToRow(row, s.getTenantVacantCount());
		this.addIntToRow(row, s.getTenantOccupiedCount());
		this.addIntToRow(row, s.getParkingSpaceCount());
		this.addIntToRow(row, s.getLegacyLocationId());
		this.addIntToRow(row, s.getCbsaId());
		this.addFloatToRow(row, s.getScConditionScore());
		row.add(s.getVolumeConfidence());
		this.addIntToRow(row, s.getPower());
		this.addDateToRow(row, s.getPowerDate());

		if (salesArea != null) {
			row.add(salesArea.getKey());    //		using_area_sales_from
		} else {
			row.add(null);
		}
		row.add(null);    //		using_area_total_from
		row.add(null);    //		pseudo_power
		row.add(null);    //		dollars_per_sqft

		return row.toArray(new String[]{});
	}

//	private String[] getStoreRow(Store store, Function<Float, Float> getStoreStrength) {
//		Banner banner = store.getBanner();
//		StoreBestVolume bestVolume = this.bestVolumeRepository.findOneByStoreIdEquals(store.getId()).orElse(null);
//
//		Integer salesArea = null;
//		if (store.getAreaSales() != null) {
//			salesArea = store.getAreaSales();
//		} else if (banner != null && banner.getDefaultSalesArea() != null) {
//			salesArea = banner.getDefaultSalesArea();
//		}
//
//		List<String> row = new ArrayList<>();
//		row.add(store.getId().toString());
//		row.add(String.valueOf(store.getSite().getLatitude()));
//		row.add(String.valueOf(store.getSite().getLongitude()));
//		row.add(store.getSite().getAddress1());
//		row.add(store.getSite().getAddress2());
//		row.add(store.getSite().getCity());
//		row.add(store.getSite().getCounty());
//		row.add(store.getSite().getState());
//		row.add(store.getSite().getPostalCode());
//		row.add(store.getSite().getQuad());
//		row.add(store.getSite().getIntersectionStreetPrimary());
//		row.add(store.getSite().getIntersectionStreetSecondary());
//		row.add(store.getStoreType().name());
//		row.add(store.getStoreName());
//		row.add(store.getStoreNumber());
//
//		Optional<StoreStatus> storeStatus = StoreUtil.getLatestStatusAsOfDateTime(store, LocalDateTime.now());
//		if (storeStatus.isPresent()) {
//			row.add(storeStatus.get().getStatus());
//		} else {
//			row.add(null);
//		}
//
//		// store_strength
//		if (bestVolume != null && bestVolume.getVolumeTotal() != null && salesArea != null) {
//			float dps = bestVolume.getVolumeTotal() / new Float(salesArea);
//			float strength = getStoreStrength.apply(dps);
//			row.add(String.valueOf(strength));
//		} else {
//			row.add(null);
//		}
//
//		String fit = "Conventional";
//		boolean usingDefaultFit = true;
//		if (store.getFit() != null) {
//			fit = store.getFit();
//			usingDefaultFit = false;
//		} else if (banner != null) {
//			fit = banner.getDefaultStoreFit();
//		}
//		row.add(fit);
//		row.add(String.valueOf(usingDefaultFit));
//
//		if (banner != null) {
//			row.add(String.valueOf(banner.getId()));
//			row.add(banner.getBannerName());
//			Company company = banner.getCompany();
//			if (company != null) {
//				row.add(String.valueOf(company.getId()));
//				row.add(company.getCompanyName());
//				Company parentCompany = company.getParentCompany();
//				if (parentCompany != null) {
//					row.add(String.valueOf(parentCompany.getId()));
//					row.add(parentCompany.getCompanyName());
//				} else {
//					IntStream.range(0, 2).forEach(i -> row.add(null));
//				}
//			} else {
//				IntStream.range(0, 4).forEach(i -> row.add(null));
//			}
//		} else {
//			IntStream.range(0, 6).forEach(i -> row.add(null));
//		}
//		this.addIntToRow(row, salesArea);
//
//		this.addIntToRow(row, store.getAreaTotal());
//
//		if (bestVolume != null) {
//			row.add(bestVolume.getDecision());                      // volume_choice
//			this.addIntToRow(row, bestVolume.getVolumeTotal());        // volume_total
//			this.addDateToRow(row, bestVolume.getVolumeDate());        // volume_date
//		} else {
//			IntStream.range(0, 3).forEach(i -> row.add(null));
//		}
//
//		ShoppingCenter sc = store.getSite().getShoppingCenter();
//		if (sc != null) {
//			Optional<ShoppingCenterCasing> optScCasing = sc.getCasings().stream().max(Comparator.comparing(ShoppingCenterCasing::getCasingDate));
//			if (optScCasing.isPresent()) {
//				ShoppingCenterCasing mostRecentScCasing = optScCasing.get();
//				row.add(null);    //		shopping_center_feature_score
//				RatingType synergy = mostRecentScCasing.getRatingSynergy();
//				if (synergy != null) {
//					row.add(mostRecentScCasing.getRatingSynergy().name());    //		rating_synergy
//				} else {
//					row.add(null);
//				}
//				this.addIntToRow(row, mostRecentScCasing.getShoppingCenterSurvey().getTenantVacantCount());  //		tenant_vacant_count
//				this.addIntToRow(row, mostRecentScCasing.getShoppingCenterSurvey().getTenantOccupiedCount());  //		tenant_occupied_count
//			} else {
//				IntStream.range(0, 4).forEach(i -> row.add(null));
//			}
//		} else {
//			IntStream.range(0, 4).forEach(i -> row.add(null));
//		}
//
//		Optional<StoreCasing> optStoreCasing = store.getCasings().stream().max(Comparator.comparing(StoreCasing::getCasingDate));
//		if (optStoreCasing.isPresent()) {
//			this.addIntToRow(row, optStoreCasing.get().getStoreSurvey().getParkingSpaceCount());    //		parking_space_count
//		} else {
//			row.add(null);
//		}
//		this.addIntToRow(row, store.getLegacyLocationId());
//		this.addIntToRow(row, store.getSite().getCbsaId());    //		cbsa_id
//		row.add(null);    //		sc_condition_score
//
//		row.add(null);    //		volume_confidence *
//
//		Optional<StoreModel> optStoreModel = store.getModels().stream().max(Comparator.comparing(StoreModel::getModelDate));
//		if (optStoreModel.isPresent()) {
//			StoreModel storeModel = optStoreModel.get();
//			this.addIntToRow(row, storeModel.getPower().intValue());    //		power
//			row.add(storeModel.getModelDate().toString());              //		power_date
//		} else {
//			IntStream.range(0, 2).forEach(i -> row.add(null));
//		}
//		row.add(null);    //		using_area_sales_from
//		row.add(null);    //		using_area_total_from
//		row.add(null);    //		pseudo_power
//		row.add(null);    //		dollars_per_sqft
//
//		return row.toArray(new String[]{});
//	}
}

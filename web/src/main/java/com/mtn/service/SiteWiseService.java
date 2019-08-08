package com.mtn.service;

import com.mtn.config.SiteWiseSftpConfig;
import com.mtn.constant.RatingType;
import com.mtn.model.domain.*;
import com.mtn.model.utils.StoreUtil;
import com.mtn.repository.StoreBestVolumeRepository;
import com.mtn.util.MtnLogger;
import com.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.IntStream;

@Service
public class SiteWiseService {

	private final StoreService storeService;
	private final EntityManagerFactory entityManagerFactory;
	private final StoreBestVolumeRepository bestVolumeRepository;
	private final SiteWiseSftpConfig.SiteWiseSftpGateway gateway;


	@Autowired
	public SiteWiseService(StoreService storeService,
						   EntityManagerFactory entityManagerFactory,
						   StoreBestVolumeRepository bestVolumeRepository,
						   @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") SiteWiseSftpConfig.SiteWiseSftpGateway gateway) {
		this.storeService = storeService;
		this.entityManagerFactory = entityManagerFactory;
		this.bestVolumeRepository = bestVolumeRepository;
		this.gateway = gateway;
	}

	public File getCsvFile() throws Exception {
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

	private List<String[]> getCsvData() throws Exception {

		MtnLogger.info("Getting Store Strength Calculator: " + LocalDateTime.now());
		Function<Float, Float> getStoreStrength = this.getStoreStrengthCalculator();

		MtnLogger.info("Building document: " + LocalDateTime.now());

		long start = System.currentTimeMillis();

		List<String[]> rows = new ArrayList<>();

		// Add the header row to the document
		rows.add(this.getHeaderRow());

		// Set page size
		Pageable pageRequest = new PageRequest(0, 100);
		Page<Store> page;

		while (true) {
			long pageStart = System.currentTimeMillis();

			// Gets a page of stores
			page = this.getStores(pageRequest);

			page.forEach(store -> rows.add(this.getStoreRow(store, getStoreStrength)));

			long ellapsedPageSeconds = (System.currentTimeMillis() - pageStart) / 1000L;

			MtnLogger.info("Page " + (page.getNumber() + 1) + "/" + page.getTotalPages() + " took " + ellapsedPageSeconds + " seconds");

			if (page.hasNext() && page.getNumber() < 5) {
				pageRequest = page.nextPageable();
			} else {
				break;
			}
		}

		long ellapsedSeconds = (System.currentTimeMillis() - start) / 1000L;
		MtnLogger.info("Data collected after: " + ellapsedSeconds + " s");

		return rows;
	}

	private Function<Float, Float> getStoreStrengthCalculator() throws Exception {
		EntityManager em = entityManagerFactory.createEntityManager();
		try {
			Query query = em.createNativeQuery("SELECT \n" +
					"\tAVG(sb.volume_total / COALESCE(st.area_sales, b.default_sales_area)) AS dps_avg,\n" +
					"\tstddev(sb.volume_total / COALESCE(st.area_sales, b.default_sales_area)) AS dps_std_dev,\n" +
					"\tmax(sb.volume_total / COALESCE(st.area_sales, b.default_sales_area)) AS dps_max,\n" +
					"\tmin(sb.volume_total / COALESCE(st.area_sales, b.default_sales_area)) AS dps_min\n" +
					"FROM store_best_volume sb\n" +
					"JOIN store st ON st.store_id = sb.store_id\n" +
					"LEFT JOIN banner b ON b.banner_id = st.banner_id\n" +
					"WHERE sb.volume_total IS NOT NULL\n" +
					"AND st.store_type = 'ACTIVE'\n" +
					"AND COALESCE(st.area_sales, b.default_sales_area) IS NOT null\n" +
					"AND st.deleted_date IS NULL;");
			Object[] dpsStats = (Object[]) query.getSingleResult();
			Float dpsMax = ((BigDecimal) dpsStats[2]).floatValue();
			Float dpsMin = ((BigDecimal) dpsStats[3]).floatValue();

			return dps -> {
				float value = (dps - dpsMin) / (dpsMax - dpsMin);
				return Math.max(0.000f, Math.min(1.000f, value));
			};

		} catch (NoResultException e) {
			MtnLogger.error("Failed to get dps stats", e);
			throw new Exception("Failed to get store strength params");
		} finally {
			if (em.isOpen()) em.close();
		}
	}

	private Page<Store> getStores(Pageable pageable) {
		return this.storeService.findAllActiveAndFuture(pageable);
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

	private String[] getStoreRow(Store store, Function<Float, Float> getStoreStrength) {
		Banner banner = store.getBanner();
		StoreBestVolume bestVolume = this.bestVolumeRepository.findOneByStoreIdEquals(store.getId()).orElse(null);

		Integer salesArea = null;
		if (store.getAreaSales() != null) {
			salesArea = store.getAreaSales();
		} else if (banner != null && banner.getDefaultSalesArea() != null) {
			salesArea = banner.getDefaultSalesArea();
		}

		List<String> row = new ArrayList<>();
		row.add(store.getId().toString());
		row.add(String.valueOf(store.getSite().getLatitude()));
		row.add(String.valueOf(store.getSite().getLongitude()));
		row.add(store.getSite().getAddress1());
		row.add(store.getSite().getAddress2());
		row.add(store.getSite().getCity());
		row.add(store.getSite().getCounty());
		row.add(store.getSite().getState());
		row.add(store.getSite().getPostalCode());
		row.add(store.getSite().getQuad());
		row.add(store.getSite().getIntersectionStreetPrimary());
		row.add(store.getSite().getIntersectionStreetSecondary());
		row.add(store.getStoreType().name());
		row.add(store.getStoreName());
		row.add(store.getStoreNumber());

		Optional<StoreStatus> storeStatus = StoreUtil.getLatestStatusAsOfDateTime(store, LocalDateTime.now());
		if (storeStatus.isPresent()) {
			row.add(storeStatus.get().getStatus());
		} else {
			row.add(null);
		}

		// store_strength
		if (bestVolume != null && bestVolume.getVolumeTotal() != null && salesArea != null) {
			float dps = bestVolume.getVolumeTotal() / new Float(salesArea);
			float strength = getStoreStrength.apply(dps);
			row.add(String.valueOf(strength));
		} else {
			row.add(null);
		}

		String fit = "Conventional";
		boolean usingDefaultFit = true;
		if (store.getFit() != null) {
			fit = store.getFit();
			usingDefaultFit = false;
		} else if (banner != null) {
			fit = banner.getDefaultStoreFit();
		}
		row.add(fit);
		row.add(String.valueOf(usingDefaultFit));

		if (banner != null) {
			row.add(String.valueOf(banner.getId()));
			row.add(banner.getBannerName());
			Company company = banner.getCompany();
			if (company != null) {
				row.add(String.valueOf(company.getId()));
				row.add(company.getCompanyName());
				Company parentCompany = company.getParentCompany();
				if (parentCompany != null) {
					row.add(String.valueOf(parentCompany.getId()));
					row.add(parentCompany.getCompanyName());
				} else {
					IntStream.range(0, 2).forEach(i -> row.add(null));
				}
			} else {
				IntStream.range(0, 4).forEach(i -> row.add(null));
			}
		} else {
			IntStream.range(0, 6).forEach(i -> row.add(null));
		}
		this.addIntToRow(row, salesArea);

		this.addIntToRow(row, store.getAreaTotal());

		if (bestVolume != null) {
			row.add(bestVolume.getDecision());                      // volume_choice
			this.addIntToRow(row, bestVolume.getVolumeTotal());        // volume_total
			this.addDateToRow(row, bestVolume.getVolumeDate());        // volume_date
		} else {
			IntStream.range(0, 3).forEach(i -> row.add(null));
		}

		ShoppingCenter sc = store.getSite().getShoppingCenter();
		if (sc != null) {
			Optional<ShoppingCenterCasing> optScCasing = sc.getCasings().stream().max(Comparator.comparing(ShoppingCenterCasing::getCasingDate));
			if (optScCasing.isPresent()) {
				ShoppingCenterCasing mostRecentScCasing = optScCasing.get();
				row.add(null);    //		shopping_center_feature_score
				RatingType synergy = mostRecentScCasing.getRatingSynergy();
				if (synergy != null) {
					row.add(mostRecentScCasing.getRatingSynergy().name());    //		rating_synergy
				} else {
					row.add(null);
				}
				this.addIntToRow(row, mostRecentScCasing.getShoppingCenterSurvey().getTenantVacantCount());  //		tenant_vacant_count
				this.addIntToRow(row, mostRecentScCasing.getShoppingCenterSurvey().getTenantOccupiedCount());  //		tenant_occupied_count
			} else {
				IntStream.range(0, 4).forEach(i -> row.add(null));
			}
		} else {
			IntStream.range(0, 4).forEach(i -> row.add(null));
		}

		Optional<StoreCasing> optStoreCasing = store.getCasings().stream().max(Comparator.comparing(StoreCasing::getCasingDate));
		if (optStoreCasing.isPresent()) {
			this.addIntToRow(row, optStoreCasing.get().getStoreSurvey().getParkingSpaceCount());    //		parking_space_count
		} else {
			row.add(null);
		}
		this.addIntToRow(row, store.getLegacyLocationId());
		this.addIntToRow(row, store.getSite().getCbsaId());    //		cbsa_id
		row.add(null);    //		sc_condition_score

		row.add(null);    //		volume_confidence *

		Optional<StoreModel> optStoreModel = store.getModels().stream().max(Comparator.comparing(StoreModel::getModelDate));
		if (optStoreModel.isPresent()) {
			StoreModel storeModel = optStoreModel.get();
			this.addIntToRow(row, storeModel.getPower().intValue());    //		power
			row.add(storeModel.getModelDate().toString());              //		power_date
		} else {
			IntStream.range(0, 2).forEach(i -> row.add(null));
		}
		row.add(null);    //		using_area_sales_from
		row.add(null);    //		using_area_total_from
		row.add(null);    //		pseudo_power
		row.add(null);    //		dollars_per_sqft

		return row.toArray(new String[]{});
	}
}

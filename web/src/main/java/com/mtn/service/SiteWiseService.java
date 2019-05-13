package com.mtn.service;

import com.mtn.config.SiteWiseSftpConfig;
import com.mtn.model.domain.Banner;
import com.mtn.model.domain.Company;
import com.mtn.model.domain.Store;
import com.mtn.model.domain.StoreStatus;
import com.mtn.model.utils.StoreUtil;
import com.mtn.util.MtnLogger;
import com.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
public class SiteWiseService {

	private final StoreService storeService;
	private final SiteWiseSftpConfig.SiteWiseSftpGateway gateway;


	@Autowired
	public SiteWiseService(StoreService storeService,
						   @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") SiteWiseSftpConfig.SiteWiseSftpGateway gateway) {
		this.storeService = storeService;
		this.gateway = gateway;
	}

	@Async
	@Transactional(readOnly = true)
	public void buildAndTransmitExtraction() {
		try {
			File temp = File.createTempFile("temp-file-name", ".tmp");

			CSVWriter csvWriter = new CSVWriter(new FileWriter(temp));
			getCsvData().forEach(csvWriter::writeNext);
			csvWriter.close();

			this.gateway.sendToSftp(temp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private List<String[]> getCsvData() {
		long start = System.currentTimeMillis();

		List<String[]> rows = new ArrayList<>();

		rows.add(this.getHeaderRow());

		Pageable pageRequest = new PageRequest(0, 100);
		Page<Store> page;

		while (true) {
			page = this.getStores(pageRequest);
			page.forEach(store -> rows.add(this.getStoreRow(store)));
			if (page.hasNext() || page.getNumber() < 10) {
				pageRequest = page.nextPageable();
			} else {
				break;
			}
		}

		long ellapsedSeconds = (System.currentTimeMillis() - start) / 1000L;
		MtnLogger.info("Data collected after: " + ellapsedSeconds + " s" );

		return rows;
	}

	private Page<Store> getStores(Pageable pageable) {
		return this.storeService.findAllActiveAndFuture(pageable);
	}

	private String[] getHeaderRow() {
		return new String[]{"store_id", "latitude", "longitude", "address_1", "address_2", "city", "county", "state", "postal_code", "quad", "intersection_street_primary", "intersection_street_secondary", "store_type", "store_name", "store_number", "store_status", "store_strength", "store_fit", "using_default_fit", "banner_id", "banner_name", "company_id", "company_name", "parent_company_id", "parent_company_name", "area_sales", "area_total", "volume_choice", "volume_total", "volume_date", "shopping_center_feature_score", "rating_synergy", "tenant_vacant_count", "tenant_occupied_count", "parking_space_count", "legacy_location_id", "cbsa_id", "sc_condition_score", "volume_confidence", "power", "power_date", "using_area_sales_from", "using_area_total_from", "pseudo_power", "dollars_per_sqft"};
	}

	private String[] getStoreRow(Store store) {
		Banner banner = store.getBanner();

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

		row.add("");//		store_strength

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
				}
			}
		} else {
			IntStream.range(0, 6).forEach(i -> row.add(null));
		}

		row.add(String.valueOf(store.getAreaSales())); // TODO Get default if null
		row.add(String.valueOf(store.getAreaTotal())); // TODO Get default if null

		row.add(null);    //		volume_choice
		row.add(null);    //		volume_total
		row.add(null);    //		volume_date
		row.add(null);    //		shopping_center_feature_score
		row.add(null);    //		rating_synergy
		row.add(null);    //		tenant_vacant_count
		row.add(null);    //		tenant_occupied_count
		row.add(null);    //		parking_space_count
		row.add(String.valueOf(store.getLegacyLocationId()));
		row.add(null);    //		cbsa_id
		row.add(null);    //		sc_condition_score
		row.add(null);    //		volume_confidence
		row.add(null);    //		power
		row.add(null);    //		power_date
		row.add(null);    //		using_area_sales_from
		row.add(null);    //		using_area_total_from
		row.add(null);    //		pseudo_power
		row.add(null);    //		dollars_per_sqft

		return row.toArray(new String[]{});
	}
}

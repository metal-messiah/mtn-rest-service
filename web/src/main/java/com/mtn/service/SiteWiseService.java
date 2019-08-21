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
	private final EmptySitesRepository emptySitesRepository;
	private final AvgByBannerInCountyRepository avgByBannerInCountyRepository;
	private final AvgByBannerRepository avgByBannerRepository;
	private final AvgByCountyRepository avgByCountyRepository;
	private final AvgByFitInCountyRepository avgByFitInCountyRepository;
	private final AvgByFitRepository avgByFitRepository;
	private final AvgSalesAreaPercentByBannerRepository avgSalesAreaPercentByBannerRepository;
	private final AvgSalesAreaPercentByFitRepository avgSalesAreaPercentByFitRepository;

	private boolean sendingSftp = false;

	@Autowired
	public SiteWiseService(@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") SiteWiseSftpConfig.SiteWiseSftpGateway gateway,
						   ActiveAndFutureStoresRepository activeAndFutureStoresRepository,
						   EmptySitesRepository emptySitesRepository,
						   AvgByBannerInCountyRepository avgByBannerInCountyRepository,
						   AvgByBannerRepository avgByBannerRepository,
						   AvgByCountyRepository avgByCountyRepository,
						   AvgByFitInCountyRepository avgByFitInCountyRepository,
						   AvgByFitRepository avgByFitRepository,
						   AvgSalesAreaPercentByBannerRepository avgSalesAreaPercentByBannerRepository,
						   AvgSalesAreaPercentByFitRepository avgSalesAreaPercentByFitRepository) {
		this.gateway = gateway;
		this.activeAndFutureStoresRepository = activeAndFutureStoresRepository;
		this.emptySitesRepository = emptySitesRepository;
		this.avgByBannerInCountyRepository = avgByBannerInCountyRepository;
		this.avgByBannerRepository = avgByBannerRepository;
		this.avgByCountyRepository = avgByCountyRepository;
		this.avgByFitInCountyRepository = avgByFitInCountyRepository;
		this.avgByFitRepository = avgByFitRepository;
		this.avgSalesAreaPercentByBannerRepository = avgSalesAreaPercentByBannerRepository;
		this.avgSalesAreaPercentByFitRepository = avgSalesAreaPercentByFitRepository;
	}

	public File getActiveAndFutureStoresFile() throws IOException {
		List<String[]> data = getActiveAndFutureStoreData();
		return this.getFile("active-and-future-stores", data);
	}

	public File getEmptySitesFile() throws IOException {
		return this.getFile("empty-sites", getEmptySitesData());
	}

	private File getFile(String fileName, List<String[]> data) throws IOException {
		File temp = File.createTempFile(fileName, ".csv");

		CSVWriter csvWriter = new CSVWriter(new FileWriter(temp));
		data.forEach(csvWriter::writeNext);
		csvWriter.close();
		return temp;
	}

	@Async
	@Transactional(readOnly = true)
	public void buildAndTransmitActiveAndFutureStoreData() {
		try {
			// Prevent multiple requests from being submitted at the same time
			if (!this.sendingSftp) {
				this.sendingSftp = true;
				File file = getActiveAndFutureStoresFile();
				this.gateway.sendToSftp(file);
				if (file.delete()) {
					MtnLogger.info("Successfully deleted temp file.");
				} else {
					MtnLogger.error("Failed to delete temp file - active_and_future");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.sendingSftp = false;
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

	private List<String[]> getActiveAndFutureStoreData() {
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
			if (a.getAreaTotal() != null) {
				if (a.getBannerId() != null) {
					Optional<AvgSalesAreaPercentByBanner> abo = avgSalesAreaPercentByBanner.stream().filter(ab -> ab.getBannerId().equals(a.getBannerId())).findAny();
					if (abo.isPresent() && abo.get().getAvgSalesPercent() != null) {
						Integer salesArea = nearestThousand(abo.get().getAvgSalesPercent() * a.getAreaTotal());
						return new Pair<>("Calculated from % of total avg by banner", salesArea);
					}
				}
				if (a.getStoreFit() != null) {
					Optional<AvgSalesAreaPercentByFit> abo = avgSalesAreaPercentByFit.stream().filter(ab -> ab.getStoreFit().equals(a.getStoreFit())).findAny();
					if (abo.isPresent() && abo.get().getAvgSalesPercent() != null) {
						Integer salesArea = nearestThousand(abo.get().getAvgSalesPercent() * a.getAreaTotal());
						return new Pair<>("Calculated from % of total avg by fit", salesArea);
					}
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
		Function<ActiveAndFutureStores, Pair<String, Integer>> getAreaTotal = a -> {
			if (a.getAreaTotal() != null) {
				return new Pair<>("DB", nearestThousand(a.getAreaTotal()));
			}
			if (a.getAreaSales() != null) {
				if (a.getBannerId() != null) {
					Optional<AvgSalesAreaPercentByBanner> abo = avgSalesAreaPercentByBanner.stream().filter(ab -> ab.getBannerId().equals(a.getBannerId())).findAny();
					if (abo.isPresent() && abo.get().getAvgSalesPercent() != null) {
						Integer totalArea = nearestThousand(a.getAreaSales() / abo.get().getAvgSalesPercent());
						return new Pair<>("Calculated from % of total avg by banner", totalArea);
					}
				}
				if (a.getStoreFit() != null) {
					Optional<AvgSalesAreaPercentByFit> abo = avgSalesAreaPercentByFit.stream().filter(ab -> ab.getStoreFit().equals(a.getStoreFit())).findAny();
					if (abo.isPresent() && abo.get().getAvgSalesPercent() != null) {
						Integer totalArea = nearestThousand(a.getAreaSales() / abo.get().getAvgSalesPercent());
						return new Pair<>("Calculated from % of total avg by fit", totalArea);
					}
				}
			}
			if (a.getBannerId() != null) {
				Optional<AvgByBannerInCounty> abo = avgByBannerInCounty.stream().filter(ab -> ab.getBannerId().equals(a.getBannerId()) && sameCounty(a, ab)).findAny();
				if (abo.isPresent() && abo.get().getAreaTotal() != null) {
					Integer totalArea = nearestThousand(abo.get().getAreaTotal());
					return new Pair<>("Avg by Banner in County", totalArea);
				}
				Optional<AvgByBanner> abb = avgByBanner.stream().filter(ab -> ab.getBannerId().equals(a.getBannerId())).findAny();
				if (abb.isPresent() && abb.get().getAreaTotal() != null) {
					Integer totalArea = nearestThousand(abb.get().getAreaTotal());
					return new Pair<>("Avg by Banner in Nation", totalArea);
				}
			}
			if (a.getStoreFit() != null) {
				Optional<AvgByFitInCounty> abo = avgByFitInCounty.stream().filter(ab -> ab.getStoreFit().equals(a.getStoreFit()) && sameCounty(a, ab)).findAny();
				if (abo.isPresent() && abo.get().getAreaTotal() != null) {
					Integer totalArea = nearestThousand(abo.get().getAreaTotal());
					return new Pair<>("Avg by Fit in County", totalArea);
				}
				Optional<AvgByFit> abf = avgByFit.stream().filter(ab -> ab.getStoreFit().equals(a.getStoreFit())).findAny();
				if (abf.isPresent() && abf.get().getAreaTotal() != null) {
					Integer totalArea = nearestThousand(abf.get().getAreaTotal());
					return new Pair<>("Avg by Fit in Nation", totalArea);
				}
			}
			Optional<AvgByCounty> abo = avgByCounty.stream().filter(ab -> sameCounty(a, ab)).findAny();
			if (abo.isPresent() && abo.get().getAreaTotal() != null) {
				Integer totalArea = nearestThousand(abo.get().getAreaTotal());
				return new Pair<>("Avg by County", totalArea);
			}
			return null;
		};
		Function<Pair<ActiveAndFutureStores, Pair<String, Integer>>, Pair<String, Integer>> getVolume = aa -> {
			ActiveAndFutureStores a = aa.getKey();
			Pair<String, Integer> salesArea = aa.getValue();

			if (a.getVolumeTotal() != null) {
				return new Pair<>(a.getVolumeChoice(), nearestThousand(a.getVolumeTotal()));
			}
			if (salesArea != null && salesArea.getValue() != null) {
				Optional<AvgByBannerInCounty> abic = avgByBannerInCounty.stream().filter(ab -> ab.getBannerId().equals(a.getBannerId()) && sameCounty(a, ab)).findAny();
				if (abic.isPresent() && abic.get().getDpsf() != null) {
					Integer volume = nearestThousand(abic.get().getDpsf() * salesArea.getValue());
					return new Pair<>(salesArea.getKey() + " * (Avg Banner in County $/sqft)", volume);
				}
				Optional<AvgByBanner> abb = avgByBanner.stream().filter(ab -> ab.getBannerId().equals(a.getBannerId())).findAny();
				if (abb.isPresent() && abb.get().getDpsf() != null) {
					Integer volume = nearestThousand(abb.get().getDpsf() * salesArea.getValue());
					return new Pair<>(salesArea.getKey() + " * (Avg Banner in Nation $/sqft)", volume);
				}
				Optional<AvgByFitInCounty> afic = avgByFitInCounty.stream().filter(ab -> ab.getStoreFit().equals(a.getStoreFit()) && sameCounty(a, ab)).findAny();
				if (afic.isPresent() && afic.get().getDpsf() != null) {
					Integer volume = nearestThousand(afic.get().getDpsf() * salesArea.getValue());
					return new Pair<>(salesArea.getKey() + " * (Avg Fit in County $/sqft)", volume);
				}
				Optional<AvgByFit> af = avgByFit.stream().filter(ab -> ab.getStoreFit().equals(a.getStoreFit())).findAny();
				if (af.isPresent() && af.get().getDpsf() != null) {
					Integer volume = nearestThousand(af.get().getDpsf() * salesArea.getValue());
					return new Pair<>(salesArea.getKey() + " * (Avg Fit in Nation $/sqft)", volume);
				}
				Optional<AvgByCounty> ac = avgByCounty.stream().filter(ab -> sameCounty(a, ab)).findAny();
				if (ac.isPresent() && ac.get().getDpsf() != null) {
					Integer volume = nearestThousand(ac.get().getDpsf() * salesArea.getValue());
					return new Pair<>(salesArea.getKey() + " * (Avg County $/sqft)", volume);
				}
			} else {
				Optional<AvgByBannerInCounty> abic = avgByBannerInCounty.stream().filter(ab -> ab.getBannerId().equals(a.getBannerId()) && sameCounty(a, ab)).findAny();
				if (abic.isPresent() && abic.get().getVolume() != null) {
					Integer volume = nearestThousand(abic.get().getVolume());
					return new Pair<>("Avg by Banner in County", volume);
				}
				Optional<AvgByBanner> abb = avgByBanner.stream().filter(ab -> ab.getBannerId().equals(a.getBannerId())).findAny();
				if (abb.isPresent() && abb.get().getVolume() != null) {
					Integer volume = nearestThousand(abb.get().getVolume());
					return new Pair<>("Avg by Banner in Nation", volume);
				}
				Optional<AvgByFitInCounty> afic = avgByFitInCounty.stream().filter(ab -> ab.getStoreFit().equals(a.getStoreFit()) && sameCounty(a, ab)).findAny();
				if (afic.isPresent() && afic.get().getVolume() != null) {
					Integer volume = nearestThousand(afic.get().getVolume());
					return new Pair<>("Avg by Fit in County", volume);
				}
				Optional<AvgByFit> af = avgByFit.stream().filter(ab -> ab.getStoreFit().equals(a.getStoreFit())).findAny();
				if (af.isPresent() && af.get().getVolume() != null) {
					Integer volume = nearestThousand(af.get().getVolume());
					return new Pair<>("Avg by Fit in Nation", volume);
				}
				Optional<AvgByCounty> ac = avgByCounty.stream().filter(ab -> sameCounty(a, ab)).findAny();
				if (ac.isPresent() && ac.get().getVolume() != null) {
					Integer volume = nearestThousand(ac.get().getVolume());
					return new Pair<>("Avg by County", volume);
				}
			}
			return null;
		};
		Function<Pair<ActiveAndFutureStores, Float>, Integer> getPseudoPower = aa -> {
			ActiveAndFutureStores a = aa.getKey();
			Float dpsf = aa.getValue();
			if (dpsf != null) {
				Optional<AvgByCounty> ac = avgByCounty.stream().filter(ab -> sameCounty(a, ab)).findAny();
				if (ac.isPresent() && ac.get().getDpsf() != null && ac.get().getDpsfMin() != null && ac.get().getDpsfMax() != null) {
					Float min = ac.get().getDpsfMin();
					Float max = ac.get().getDpsfMax();
					Float avg = ac.get().getDpsf();

					float range = min.equals(max) ? 1 : max - min;
					int pseudoPower = Math.round((dpsf - avg) / range * 100f) + 100;

					// Pseudo power is capped between 0 and 200 (100 should be avg)
					return Math.min(200, Math.max(pseudoPower, 0));
				}
			}
			return null;
		};

		MtnLogger.info("Building document: " + LocalDateTime.now());

		long start = System.currentTimeMillis();

		List<String[]> rows = new ArrayList<>();

		// Add the header row to the document
		rows.add(this.getActiveAndFutureStoresHeaderRow());

		// Set page size
		Pageable pageRequest = new PageRequest(0, 50000);
		Page<ActiveAndFutureStores> page;

		while (true) {
			long pageStart = System.currentTimeMillis();

			// Gets a page of stores
			page = this.activeAndFutureStoresRepository.findAll(pageRequest);

			long pageRetrieved = System.currentTimeMillis();
			MtnLogger.info("Page " + (page.getNumber() + 1) + "/" + page.getTotalPages() + " retrieved in  " + ((pageRetrieved - pageStart) / 1000L) + " seconds");

			page.forEach(store -> rows.add(this.getActiveAndFutureStoresRow(store, getAreaSales, getAreaTotal, getVolume, getPseudoPower)));

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

	private List<String[]> getEmptySitesData() {

		long start = System.currentTimeMillis();

		List<String[]> rows = new ArrayList<>();

		// Add the header row to the document
		rows.add(this.getEmptySitesHeaderRow());

		// Set page size
		Pageable pageRequest = new PageRequest(0, 50000);
		Page<EmptySites> page;

		while (true) {
			long pageStart = System.currentTimeMillis();

			// Gets a page of stores
			page = this.emptySitesRepository.findAll(pageRequest);

			long pageRetrieved = System.currentTimeMillis();
			MtnLogger.info("Page " + (page.getNumber() + 1) + "/" + page.getTotalPages() + " retrieved in  " + ((pageRetrieved - pageStart) / 1000L) + " seconds");

			page.forEach(store -> rows.add(this.getEmptySitesRow(store)));

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

	private String[] getActiveAndFutureStoresHeaderRow() {
		return new String[]{"store_id", "latitude", "longitude", "address_1", "address_2", "city", "county", "state", "postal_code", "quad", "intersection_street_primary", "intersection_street_secondary", "store_type", "store_name", "store_number", "store_status", "store_strength", "store_fit", "using_default_fit", "banner_id", "banner_name", "company_id", "company_name", "parent_company_id", "parent_company_name", "area_sales", "area_total", "volume_choice", "volume_total", "volume_date", "shopping_center_feature_score", "rating_synergy", "tenant_vacant_count", "tenant_occupied_count", "parking_space_count", "legacy_location_id", "cbsa_id", "sc_condition_score", "volume_confidence", "power", "power_date", "using_area_sales_from", "using_area_total_from", "pseudo_power", "dollars_per_sqft"};
	}

	private String[] getEmptySitesHeaderRow() {
		return new String[]{"site_id", "latitude", "longitude", "address", "city", "county", "state", "postal_code", "intersection_type", "quad", "primary_intersection_street", "secondary_intersection_street", "cbsa_id", "planned_store_id", "planned_store_name", "planned_store_opening_date", "planned_store_total_area"};
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

	private String[] getActiveAndFutureStoresRow(ActiveAndFutureStores s,
												 Function<ActiveAndFutureStores, Pair<String, Integer>> getAreaSales,
												 Function<ActiveAndFutureStores, Pair<String, Integer>> getAreaTotal,
												 Function<Pair<ActiveAndFutureStores, Pair<String, Integer>>, Pair<String, Integer>> getVolume,
												 Function<Pair<ActiveAndFutureStores, Float>, Integer> getPseudoPower) {
		Pair<String, Integer> salesArea = getAreaSales.apply(s);
		Pair<String, Integer> totalArea = getAreaTotal.apply(s);
		Pair<String, Integer> volume = getVolume.apply(new Pair<>(s, salesArea));

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
		addFloatToRow(row, s.getStoreStrength());
		if (s.getStoreFit() != null && s.getStoreFit().equals("Asian")) {
			row.add("Conventional");
		} else if (s.getStoreFit() != null && s.getStoreFit().equals("Natural Foods")) {
			row.add("Natural/Organic");
		} else {
			row.add(s.getStoreFit());
		}
		row.add(s.getUsingDefaultFit());
		this.addIntToRow(row, s.getBannerId());
		row.add(s.getBannerName());
		this.addIntToRow(row, s.getCompanyId());
		row.add(s.getCompanyName());
		this.addIntToRow(row, s.getParentCompanyId());
		row.add(s.getParentCompanyName());

		// Sales Area
		if (salesArea != null) {
			this.addIntToRow(row, salesArea.getValue());
		} else {
			row.add(null);
		}

		// Total Area
		if (totalArea != null) {
			this.addIntToRow(row, totalArea.getValue());
		} else {
			row.add(null);
		}

		// Volume
		if (volume != null) {
			row.add(volume.getKey());
			addIntToRow(row, volume.getValue());
		} else {
			row.add(null);
			row.add(null);
		}

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
			row.add(salesArea.getKey());    //	using_area_sales_from
		} else {
			row.add(null);
		}
		if (totalArea != null) {
			row.add(totalArea.getKey());    //	using_area_total_from
		} else {
			row.add(null);
		}

		// Pseudo_power && // Dollars per sqft
		if (volume != null && volume.getValue() != null && salesArea != null && salesArea.getValue() != null && salesArea.getValue() != 0) {
			float dpsf = volume.getValue().floatValue() / salesArea.getValue().floatValue();
			Integer pseudoPower = getPseudoPower.apply(new Pair<>(s, dpsf));
			addIntToRow(row, pseudoPower);
			addFloatToRow(row, Math.round(dpsf * 100f) / 100f);
		} else {
			row.add(null);
			row.add(null);
		}

		return row.toArray(new String[]{});
	}

	private String[] getEmptySitesRow(EmptySites s) {
		List<String> row = new ArrayList<>();
		addIntToRow(row, s.getSiteId());
		addFloatToRow(row, s.getLatitude());
		addFloatToRow(row, s.getLongitude());
		row.add(s.getAddress());
		row.add(s.getCity());
		row.add(s.getCounty());
		row.add(s.getState());
		row.add(s.getPostalCode());
		row.add(s.getIntersectionType());
		row.add(s.getQuad());
		row.add(s.getPrimaryIntersectionStreet());
		row.add(s.getSecondaryIntersectionStreet());
		addIntToRow(row, s.getCbsaId());
		addIntToRow(row, s.getPlannedStoreId());
		row.add(s.getPlannedStoreName());
		addDateToRow(row, s.getPlannedStoreOpeningDate());
		addIntToRow(row, s.getPlannedStoreTotalArea());

		return row.toArray(new String[]{});
	}

}

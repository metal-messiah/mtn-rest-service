package com.mtn.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.mtn.interceptors.HeaderRequestInterceptor;
import com.mtn.model.domain.*;
import com.mtn.model.view.BannerSourceView;
import com.mtn.model.view.StoreSourceView;
import com.mtn.repository.SourceRepository;
import com.mtn.util.MtnLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ChainXYService {

	private final String CHAINXY_SOURCE_NAME = "ChainXY";
	private final BannerSourceService bannerSourceService;
	private final StoreSourceService storeSourceService;
	private final SourceRepository sourceRepository;

	@Value("${chainXyApiKey}")
	private String chainXyApiKey;

	@Autowired
	public ChainXYService(BannerSourceService bannerSourceService,
						  StoreSourceService storeSourceService,
						  SourceRepository sourceRepository) {
		this.bannerSourceService = bannerSourceService;
		this.storeSourceService = storeSourceService;
		this.sourceRepository = sourceRepository;
	}

	public JsonNode getChainXyLocationForStoreSource(Integer storeSourceId) throws IOException {
		StoreSource storeSource = this.storeSourceService.findOne(storeSourceId);

		UriComponents request = UriComponentsBuilder.newInstance()
				.scheme("https")
				.host("location.chainxy.com")
				.path("/api/ChainLocations/" + storeSource.getSourceNativeId())
				.build();

		ResponseEntity<String> response = getRestTemplate().getForEntity(request.toUriString(), String.class);

		JsonNode jsonRoot = new ObjectMapper().readTree(response.getBody());

		return jsonRoot.get("Record");
	}

	/*
	Retrieves one location record from the ChainXY API by id
	 */
	public ResponseEntity<String> getChainXyLocationById(String id) throws IOException {
		// TODO Add API key as header, Build query
		UriComponents featureQueryUri = UriComponentsBuilder.newInstance()
				.scheme("https")
				.host("location.chainxy.com")
				.path("/api/Locations")
				.queryParam("query", "{id: " + id + "}")
				.build();

		return new RestTemplate().getForEntity(featureQueryUri.toUriString(), String.class);
	}

	@Async
	public void updateDbSources(UserProfile userProfile, Boolean all) {
		try {
			LocalDateTime start = LocalDateTime.now();
			MtnLogger.info("ChainXY Sync: Started at " + start.toString());

			Source source = this.sourceRepository.findFirstBySourceName("ChainXY");

			ChainXyPagedRequest pagedRequest;
			if (all) {
				pagedRequest = this.getAllChainXyChains();
			} else {
				pagedRequest = this.getLatestChainXyChains(source);
			}

			if (pagedRequest != null) {
				this.updateBannerSources(pagedRequest);
			} else {
				MtnLogger.info("ChainXY Sync: 0 Chains updated");
			}

			source.setLastSyncDate(start);
			source.setUpdatedBy(userProfile);
			sourceRepository.save(source);

			MtnLogger.info("ChainXY Sync: Completed at " + LocalDateTime.now().toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void updateBannerSources(ChainXyPagedRequest pagedRequest) throws IOException {
		do {
			ArrayNode chains = pagedRequest.next();

			// For each ChainXY chain,
			chains.forEach(chainNode -> {
				// Parse data for chain
				String chainId = String.valueOf(chainNode.get("Id").intValue());
				String url = chainNode.get("Url").textValue();
				String chainName = chainNode.get("Name").textValue();
				String lastScrapeDateString = chainNode.get("LastScrapeDate").textValue();
				LocalDateTime lastScrapeDate = LocalDateTime.parse(lastScrapeDateString).withNano(0);

				// Find or create a banner_source record
				BannerSource bs = this.bannerSourceService.findOneBySourceNativeIdUsingSpecs(this.CHAINXY_SOURCE_NAME, chainId)
						.orElseGet(() -> {
							BannerSourceView addRequest = new BannerSourceView();
							addRequest.setSourceName(this.CHAINXY_SOURCE_NAME);
							addRequest.setSourceNativeId(chainId);
							addRequest.setSourceUrl(url);
							addRequest.setSourceBannerName(chainName);
							MtnLogger.info("ChainXY Sync: Created BannerSource for " + chainName);
							return this.bannerSourceService.addOne(addRequest);
						});

				// Check the chain's stores if necessary, and update the banner_source to reflect that
				if (bs.getSourceEditedDate() == null || bs.getSourceEditedDate().isBefore(lastScrapeDate)) {
					this.updateStoreSourcesForBanner(bs);

					// Update banner_source record with lastScrape Date
					bs.setSourceEditedDate(lastScrapeDate);
					this.bannerSourceService.updateOne(bs);

					MtnLogger.info("ChainXY Sync: Updated store sources for " + chainName);
				} else {
					MtnLogger.info("ChainXY Sync: " + chainName + " already up to date");
				}
			});
		} while (pagedRequest.hasNext());
	}

	private void updateStoreSourcesForBanner(BannerSource bs) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
		try {
			// Get Chain Stores
			ChainXyPagedRequest pagedRequest = this.getChainStores(bs.getSourceNativeId());

			Set<String> hashIds = new HashSet<>();

			do {
				ArrayNode chainStores = pagedRequest.next();

				// Add Store Sources for new stores
				chainStores.forEach(storeNode -> {
					// Parse Data for Store
					String hashId = storeNode.get("HashId").textValue();

					hashIds.add(hashId);

					String sourceStoreName = storeNode.get("StoreName").textValue();
					String firstAppearedString = storeNode.get("FirstAppeared").textValue();
					String lastUpdateString = storeNode.get("LastUpdate").textValue();
					LocalDateTime lastUpdateDate = LocalDate.parse(lastUpdateString, formatter).atStartOfDay();

					// Find or create storeSource record for store
					StoreSource storeSource = this.storeSourceService.findOneBySourceNativeIdUsingSpecs(this.CHAINXY_SOURCE_NAME, hashId).orElseGet(() -> {
						StoreSourceView storeSourceRequest = new StoreSourceView();
						storeSourceRequest.setSourceName(this.CHAINXY_SOURCE_NAME);
						storeSourceRequest.setSourceNativeId(hashId);
						storeSourceRequest.setSourceStoreName(sourceStoreName);
						if (firstAppearedString.isEmpty()) {
							storeSourceRequest.setSourceCreatedDate(lastUpdateDate);
						} else {
							LocalDateTime firstAppearedDate = LocalDate.parse(firstAppearedString, formatter).atStartOfDay();
							storeSourceRequest.setSourceCreatedDate(firstAppearedDate);
						}
						storeSourceRequest.setSourceEditedDate(lastUpdateDate);
						return this.storeSourceService.addOne(storeSourceRequest);
					});
					if (storeSource.getBannerSource() == null) {
						storeSource.setBannerSource(bs);
						this.storeSourceService.updateOne(storeSource);
					}
				});
			} while (pagedRequest.hasNext());

			// Check for deletions
			// If store source hashId no longer found in ChainXY results
			List<StoreSource> deletedSources = storeSourceService.findAllOfBannerSourceWhereNativeIdNotInSet(bs.getId(), hashIds);
			// Mark the store source as having been deleted from the source by setting the sourceDeletedDate
			deletedSources.forEach(storeSource -> {
				storeSource.setSourceDeletedDate(bs.getSourceEditedDate());
				storeSourceService.updateOne(storeSource);
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private ChainXyPagedRequest getChainStores(String chainId) {
		UriComponents request = UriComponentsBuilder.newInstance()
				.scheme("https")
				.host("location.chainxy.com")
				.path("/api/Locations")
				.queryParam("fields", "HashId,StoreName,ChainName,FirstAppeared,LastUpdate,LastSeen")
				.queryParam("model.chainId", chainId)
				.queryParam("model.north", "90")
				.queryParam("model.south", "-90")
				.queryParam("model.east", "180")
				.queryParam("model.west", "-180")
				.queryParam("model.includeComingSoon", "true")
				.queryParam("model.includeClosed", "true")
				.queryParam("model.includeDistributors", "false")
				.queryParam("model.includeSubChains", "false")
				.queryParam("model.limit", "200")
				.queryParam("model.page", "{page}")
				.build();

		Map<String, String> vars = new HashMap<>();

		return new ChainXyPagedRequest(request, vars);
	}

	public List<Integer> getIdsOfChainsScrapedSince(LocalDateTime dateTime) throws IOException {

		UriComponents request = UriComponentsBuilder.newInstance()
				.scheme("https")
				.host("location.chainxy.com")
				.path("/api/ChainScrapes")
				.queryParam("page", "{page}")
				.queryParam("query", "{query}")
				.build(); // Grocery Category

		Map<String, String> vars = new HashMap<>();

		// IMPORTANT! JSON query object must be injected as a uri variable, because otherwise Spring interprets the
		// curly braces as a variable placeholder (even if they are encoded)
		vars.put("query", "{\"Chain/Categories\":{\"Id\":154}, \"Chain/Countries\":{\"Code\":\"US\"}, \"Latest\":true, \"RunDate\":\">" + dateTime + "\"}");

		List<Integer> chainIds = new ArrayList<>();
		ChainXyPagedRequest pagedRequest = new ChainXyPagedRequest(request, vars);
		do {
			ArrayNode chainScrapes = pagedRequest.next();
			chainScrapes.forEach(cs -> chainIds.add(cs.get("Chain").get("Id").intValue()));
		} while (pagedRequest.hasNext());

		return chainIds;
	}

	private ChainXyPagedRequest getLatestChainXyChains(Source source) throws IOException {

		// Get ids of chains that have been scraped since last sync date
		List<Integer> updatedChainIds = this.getIdsOfChainsScrapedSince(source.getLastSyncDate());

		if (updatedChainIds.size() > 100) {
			return this.getAllChainXyChains();
		} else if (updatedChainIds.size() > 0) {
			UriComponents request = UriComponentsBuilder.newInstance()
					.scheme("https")
					.host("location.chainxy.com")
					.path("/api/Chains")
					.queryParam("fields", "Name,LastScrapeDate,Id,Url")
					.queryParam("page", "{page}")
					.queryParam("query", "{query}")
					.build();

			Map<String, String> vars = new HashMap<>();

			// IMPORTANT! JSON query object must be injected as a uri variable, because otherwise Spring interprets the
			// curly braces as a variable placeholder (even if they are encoded)
			vars.put("query", "{\"Id\":" + updatedChainIds.toString() + "}");

			return new ChainXyPagedRequest(request, vars);
		} else {
			return null;
		}
	}

	private ChainXyPagedRequest getAllChainXyChains() {
		UriComponents request = UriComponentsBuilder.newInstance()
				.scheme("https")
				.host("location.chainxy.com")
				.path("/api/Chains")
				.queryParam("fields", "Name,LastScrapeDate,Id,Url")
				.queryParam("page", "{page}")
				.queryParam("query", "{query}")
				.build();

		Map<String, String> vars = new HashMap<>();

		// IMPORTANT! JSON query object must be injected as a uri variable, because otherwise Spring interprets the
		// curly braces as a variable placeholder (even if they are encoded)
		vars.put("query", "{\"Categories\":{\"Id\":154}, \"Countries\":{\"Code\":\"US\"}}");

		return new ChainXyPagedRequest(request, vars);
	}

	private class ChainXyPagedRequest {
		private int totalPages = 1;
		private int currentPageIndex = 0;
		private UriComponents requestComponents;
		private Map<String, String> vars;

		public ChainXyPagedRequest(UriComponents requestComponents, Map<String, String> vars) {
			this.requestComponents = requestComponents;
			this.vars = vars;
		}

		public boolean hasNext() {
			return this.currentPageIndex < totalPages;
		}

		public ArrayNode next() throws IOException {
			// Set the page to GET
			this.vars.put("page", String.valueOf(this.currentPageIndex));

			// Submit GET request
			ResponseEntity<String> response = getRestTemplate().getForEntity(this.requestComponents.toUriString(), String.class, vars);

			// Read response body into a JSON object
			JsonNode jsonRoot = new ObjectMapper().readTree(response.getBody());

			if (this.currentPageIndex == 0) {
				// Set the total number of pages for further requests
				this.totalPages = jsonRoot.get("Pages").intValue();
			}

			this.currentPageIndex += 1;

			// Extract the records from the JSON object
			return (ArrayNode) jsonRoot.path("Records");
		}
	}

	private ArrayNode getRecords(UriComponents request, Map<String, String> vars) throws IOException {
		ArrayNode records = null;

		int currentPage = 0;
		int totalPages = 1;
		do {
			// Set the page to GET
			vars.put("page", String.valueOf(currentPage));

			// Submit GET request
			ResponseEntity<String> response = this.getRestTemplate().getForEntity(request.toUriString(), String.class, vars);

			// Read response body into a JSON object
			JsonNode jsonRoot = new ObjectMapper().readTree(response.getBody());

			// Set the total number of pages for further requests
			totalPages = jsonRoot.get("Pages").intValue();

			// Extract the records from the JSON object
			ArrayNode pageElements = (ArrayNode) jsonRoot.path("Records");

			// If this is the first page, save as initial ArrayNode, else add this page's records to the others
			if (records == null) {
				records = pageElements;
			} else {
				records.addAll(pageElements);
			}
		} while (++currentPage < totalPages);
		return records;
	}

	private RestTemplate getRestTemplate() {
		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
		interceptors.add(new HeaderRequestInterceptor("Accept", MediaType.APPLICATION_JSON_VALUE));
		interceptors.add(new HeaderRequestInterceptor("X-ApiKey", this.chainXyApiKey));
		interceptors.add(new HeaderRequestInterceptor("X-Application", "Getting Chains"));

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setInterceptors(interceptors);
		return restTemplate;
	}

}

package com.mtn.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mtn.model.domain.*;
import com.mtn.model.utils.StoreUtil;
import com.mtn.model.view.*;
import com.mtn.util.MtnLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class PlannedGroceryService {

	private final StoreSourceService storeSourceService;
	private final StoreSurveyService storeSurveyService;
	private final ShoppingCenterService shoppingCenterService;
	private final StoreService storeService;
	private final SiteService siteService;
	private final StoreStatusService storeStatusService;
	private final SecurityService securityService;

	@Value("${planned-grocery.client_id}")
	private String pgClientId;

	@Value("${planned-grocery.client_secret}")
	private String pgClientSecret;

	private Map<Integer, String> statusMap;

	@Autowired
	public PlannedGroceryService(StoreSourceService storeSourceService, StoreSurveyService storeSurveyService,
								 ShoppingCenterService shoppingCenterService, StoreService storeService,
								 SiteService siteService, StoreStatusService storeStatusService, SecurityService securityService) {
		this.storeSourceService = storeSourceService;
		this.storeSurveyService = storeSurveyService;
		this.shoppingCenterService = shoppingCenterService;
		this.storeService = storeService;
		this.siteService = siteService;
		this.storeStatusService = storeStatusService;
		this.securityService = securityService;
	}

	public ResponseEntity<String> getFeatureByObjectId(String objectId) throws IOException {
		ResponseEntity<String> token = this.getAccessToken();
		JsonNode keyRoot = new ObjectMapper().readTree(token.getBody());
		JsonNode accessTokenField = keyRoot.path("access_token");
		String accessToken = accessTokenField.textValue();

		UriComponents featureQueryUri = UriComponentsBuilder.newInstance()
				.scheme("https")
				.host("services1.arcgis.com")
				.path("/aUqH6d4IMis39TBB/arcgis/rest/services/BD_FUTURE_RETAIL/FeatureServer/0/query")
				.queryParam("where", String.format("OBJECTID='%s'", objectId))
				.queryParam("outFields", "*")
				.queryParam("returnGeometry", "true")
				.queryParam("f", "pjson")
				.queryParam("token", accessToken)
				.queryParam("outSR", "4326")
				.build();

		return new RestTemplate().getForEntity(featureQueryUri.toUriString(), String.class);
	}

	public void addAndUpdateSourcesFromPlannedGrocery(UserProfile validator) {
		MtnLogger.info("Running Planned Grocery update");
		Date start = new Date();

		try {
			JsonNode featureNodes = this.getFeaturesEditedSince(this.storeSourceService.getMaxSourceEditedDate("Planned Grocery"));
			if (featureNodes.isArray()) {
				featureNodes.forEach(f -> processFeatureNode(f, validator));
			} else {
				MtnLogger.warn("Failed to find features in Planned Grocery results");
			}
		} catch (IOException e) {
			MtnLogger.warn("Failed to retrieve parse response from Planned Grocery", e);
		}

		long duration = (new Date().getTime() - start.getTime());
		MtnLogger.info(String.format("Finished running Planned Grocery update in %d ms by %s", duration, validator.getEmail()));
	}

	public PlannedGroceryUpdatable getUpdatableByStoreId(Integer storeId) {
		Store store = storeService.findOne(storeId);
		return new PlannedGroceryUpdatable(store);
	}

	public PlannedGroceryUpdatable getUpdatableBySiteId(Integer siteId) {
		Site site = siteService.findOne(siteId);
		return new PlannedGroceryUpdatable(site);
	}

	public PlannedGroceryUpdatable getUpdatableByShoppingCenterId(Integer shoppingCenterId) {
		ShoppingCenter shoppingCenter = shoppingCenterService.findOne(shoppingCenterId);
		return new PlannedGroceryUpdatable(shoppingCenter);
	}

	@Transactional
	public Store updateFromUpdatable(PlannedGroceryUpdatable updatable) {
		Store store;
		if (updatable.getShoppingCenterId() == null) {
			ShoppingCenter sc = shoppingCenterService.createNew();
			Site site = siteService.createOne(sc, updatable.getLatitude(), updatable.getLongitude());
			store = this.storeService.createNewStoreForSite(site);
		} else if (updatable.getSiteId() == null) {
			ShoppingCenter sc = shoppingCenterService.findOne(updatable.getShoppingCenterId());
			Site site = this.createNewSiteInShoppingCenter(sc, updatable.getLatitude(), updatable.getLongitude());
			store = this.storeService.createNewStoreForSite(site);
		} else if (updatable.getStoreId() == null) {
			Site site = siteService.findOne(updatable.getSiteId());
			store = this.storeService.createNewStoreForSite(site);
		} else {
			store = storeService.findOne(updatable.getStoreId());
		}
		this.updateStoreFromUpdatable(updatable, store);
		this.updateSiteFromUpdatable(updatable, store.getSite());
		this.updateShoppingCenterFromUpdatable(updatable, store.getSite().getShoppingCenter());
		return store;
	}


	private ResponseEntity<String> getAccessToken() {
		UriComponents keyUri = UriComponentsBuilder.newInstance()
				.scheme("https")
				.host("www.arcgis.com")
				.path("/sharing/rest/oauth2/token/")
				.queryParam("client_id", this.pgClientId)
				.queryParam("client_secret", this.pgClientSecret)
				.queryParam("grant_type", "client_credentials")
				.queryParam("expiration", "20160")
				.build();

		return new RestTemplate().getForEntity(keyUri.toUriString(), String.class);
	}

	private JsonNode getFeaturesEditedSince(LocalDateTime since) throws IOException {
		ObjectMapper mapper = new ObjectMapper();

		ResponseEntity<String> token = this.getAccessToken();
		JsonNode keyRoot = mapper.readTree(token.getBody());
		JsonNode accessTokenField = keyRoot.path("access_token");
		String accessToken = accessTokenField.textValue();

		String maxSourceEditedDateString = since.toString().replace('T', ' ');

		UriComponents featureQueryUri = UriComponentsBuilder.newInstance()
				.scheme("https")
				.host("services1.arcgis.com")
				.path("/aUqH6d4IMis39TBB/arcgis/rest/services/BD_FUTURE_RETAIL/FeatureServer/0/query")
				.queryParam("where", String.format("EditDate>='%s'", maxSourceEditedDateString))
				.queryParam("outFields", "*")
				.queryParam("f", "json")
				.queryParam("token", accessToken)
				.queryParam("outSR", "4326")
				.build();

		ResponseEntity<String> response = new RestTemplate().getForEntity(featureQueryUri.toUriString(), String.class);
		JsonNode root = mapper.readTree(response.getBody());
		return root.get("features");
	}

	@Transactional
	protected Site createNewSiteInShoppingCenter(ShoppingCenter sc, Float latitude, Float longitude) {
		Site site = new Site();
		site.setShoppingCenter(sc);
		site.setLatitude(latitude);
		site.setLongitude(longitude);
		return siteService.createOne(sc, latitude, longitude);
	}

	@Transactional
	protected void updateShoppingCenterFromUpdatable(PlannedGroceryUpdatable updatable, ShoppingCenter shoppingCenter) {
		shoppingCenter.setName(updatable.getShoppingCenterName());
		shoppingCenterService.updateOne(new ShoppingCenterView(shoppingCenter));
	}

	@Transactional
	protected void updateSiteFromUpdatable(PlannedGroceryUpdatable updatable, Site site) {
		site.setAddress1(updatable.getAddress());
		site.setQuad(updatable.getQuad());
		site.setIntersectionStreetPrimary(updatable.getIntersectionStreetPrimary());
		site.setIntersectionStreetSecondary(updatable.getIntersectionStreetSecondary());
		site.setCity(updatable.getCity());
		site.setCounty(updatable.getCounty());
		site.setState(updatable.getState());
		site.setPostalCode(updatable.getPostalCode());
		site.setLatitude(updatable.getLatitude());
		site.setLongitude(updatable.getLongitude());
		siteService.updateOne(new SiteView(site));
	}

	@Transactional
	protected void updateStoreFromUpdatable(PlannedGroceryUpdatable updatable, Store store) {
		store.setStoreName(updatable.getStoreName());
		store.setDateOpened(updatable.getDateOpened());
		if (updatable.getStoreStatuses() != null) {
			updatable.getStoreStatuses().stream().filter(status -> status.getId() == null).forEach(status -> {
				StoreStatus newStoreStatus = new StoreStatus();
				newStoreStatus.setStore(store);
				newStoreStatus.setStatus(status.getStatus());
				newStoreStatus.setStatusStartDate(status.getStatusStartDate());
				storeStatusService.addOne(new StoreStatusView(newStoreStatus));
			});
		}
		StoreSurvey storeSurvey = StoreUtil.getLatestSurveyAsOfDateTime(store, LocalDateTime.now()).orElseGet(() -> {
			StoreSurvey newSurvey = new StoreSurvey();
			newSurvey.setStore(store);
			newSurvey.setSurveyDate(LocalDateTime.now());
			return storeSurveyService.addOne(newSurvey);
		});
		storeSurvey.setAreaTotal(updatable.getAreaTotal());
		storeSurvey.setUpdatedBy(securityService.getCurrentUser());
		storeService.updateOne(new StoreView(store));
	}

	@Transactional
	protected void processFeatureNode(JsonNode featureNode, UserProfile validatingUser) {
		// Parse Node into values
		JsonNode attributesNode = featureNode.path("attributes");

		JsonNode objectIdNode = attributesNode.get("OBJECTID");
		String objectId = String.valueOf(objectIdNode.numberValue());

		String sourceUrl = attributesNode.get("SOURCE").textValue();
		String sourceStoreName = attributesNode.get("NAME").textValue();
		LocalDateTime sourceCreateDate = this.epochMillisecondsToLocalDateTime(attributesNode.get("CreationDate").longValue());
		LocalDateTime sourceEditDate = this.epochMillisecondsToLocalDateTime(attributesNode.get("EditDate").longValue());

		// Check if source already exists
		StoreSource source = this.storeSourceService.findOneBySourceNativeIdUsingSpecs("Planned Grocery", objectId).orElseGet(() -> {
			StoreSource newSource = new StoreSource();
			newSource.setSourceNativeId(objectId);
			newSource.setSourceName("Planned Grocery");
			newSource.setSourceCreatedDate(sourceCreateDate);
			return newSource;
		});

		// Update relevant source data
		source.setSourceStoreName(sourceStoreName);
		source.setSourceUrl(sourceUrl);
		source.setSourceEditedDate(sourceEditDate);

		// Attempt to automatically update database records from source
		if (this.updateDatabaseRecords(attributesNode, source)) {
			source.setValidatedBy(validatingUser);
			source.setValidatedDate(LocalDateTime.now());
		} else {
			source.setValidatedBy(null);
			source.setValidatedDate(null);
		}

		if (source.getId() != null) {
			this.storeSourceService.updateOne(new StoreSourceView(source));
		} else {
			this.storeSourceService.addOne(new StoreSourceView(source));
		}
	}

	@Transactional
	protected boolean updateDatabaseRecords(JsonNode attributesNode, StoreSource source) {
		Store store = source.getStore();
		if (store == null) {
			return false; // Not yet matched
		}
		Site site = store.getSite();
		if (store.getSite() == null) {
			throw new IllegalStateException("Store must have a site!");
		}

		this.updateSiteFromPGFeature(site, attributesNode);
		this.updateShoppingCenter(site.getShoppingCenter(), attributesNode);

		// If the stores are mismatched, or if store status is messed up, it won't be marked as validated
		if (source.getSourceStoreName().equals(store.getStoreName())) {
			try {
				this.updateStoreFromJson(store, attributesNode, source.getSourceEditedDate());
				return true;
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
	}

	@Transactional
	protected void updateStoreFromJson(Store store, JsonNode attributesNode, LocalDateTime sourceEditedDate) throws Exception {
		boolean storeEdited = false;
		// TODO Get OPENDATEAPPROX and translate, use where OPENDATE is null
		if (attributesNode.hasNonNull("OPENDATE") && store.getDateOpened() == null) {
			LocalDateTime openDate = this.epochMillisecondsToLocalDateTime(attributesNode.get("OPENDATE").longValue());
			store.setDateOpened(openDate);
			storeEdited = true;
		}

		if (attributesNode.hasNonNull("SIZESF")) {
			StoreUtil.getLatestSurveyAsOfDateTime(store, LocalDateTime.now()).ifPresent(storeSurvey -> {
				if (storeSurvey.getAreaTotal() == null) {
					Integer siteSF = attributesNode.get("SIZESF").intValue();
					storeSurvey.setAreaTotal(siteSF);
					storeSurveyService.updateOne(new StoreSurveyView(storeSurvey));
				}
			});
		}

		if (attributesNode.hasNonNull("STATUS")) {
			String sourceStatus = getStatusMap().get(attributesNode.get("STATUS").intValue());
			Optional<StoreStatus> mostRelevantStatus = StoreUtil.getLatestStatusAsOfDateTime(store, sourceEditedDate);
			if (mostRelevantStatus.isPresent()) {
				String status = mostRelevantStatus.get().getStatus();
				// Planned Grocery doesn't do closings, so if we already have it as open, leave it open.
				if (getStatusRank(sourceStatus) >= getStatusRank(status)) {
					// Only create new if changed (progressively)
					if (!sourceStatus.equals(status)) {
						this.createNewStatusFromSource(store, sourceStatus, sourceEditedDate);
					}
				} else {
					// Store status must be validated manually
					throw new Exception("Status Out of Order");
				}
			} else {
				this.createNewStatusFromSource(store, sourceStatus, sourceEditedDate);
			}
		}
		if (storeEdited) {
			storeService.updateOne(new StoreView(store));
		}
	}

	@Transactional
	protected void updateSiteFromPGFeature(Site site, JsonNode attributesNode) {
		boolean siteEdited = false;
		if (attributesNode.hasNonNull("DESCLOCATION") && site.getAddress1() == null) {
			String address = attributesNode.get("DESCLOCATION").textValue();
			site.setAddress1(address);
			siteEdited = true;
		}
		if (attributesNode.hasNonNull("CITY") && site.getCity() == null) {
			String city = attributesNode.get("CITY").textValue();
			site.setCity(city);
			siteEdited = true;
		}
		if (attributesNode.hasNonNull("STATE") && site.getState() == null) {
			String state = attributesNode.get("STATE").textValue();
			site.setState(state);
			siteEdited = true;
		}
		if (attributesNode.hasNonNull("ZIP") && site.getPostalCode() == null) {
			String zip = attributesNode.get("ZIP").textValue();
			site.setPostalCode(zip);
			siteEdited = true;
		}
		if (attributesNode.hasNonNull("COUNTY") && site.getCounty() == null) {
			String county = attributesNode.get("COUNTY").textValue();
			site.setCounty(county);
			siteEdited = true;
		}

		if (siteEdited) {
			siteService.updateOne(new SiteView(site));
		}
	}

	@Transactional
	protected void updateShoppingCenter(ShoppingCenter shoppingCenter, JsonNode attributesNode) {
		if (attributesNode.hasNonNull("NAMECENTER") && shoppingCenter != null && shoppingCenter.getName() == null) {
			String nameCenter = attributesNode.get("NAMECENTER").textValue();
			shoppingCenter.setName(nameCenter);
			shoppingCenterService.updateOne(new ShoppingCenterView(shoppingCenter));
		}
	}

	@Transactional
	protected void createNewStatusFromSource(Store store, String sourceStatus, LocalDateTime statusStartDate) {
		StoreStatus newStatus = new StoreStatus();
		newStatus.setStore(store);
		newStatus.setStatus(sourceStatus);
		newStatus.setStatusStartDate(statusStartDate);
		storeStatusService.addOne(new StoreStatusView(newStatus));
	}

	private LocalDateTime epochMillisecondsToLocalDateTime(Long epochMilliseconds) {
		if (epochMilliseconds == null) {
			return null;
		}
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(epochMilliseconds), ZoneId.systemDefault());
	}

	private Integer getStatusRank(String status) {
		if (status.equals("Open")) {
			return 8;
		}
		if (status.equals("Closed") || status.equals("Temporarily Closed")) {
			return 7;
		}
		if (status.equals("Dead Deal")) {
			return 6;
		}
		if (status.equals("New Under Construction")) {
			return 5;
		}
		if (status.equals("Remodel")) {
			return 4;
		}
		if (status.equals("Planned")) {
			return 3;
		}
		if (status.equals("Proposed")) {
			return 2;
		}
		if (status.equals("Rumored") || status.equals("Strong Rumor")) {
			return 1;
		}
		// if (status.equals("Dead") || status.equals("Float")) {
		return 0;
		//}
	}

	private Map<Integer, String> getStatusMap() {
		if (this.statusMap == null) {
			this.statusMap = new HashMap<>();
			this.statusMap.put(0, "Open");
			this.statusMap.put(1, "New Under Construction");
			this.statusMap.put(2, "Proposed");
			this.statusMap.put(3, "Planned");
			this.statusMap.put(99, "Dead Deal");
		}
		return this.statusMap;
	}

}

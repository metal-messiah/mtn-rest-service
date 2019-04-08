package com.mtn.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mtn.model.domain.*;
import com.mtn.model.utils.JsonNodeUtil;
import com.mtn.model.utils.StoreUtil;
import com.mtn.model.view.StoreSourceData;
import com.mtn.model.view.StoreSourceView;
import com.mtn.model.view.StoreStatusView;
import com.mtn.util.MtnLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

@Service
public class PlannedGroceryService {

	private final StoreSourceService storeSourceService;
	private final ShoppingCenterService shoppingCenterService;
	private final StoreService storeService;
	private final SiteService siteService;
	private final StoreStatusService storeStatusService;
	private final SecurityService securityService;

	@Value("${planned-grocery.client_id}")
	private String pgClientId;

	@Value("${planned-grocery.client_secret}")
	private String pgClientSecret;

	@Autowired
	public PlannedGroceryService(StoreSourceService storeSourceService,
								 ShoppingCenterService shoppingCenterService,
								 StoreService storeService,
								 SiteService siteService,
								 StoreStatusService storeStatusService,
								 SecurityService securityService) {
		this.storeSourceService = storeSourceService;
		this.shoppingCenterService = shoppingCenterService;
		this.storeService = storeService;
		this.siteService = siteService;
		this.storeStatusService = storeStatusService;
		this.securityService = securityService;
	}

	public StoreSourceData getStoreDataForSource(StoreSource storeSource) throws Exception {
		if (!storeSource.getSourceName().equals("Planned Grocery")) {
			throw new Exception("StoreSource must be from Planned Grocery");
		}

		StoreSourceData ssd = new StoreSourceData();

		JsonNode root = this.getFeatureByObjectId(storeSource.getSourceNativeId());
		JsonNode features = root.get("features");
		if (features == null || !features.isArray() || features.size() != 1) {
			throw new EntityNotFoundException("Original Planned Grocery source not found!");
		}
		JsonNode pgRecord = features.get(0);

		JsonNode geometry = pgRecord.get("geometry");
		ssd.setLatitude(geometry.get("y").floatValue());
		ssd.setLongitude(geometry.get("x").floatValue());

		JsonNode attributes = pgRecord.get("attributes");
		ssd.setShoppingCenterName(JsonNodeUtil.getNodeStringValue(attributes, "NAMECENTER"));
		ssd.setAddress(JsonNodeUtil.getNodeStringValue(attributes, "DESCLOCATION"));
		ssd.setCity(JsonNodeUtil.getNodeStringValue(attributes, "CITY"));
		ssd.setCounty(JsonNodeUtil.getNodeStringValue(attributes, "county"));
		ssd.setState(JsonNodeUtil.getNodeStringValue(attributes, "STATE"));
		ssd.setPostalCode(JsonNodeUtil.getNodeStringValue(attributes, "ZIP"));

		ssd.setStoreName(JsonNodeUtil.getNodeStringValue(attributes, "NAME"));

		if (attributes.hasNonNull("OPENDATE")) {
			Long epochMilli = attributes.get("OPENDATE").longValue();
			LocalDate date = Instant.ofEpochMilli(epochMilli).atZone(ZoneId.systemDefault()).toLocalDate();
			ssd.setDateOpened(date.format(DateTimeFormatter.BASIC_ISO_DATE));
		} else if (attributes.hasNonNull("OPENDATEAPPROX")) {
			ssd.setDateOpened(attributes.get("OPENDATEAPPROX").textValue());
		}

		if (attributes.hasNonNull("STATUS")) {
			ssd.setStoreStatus(this.getStatusFromPGStatusCode(attributes.get("STATUS").intValue()));
		}

		ssd.setAreaTotal(JsonNodeUtil.getNodeIntValue(attributes, "SIZESF"));
		ssd.setAreaIsActual(attributes.hasNonNull("SIZESFTYPE") && attributes.get("SIZESFTYPE").intValue() == 2);

		return ssd;
	}

	private String getStatusFromPGStatusCode(Integer statusCode) {
		switch (statusCode) {
			case 0: return "Open";
			case 1: return "New Under Construction";
			case 2: return "Proposed";
			case 3: return "Planned";
			case 99: return "Dead Deal";
			default: return null;
		}
	}

	public JsonNode getFeatureByObjectId(String objectId) throws IOException {
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

		ResponseEntity<String> response = new RestTemplate().getForEntity(featureQueryUri.toUriString(), String.class);
		return new ObjectMapper().readTree(response.getBody());
	}

	@Async
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

	private void processFeatureNode(JsonNode featureNode, UserProfile validatingUser) {
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
			StoreSourceView newSourceRequest = new StoreSourceView();
			newSourceRequest.setSourceNativeId(objectId);
			newSourceRequest.setSourceName("Planned Grocery");
			newSourceRequest.setSourceCreatedDate(sourceCreateDate);
			return this.storeSourceService.addOne(newSourceRequest);
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

		this.storeSourceService.updateOne(source);
	}

	private boolean updateDatabaseRecords(JsonNode attributesNode, StoreSource source) {
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

	private void updateStoreFromJson(Store store, JsonNode attributesNode, LocalDateTime sourceEditedDate) throws Exception {
		boolean storeEdited = false;
		// TODO Get OPENDATEAPPROX and translate, use where OPENDATE is null
		if (attributesNode.hasNonNull("OPENDATE") && store.getDateOpened() == null) {
			LocalDateTime openDate = this.epochMillisecondsToLocalDateTime(attributesNode.get("OPENDATE").longValue());
			store.setDateOpened(openDate);
			storeEdited = true;
		}

		if (attributesNode.hasNonNull("SIZESF") && store.getAreaTotal() == null) {
			Integer siteSF = attributesNode.get("SIZESF").intValue();
			store.setAreaTotal(siteSF);
			storeEdited = true;
		}

		if (attributesNode.hasNonNull("STATUS")) {
			String sourceStatus = this.getStatusFromPGStatusCode(attributesNode.get("STATUS").intValue());
			Optional<StoreStatus> mostRelevantStatus = StoreUtil.getLatestStatusAsOfDateTime(store, sourceEditedDate);
			if (mostRelevantStatus.isPresent()) {
				String status = mostRelevantStatus.get().getStatus();
				// Planned Grocery doesn't do closings, so if we already have it as open, leave it open.
				if (sourceStatus != null && getStatusRank(sourceStatus) >= getStatusRank(status)) {
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
			storeService.updateOne(store);
		}
	}

	private void updateSiteFromPGFeature(Site site, JsonNode attributesNode) {
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
			site.setUpdatedBy(securityService.getCurrentUser());
			siteService.updateOne(site);
		}
	}

	private void updateShoppingCenter(ShoppingCenter shoppingCenter, JsonNode attributesNode) {
		if (attributesNode.hasNonNull("NAMECENTER") && shoppingCenter != null && shoppingCenter.getName() == null) {
			String nameCenter = attributesNode.get("NAMECENTER").textValue();
			shoppingCenter.setName(nameCenter);
			shoppingCenterService.updateOne(shoppingCenter);
		}
	}

	private void createNewStatusFromSource(Store store, String sourceStatus, LocalDateTime statusStartDate) {
		StoreStatusView newStatusRequest = new StoreStatusView();
		newStatusRequest.setStatus(sourceStatus);
		newStatusRequest.setStatusStartDate(statusStartDate);
		storeStatusService.addOneToStore(newStatusRequest, store);
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

}

package com.mtn.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mtn.model.domain.*;
import com.mtn.repository.StoreSourceRepository;
import com.mtn.util.MtnLogger;
import com.mtn.validators.StoreSourceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static com.mtn.repository.specification.StoreSourceSpecifications.*;
import static org.springframework.data.jpa.domain.Specifications.where;

@Service
public class StoreSourceServiceImpl extends EntityServiceImpl<StoreSource> implements StoreSourceService {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");


	@Autowired
	private StoreSourceRepository storeSourceRepository;
	@Autowired
	private StoreSourceValidator storeSourceValidator;
	@Autowired
	private StoreSurveyService storeSurveyService;
	@Autowired
	private ShoppingCenterService shoppingCenterService;
	@Autowired
	private StoreService storeService;
	@Autowired
	private SiteService siteService;
	@Autowired
	private StoreStatusService storeStatusService;

	private Map<Integer, String> statusMap;

	@Override
	public List<StoreSource> findAllByStoreId(Integer storeId) {
		return getEntityRepository().findAll(where(storeIdEquals(storeId)));

	}

	@Override
	public void addAndUpdateSourcesFromPlannedGrocery(UserProfile validator) {
		MtnLogger.info("Running Planned Grocery update");

		RestTemplate restTemplate = new RestTemplate();
		ObjectMapper mapper = new ObjectMapper();

		try {
			String accessToken = getAccessToken(restTemplate, mapper);
			JsonNode featureNodes = getFeatures(restTemplate, mapper, accessToken);
			if (featureNodes.isArray()) {
				featureNodes.forEach(f -> processFeatureNode(f, validator));
			} else {
				MtnLogger.warn("Failed to find features in Planned Grocery results");
			}
		} catch (IOException e) {
			MtnLogger.warn("Failed to retrieve parse response from Planned Grocery", e);
		}
		MtnLogger.info("Finished running Planned Grocery update {}", dateFormat.format(new Date()));
	}

	private String getAccessToken(RestTemplate restTemplate, ObjectMapper mapper) throws IOException {
		UriComponents keyUri = UriComponentsBuilder.newInstance()
				.scheme("https")
				.host("www.arcgis.com")
				.path("/sharing/rest/oauth2/token/")
				.queryParam("client_id", "LT6WmxqA9lqbrGOR")
				.queryParam("client_secret", "ca783148e98d45f29798a9d1ec1f75e1")
				.queryParam("grant_type", "client_credentials")
				.queryParam("expiration", "20160")
				.build();

		ResponseEntity<String> keyResponse = restTemplate.getForEntity(keyUri.toUriString(), String.class);
		JsonNode keyRoot = mapper.readTree(keyResponse.getBody());
		JsonNode accessTokenField = keyRoot.path("access_token");
		return accessTokenField.textValue();
	}

	private JsonNode getFeatures(RestTemplate restTemplate, ObjectMapper mapper, String accessToken) throws IOException {
		String maxSourceEditedDateString = this.getMaxSourceEditedDate().toString().replace('T', ' ');

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

		ResponseEntity<String> response = restTemplate.getForEntity(featureQueryUri.toUriString(), String.class);
		JsonNode root = mapper.readTree(response.getBody());
		return root.get("features");
	}

	private void processFeatureNode(JsonNode featureNode, UserProfile validator) {

		// Parse Node into values
		JsonNode attributesNode = featureNode.path("attributes");

		JsonNode objectIdNode = attributesNode.get("OBJECTID");
		String objectId = String.valueOf(objectIdNode.numberValue());

		String sourceUrl = attributesNode.get("SOURCE").textValue();
		String sourceStoreName = attributesNode.get("NAME").textValue();
		LocalDateTime sourceCreateDate = this.epochMillisecondsToLocalDateTime(attributesNode.get("CreationDate").longValue());
		LocalDateTime sourceEditDate = this.epochMillisecondsToLocalDateTime(attributesNode.get("EditDate").longValue());

		// Check if source already exists
		StoreSource source = this.findOneBySourceNativeIdUsingSpecs("Planned Grocery", objectId);
		if (source == null) {
			// Create new source record
			source = new StoreSource();
			source.setSourceNativeId(objectId);
			source.setSourceName("Planned Grocery");
			source.setSourceCreatedDate(sourceCreateDate);
		}
		// Update relevant source data
		source.setSourceStoreName(sourceStoreName);
		source.setSourceUrl(sourceUrl);
		source.setSourceEditedDate(sourceEditDate);

		if (this.updateDatabaseRecords(attributesNode, source)) {
			source.setValidatedBy(validator);
			source.setValidatedDate(LocalDateTime.now());
		} else {
			source.setValidatedBy(null);
			source.setValidatedDate(null);
		}

		if (source.getId() != null) {
			this.updateOne(source.getId(), source);
		} else {
			this.addOne(source);
		}
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

	private boolean updateDatabaseRecords(JsonNode attributesNode, StoreSource source) {
		Store store = source.getStore();
		if (store == null) {
			return false; // Not yet matched
		}
		Site site = store.getSite();
		if (store.getSite() == null) {
			throw new IllegalStateException("Store must have a site!");
		}

		this.updateSite(site, attributesNode);
		this.updateShoppingCenter(site.getShoppingCenter(), attributesNode);

		// If the stores are mismatched, or if store status is messed up, it won't be marked as validated
		if (source.getSourceStoreName().equals(store.getStoreName())) {
			try {
				this.updateStore(store, attributesNode, source.getSourceEditedDate());
				return true;
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
	}

	private Store updateStore(Store store, JsonNode attributesNode, LocalDateTime sourceEditedDate) throws Exception {
		boolean storeEdited = false;
		// TODO Get OPENDATEAPPROX and translate, use where OPENDATE is null
		if (attributesNode.hasNonNull("OPENDATE") && store.getDateOpened() == null) {
			LocalDateTime openDate = this.epochMillisecondsToLocalDateTime(attributesNode.get("OPENDATE").longValue());
			store.setDateOpened(openDate);
			storeEdited = true;
		}

		StoreSurvey survey = store.getCurrentStoreSurvey();
		if (attributesNode.hasNonNull("SIZESF") && survey != null && survey.getAreaTotal() == null) {
			Integer siteSF = attributesNode.get("SIZESF").intValue();
			survey.setAreaTotal(siteSF);
			storeSurveyService.updateOne(survey.getId(), survey);
		}

		if (attributesNode.hasNonNull("STATUS")) {
			String sourceStatus = getStatusMap().get(attributesNode.get("STATUS").intValue());
			if (store.getStatuses() != null && store.getStatuses().size() > 0) {
				StoreStatus mostRelevantStatus = store.getStatuses().stream()
						.filter(s -> s.getDeletedDate() == null && s.getStatusStartDate().isBefore(sourceEditedDate))
						.max(Comparator.comparing(StoreStatus::getStatusStartDate))
						.get();

				// Ignore outdated statuses
				// Planned Grocery doesn't do closures, so if we already have it as open, leave it open.
				if (getStatusRank(sourceStatus) >= getStatusRank(mostRelevantStatus.getStatus())) {
					// Only create new if changed
					if (!sourceStatus.equals(mostRelevantStatus.getStatus())) {
						StoreStatus newStatus = this.createNewStatusFromSource(store, sourceStatus, sourceEditedDate);
						if (store.getCurrentStatus().getStatusStartDate().isBefore(newStatus.getStatusStartDate())) {
							store.setCurrentStatus(newStatus);
							storeEdited = true;
						}
					}
				} else {
					// Store status must be validated manually
					throw new Exception("Status Out of Order");
				}
			} else {
				StoreStatus newStatus = this.createNewStatusFromSource(store, sourceStatus, sourceEditedDate);
				store.setCurrentStatus(newStatus);
				storeEdited = true;
			}
		}
		if (storeEdited) {
			storeService.updateOne(store.getId(), store);
		}

		return store;
	}

	private Site updateSite(Site site, JsonNode attributesNode) {
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
			siteService.updateOne(site.getId(), site);
		}
		return site;
	}

	private ShoppingCenter updateShoppingCenter(ShoppingCenter shoppingCenter, JsonNode attributesNode) {
		if (attributesNode.hasNonNull("NAMECENTER") && shoppingCenter != null && shoppingCenter.getName() == null) {
			String nameCenter = attributesNode.get("NAMECENTER").textValue();
			shoppingCenter.setName(nameCenter);
			shoppingCenterService.updateOne(shoppingCenter.getId(), shoppingCenter);
		}
		return shoppingCenter;
	}

	private StoreStatus createNewStatusFromSource(Store store, String sourceStatus, LocalDateTime statusStartDate) {
		StoreStatus newStatus = new StoreStatus();
		newStatus.setStore(store);
		newStatus.setStatus(sourceStatus);
		newStatus.setStatusStartDate(statusStartDate);
		return storeStatusService.addOne(newStatus);
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

	private LocalDateTime epochMillisecondsToLocalDateTime(Long epochMilliseconds) {
		if (epochMilliseconds == null) {
			return null;
		}
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(epochMilliseconds), ZoneId.systemDefault());
	}

	@Override
	public List<StoreSource> findAllByStoreIdUsingSpecs(Integer storeId) {
		return getEntityRepository().findAll(
				where(storeIdEquals(storeId))
						.and(isNotDeleted())
		);
	}

	@Override
	public LocalDateTime getMaxSourceEditedDate() {
		return getEntityRepository().getMaxSourceEditedDate();
	}

	@Override
	public Page<StoreSource> findAllUsingSpecs(Pageable page) {
		return getEntityRepository().findAll(where(isNotDeleted()), page);
	}

	@Override
	public StoreSource findOneUsingSpecs(Integer id) {
		return getEntityRepository().findOne(
				where(idEquals(id))
						.and(isNotDeleted())
		);
	}

	@Override
	public StoreSource findOneBySourceNativeIdUsingSpecs(String sourceName, String id) {
		return getEntityRepository().findOne(
				where(sourceNativeIdEquals(id))
						.and(sourceNameEquals(sourceName))
						.and(isNotDeleted())
		);
	}

	@Override
	public StoreSource updateEntityAttributes(StoreSource existing, StoreSource request) {
		// TODO

		return existing;
	}

	@Override
	public String getEntityName() {
		return "StoreSource";
	}

	@Override
	public void handleAssociationsOnDeletion(StoreSource existing) {
		// TODO - Handle Store
	}

	@Override
	public void handleAssociationsOnCreation(StoreSource request) {
		// TODO - Handle Store
	}

	@Override
	public StoreSourceRepository getEntityRepository() {
		return storeSourceRepository;
	}

	@Override
	public StoreSourceValidator getValidator() {
		return storeSourceValidator;
	}

}

package com.mtn.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class PlannedGroceryServiceImpl implements PlannedGroceryService {

	@Value("${planned-grocery.client_id}")
	private String pgClientId;

	@Value("${planned-grocery.client_secret}")
	private String pgClientSecret;

	public ResponseEntity<String> getAccessToken() {
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

	public JsonNode getFeaturesEditedSince(LocalDateTime since) throws IOException {
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

}

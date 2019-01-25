package com.mtn.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.mtn.interceptors.HeaderRequestInterceptor;
import com.mtn.model.domain.*;
import com.mtn.util.MtnLogger;
import org.apache.catalina.util.URLEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class ChainXYService {

	private final StoreSourceService storeSourceService;

	@Value("${chainXyApiKey}")
	private String chainXyApiKey;

	@Autowired
	public ChainXYService(StoreSourceService storeSourceService) {
		this.storeSourceService = storeSourceService;
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
	public void updateDbSources(UserProfile userProfile) {
		try {
			ArrayNode allChains = this.getChainXyChains();
			MtnLogger.info("Chains: " + allChains.size());
			this.updateBannerSources(allChains);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void updateBannerSources(ArrayNode allChains) {
		// Retrieve all banner_sources where source_name = 'ChainXY'

		// For each ChainXY chain,
			// If not found by native_source_id
				// create new banner_source record
				// add all store_source records
			// else if LastScrapeDate > banner_source.source_updated_date
				// check for additions and deletions of store_source


	}

	private ArrayNode getChainXyChains() throws IOException {
		// TODO get max scraped date, use as filter

		ArrayNode chains = null;

		int currentPage = 0;
		int totalPages = 1;

		UriComponents request = UriComponentsBuilder.newInstance()
				.scheme("https")
				.host("location.chainxy.com")
				.path("/api/Chains")
				.queryParam("fields", "Name,LastScrapeDate,Id")
				.queryParam("page", "{page}")
				.queryParam("query", "{query}")
				.build(); // Grocery Category

		Map<String, String> vars = new HashMap<>();

		// IMPORTANT! JSON query object must be injected as a uri variable, because otherwise Spring interprets the
		// curly braces as a variable placeholder (even if they are encoded)
		vars.put("query", "{\"Categories\":{\"Id\":154}, \"Countries\":{\"Code\":\"US\"}}");

//		do {
			vars.put("page", String.valueOf(currentPage));

			ResponseEntity<String> response = this.getRestTemplate().getForEntity(request.toUriString(), String.class, vars);

			JsonNode jsonRoot = new ObjectMapper().readTree(response.getBody());
			totalPages = jsonRoot.get("Pages").intValue();

			ArrayNode pageChains = (ArrayNode) jsonRoot.path("Records");

			// If this is the first page, save as initial ArrayNode, else add this page's chains to the others
			if (chains == null) {
				chains = pageChains;
			} else {
				chains.addAll(pageChains);
			}
//		} while (++currentPage < totalPages);

		return chains;
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

package com.mtn.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mtn.service.SecurityService;
import com.mtn.service.StoreSourceService;
import com.mtn.util.MtnLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.LocalDateTime;


/**
 * Created by Allen on 5/6/2017.
 */
@RestController
@RequestMapping("/api/store-source")
public class StoreSourceController {

	@Autowired
	private StoreSourceService storeSourceService;
	@Autowired
	private SecurityService securityService;

	@RequestMapping(value = "/planned-grocery", method = RequestMethod.GET)
	public ResponseEntity getPlannedGrocerySources() {

		storeSourceService.addAndUpdateSourcesFromPlannedGrocery(securityService.getCurrentUser());

		return ResponseEntity.noContent().build();
	}

}

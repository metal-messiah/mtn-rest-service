package com.mtn.controller;

import com.mtn.service.PlannedGroceryService;
import com.mtn.service.SecurityService;
import com.mtn.service.StoreSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


/**
 * Created by Allen on 5/6/2017.
 */
@RestController
@RequestMapping("/api/planned-grocery")
public class PlannedGroceryController {

	@Autowired
	private PlannedGroceryService plannedGroceryService;

	@RequestMapping(value = "/{objectId}", method = RequestMethod.GET)
	public ResponseEntity<String> getFeatureByObjectId(@PathVariable("objectId") String objectId) {
		try {
			return plannedGroceryService.getFeatureByObjectId(objectId);
		} catch (IOException e) {
			return ResponseEntity.notFound().build();
		}
	}

}

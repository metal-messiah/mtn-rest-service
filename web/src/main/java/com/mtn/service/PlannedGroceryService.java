package com.mtn.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.mtn.model.domain.UserProfile;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.time.LocalDateTime;

public interface PlannedGroceryService {

	ResponseEntity<String> getFeatureByObjectId(String objectId) throws IOException;

	JsonNode getFeaturesEditedSince(LocalDateTime since) throws IOException;

	void addAndUpdateSourcesFromPlannedGrocery(UserProfile validator);

}

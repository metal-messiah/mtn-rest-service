package com.mtn.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.time.LocalDateTime;

public interface PlannedGroceryService {
	ResponseEntity<String> getAccessToken();

	ResponseEntity<String> getFeatureByObjectId(String objectId) throws IOException;

	JsonNode getFeaturesEditedSince(LocalDateTime since) throws IOException;
}

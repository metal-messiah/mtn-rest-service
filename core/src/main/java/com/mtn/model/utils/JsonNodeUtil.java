package com.mtn.model.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.mtn.model.domain.Store;
import com.mtn.model.domain.StoreStatus;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Optional;

public interface JsonNodeUtil {

	static String getNodeStringValue(JsonNode node, String attrName) {
		if (node.hasNonNull(attrName)) {
			return node.get(attrName).textValue();
		}
		return null;
	}

	static Integer getNodeIntValue(JsonNode node, String attrName) {
		if (node.hasNonNull(attrName)) {
			return node.get(attrName).intValue();
		}
		return null;
	}

	static Float getNodeFloatValue(JsonNode node, String attrName) {
		if (node.hasNonNull(attrName)) {
			return node.get(attrName).floatValue();
		}
		return null;
	}

	static Long getNodeLongValue(JsonNode node, String attrName) {
		if (node.hasNonNull(attrName)) {
			return node.get(attrName).longValue();
		}
		return null;
	}
}

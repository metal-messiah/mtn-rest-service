package com.mtn.model.utils;

import com.mtn.model.domain.ShoppingCenter;
import com.mtn.model.domain.ShoppingCenterSurvey;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Optional;

public interface ShoppingCenterUtil {

		static Optional<ShoppingCenterSurvey> getLatestSurveyAsOfDateTime(ShoppingCenter shoppingCenter, LocalDateTime localDateTime) {
		return shoppingCenter.getSurveys().stream()
				.filter(s -> s.getDeletedDate() == null && s.getSurveyDate().isBefore(localDateTime))
				.max(Comparator.comparing(ShoppingCenterSurvey::getSurveyDate));
	}
}

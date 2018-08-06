package com.mtn.model.utils;

import com.mtn.model.domain.Store;
import com.mtn.model.domain.StoreStatus;
import com.mtn.model.domain.StoreSurvey;
import com.mtn.model.domain.StoreVolume;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Optional;

public interface StoreUtil {

	static Optional<StoreStatus> getLatestStatusAsOfDateTime(Store store, LocalDateTime localDateTime) {
		return store.getStatuses().stream()
				.filter(s -> s.getDeletedDate() == null && s.getStatusStartDate().isBefore(localDateTime))
				.max(Comparator.comparing(StoreStatus::getStatusStartDate));
	}

	static Optional<StoreSurvey> getLatestSurveyAsOfDateTime(Store store, LocalDateTime localDateTime) {
		return store.getSurveys().stream()
				.filter(s -> s.getDeletedDate() == null && s.getSurveyDate().isBefore(localDateTime))
				.max(Comparator.comparing(StoreSurvey::getSurveyDate));
	}

	static Optional<StoreVolume> getLatestVolumeAsOfDateTime(Store store, LocalDate localDate) {
		return store.getVolumes().stream()
				.filter(s -> s.getDeletedDate() == null && s.getVolumeDate().isBefore(localDate))
				.max(Comparator.comparing(StoreVolume::getVolumeDate));
	}
}

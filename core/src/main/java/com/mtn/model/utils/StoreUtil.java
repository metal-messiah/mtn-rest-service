package com.mtn.model.utils;

import com.mtn.model.StoreChild;
import com.mtn.model.domain.Store;
import com.mtn.model.domain.StoreStatus;
import com.mtn.model.domain.StoreSurvey;
import com.mtn.model.domain.StoreVolume;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public interface StoreUtil {

	static Optional<StoreStatus> getLatestStatusAsOfDateTime(Store store, LocalDateTime localDateTime) {
		if (store == null || store.getStatuses() == null) {
			return Optional.empty();
		}
		return store.getStatuses().stream()
				.filter(s -> s.getDeletedDate() == null && s.getStatusStartDate().isBefore(localDateTime))
				.max(Comparator.comparing(StoreStatus::getStatusStartDate));
	}

	static Optional<StoreSurvey> getLatestSurveyAsOfDateTime(Store store, LocalDateTime localDateTime) {
		if (store == null || store.getSurveys() == null) {
			return Optional.empty();
		}
		return store.getSurveys().stream()
				.filter(s -> s.getDeletedDate() == null && s.getSurveyDate().isBefore(localDateTime))
				.max(Comparator.comparing(StoreSurvey::getSurveyDate));
	}

	static Optional<StoreVolume> getLatestVolumeAsOfDateTime(Store store, LocalDate localDate) {
		if (store == null || store.getVolumes() == null) {
			return Optional.empty();
		}
		return store.getVolumes().stream()
				.filter(s -> s.getDeletedDate() == null && s.getVolumeDate().isBefore(localDate.plusDays(1)))
				.max(Comparator.comparing(StoreVolume::getVolumeDate));
	}

	static <T extends StoreChild> void transferChildren(List<T> source, List<T> destination, Store targetStore) {
		Iterator<T> it = source.iterator();
		while(it.hasNext()) {
			T item = it.next();
			it.remove();
			item.setStore(targetStore);
			destination.add(item);
		}
	}
}

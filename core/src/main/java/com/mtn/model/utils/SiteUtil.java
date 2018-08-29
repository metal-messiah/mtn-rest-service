package com.mtn.model.utils;

import com.mtn.constant.StoreType;
import com.mtn.model.domain.*;

import java.util.Optional;

public interface SiteUtil {

	static Optional<Store> getActiveStore(Site site) {
		if (site == null || site.getStores() == null) {
			return Optional.empty();
		}
		return site.getStores().stream()
				.filter(store -> store.getStoreType() == StoreType.ACTIVE).findFirst();
	}

}

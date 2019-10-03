package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.constant.StoreType;
import com.mtn.model.domain.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectSummary {

	private Integer activeStoresInBounds = 0;
	private Integer activeCasedStoresInBounds = 0;
	private Integer activeCasedStoresOutOfBounds = 0;
	private Integer futureStoresInBounds = 0;
	private Integer futureCasedStoresInBounds = 0;
	private Integer futureCasedStoresOutOfBounds = 0;
	private Integer historicalStoresInBounds = 0;
	private Integer historicalCasedStoresInBounds = 0;
	private Integer historicalCasedStoresOutOfBounds = 0;

	public ProjectSummary(Project project, List<Site> sitesInBounds) {
		List<Store> casedStores = project.getStoreCasings().stream()
				.map(StoreCasing::getStore).collect(Collectors.toList());

		sitesInBounds.forEach(site -> {
			site.getStores().forEach(store -> {
				if (store.getDeletedDate() == null) {
					if (store.getStoreType() == StoreType.ACTIVE) {
						this.activeStoresInBounds++;
					} else if (store.getStoreType() == StoreType.FUTURE) {
						this.futureStoresInBounds++;
					} else if (store.getStoreType() == StoreType.HISTORICAL) {
						this.historicalStoresInBounds++;
					}
				}
			});
		});

		Set<Integer> siteIdsInBounds = sitesInBounds.stream().map(AuditingEntity::getId).collect(Collectors.toSet());

		casedStores.forEach(store -> {
			if (siteIdsInBounds.contains(store.getSite().getId())) {
				if (store.getDeletedDate() == null) {
					if (store.getStoreType() == StoreType.ACTIVE) {
						this.activeCasedStoresInBounds++;
					} else if (store.getStoreType() == StoreType.FUTURE) {
						this.futureCasedStoresInBounds++;
					} else if (store.getStoreType() == StoreType.HISTORICAL) {
						this.historicalCasedStoresInBounds++;
					}
				}
			} else {
				if (store.getDeletedDate() == null) {
					if (store.getStoreType() == StoreType.ACTIVE) {
						this.activeCasedStoresOutOfBounds++;
					} else if (store.getStoreType() == StoreType.FUTURE) {
						this.futureCasedStoresOutOfBounds++;
					} else if (store.getStoreType() == StoreType.HISTORICAL) {
						this.historicalCasedStoresOutOfBounds++;
					}
				}
			}
		});
	}

	public Integer getActiveStoresInBounds() {
		return activeStoresInBounds;
	}

	public void setActiveStoresInBounds(Integer activeStoresInBounds) {
		this.activeStoresInBounds = activeStoresInBounds;
	}

	public Integer getActiveCasedStoresInBounds() {
		return activeCasedStoresInBounds;
	}

	public void setActiveCasedStoresInBounds(Integer activeCasedStoresInBounds) {
		this.activeCasedStoresInBounds = activeCasedStoresInBounds;
	}

	public Integer getActiveCasedStoresOutOfBounds() {
		return activeCasedStoresOutOfBounds;
	}

	public void setActiveCasedStoresOutOfBounds(Integer activeCasedStoresOutOfBounds) {
		this.activeCasedStoresOutOfBounds = activeCasedStoresOutOfBounds;
	}

	public Integer getFutureStoresInBounds() {
		return futureStoresInBounds;
	}

	public void setFutureStoresInBounds(Integer futureStoresInBounds) {
		this.futureStoresInBounds = futureStoresInBounds;
	}

	public Integer getFutureCasedStoresInBounds() {
		return futureCasedStoresInBounds;
	}

	public void setFutureCasedStoresInBounds(Integer futureCasedStoresInBounds) {
		this.futureCasedStoresInBounds = futureCasedStoresInBounds;
	}

	public Integer getFutureCasedStoresOutOfBounds() {
		return futureCasedStoresOutOfBounds;
	}

	public void setFutureCasedStoresOutOfBounds(Integer futureCasedStoresOutOfBounds) {
		this.futureCasedStoresOutOfBounds = futureCasedStoresOutOfBounds;
	}

	public Integer getHistoricalStoresInBounds() {
		return historicalStoresInBounds;
	}

	public void setHistoricalStoresInBounds(Integer historicalStoresInBounds) {
		this.historicalStoresInBounds = historicalStoresInBounds;
	}

	public Integer getHistoricalCasedStoresInBounds() {
		return historicalCasedStoresInBounds;
	}

	public void setHistoricalCasedStoresInBounds(Integer historicalCasedStoresInBounds) {
		this.historicalCasedStoresInBounds = historicalCasedStoresInBounds;
	}

	public Integer getHistoricalCasedStoresOutOfBounds() {
		return historicalCasedStoresOutOfBounds;
	}

	public void setHistoricalCasedStoresOutOfBounds(Integer historicalCasedStoresOutOfBounds) {
		this.historicalCasedStoresOutOfBounds = historicalCasedStoresOutOfBounds;
	}
}

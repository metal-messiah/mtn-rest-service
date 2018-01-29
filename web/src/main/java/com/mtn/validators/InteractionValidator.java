package com.mtn.validators;

import com.mtn.model.domain.Interaction;
import com.mtn.model.domain.ShoppingCenter;
import com.mtn.model.domain.Store;
import com.mtn.repository.ShoppingCenterRepository;
import com.mtn.repository.StoreRepository;
import com.mtn.service.InteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InteractionValidator extends ValidatingDataService<Interaction> {

	@Autowired
	private InteractionService interactionService;
	@Autowired
	private StoreRepository storeRepository;
	@Autowired
	private ShoppingCenterRepository shoppingCenterRepository;

	@Override
	public InteractionService getEntityService() {
		return interactionService;
	}

	@Override
	public void validateBusinessRules(Interaction object) {
		if (object.getProject() == null) {
			throw new IllegalArgumentException("Interaction project must be provided");
		}

		//If no store, but we have either a store survey or store casing, set the store
		setStoreIfNotPresent(object);

		//If no shopping center, but we have one of the other five fields, set the shopping center
		setShoppingCenterIfNotPresent(object);

		//Validate that the store survey and store casing actually relate to the given store
		validateStoreSurveyAndCasingRelateToStore(object);

		//Validate that the shopping center survey and casing actually relate to the given shopping center
		validateShoppingCenterSurveyAndCasingRelateToShoppingCenter(object);

		//Validate that the store actually relates to the given shopping center
		validateStoreRelatesToShoppingCenter(object);
	}

	private void validateStoreSurveyAndCasingRelateToStore(Interaction object) {
		if (object.getStore() != null) {
			if (object.getStoreSurvey() != null) {
				Store actualStore = storeRepository.findOneBySurveysId(object.getStoreSurvey().getId());
				if (actualStore == null || !actualStore.getId().equals(object.getStore().getId())) {
					throw new IllegalArgumentException("Interaction storeSurvey must belong to Interaction store");
				}
			}
			if (object.getStoreCasing() != null) {
				Store actualStore = storeRepository.findOneByCasingsId(object.getStoreCasing().getId());
				if (actualStore == null || !actualStore.getId().equals(object.getStore().getId())) {
					throw new IllegalArgumentException("Interaction storeCasing must belong to Interaction store");
				}
			}
		}
	}

	private void validateShoppingCenterSurveyAndCasingRelateToShoppingCenter(Interaction object) {
		if (object.getShoppingCenter() != null) {
			if (object.getShoppingCenterSurvey() != null) {
				ShoppingCenter actualShoppingCenter = shoppingCenterRepository.findOneBySurveysId(object.getShoppingCenterCasing().getId());
				if (actualShoppingCenter == null || !actualShoppingCenter.getId().equals(object.getShoppingCenter().getId())) {
					throw new IllegalArgumentException("Interaction shoppingCenterSurvey must belong to Interaction shoppingCenter");
				}
			}
			if (object.getShoppingCenterCasing() != null) {
				ShoppingCenter actualShoppingCenter = shoppingCenterRepository.findOneByCasingsId(object.getShoppingCenterCasing().getId());
				if (actualShoppingCenter == null || !actualShoppingCenter.getId().equals(object.getShoppingCenter().getId())) {
					throw new IllegalArgumentException("Interaction shoppingCenterCasing must belong to Interaction shoppingCenter");
				}
			}
		}
	}

	private void validateStoreRelatesToShoppingCenter(Interaction object) {
		if (object.getShoppingCenter() != null) {
			if (object.getStore() != null) {
				ShoppingCenter actualShoppingCenter = shoppingCenterRepository.findOneBySitesStoresId(object.getStore().getId());
				if (actualShoppingCenter == null || !actualShoppingCenter.getId().equals(object.getShoppingCenter().getId())) {
					throw new IllegalArgumentException("Interaction store must belong to Interaction shoppingCenter");
				}
			}
		}
	}

	private void setStoreIfNotPresent(Interaction object) {
		if (object.getStore() == null) {
			if (object.getStoreSurvey() != null) {
				object.setStore(storeRepository.findOneBySurveysId(object.getStoreSurvey().getId()));
			} else if (object.getStoreCasing() != null) {
				object.setStore(storeRepository.findOneByCasingsId(object.getStoreCasing().getId()));
			}
		}
	}

	private void setShoppingCenterIfNotPresent(Interaction object) {
		if (object.getShoppingCenter() == null) {
			if (object.getShoppingCenterSurvey() != null) {
				object.setShoppingCenter(shoppingCenterRepository.findOneBySurveysId(object.getShoppingCenterSurvey().getId()));
			} else if (object.getShoppingCenterCasing() != null) {
				object.setShoppingCenter(shoppingCenterRepository.findOneByCasingsId(object.getShoppingCenterCasing().getId()));
			} else if (object.getStore() != null) {
				object.setShoppingCenter(shoppingCenterRepository.findOneBySitesStoresId(object.getStore().getId()));
			}
		}
	}
}

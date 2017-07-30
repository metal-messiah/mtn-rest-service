package com.mtn.service;

import com.mtn.exception.VersionConflictException;
import com.mtn.model.domain.Interaction;
import com.mtn.model.domain.ShoppingCenter;
import com.mtn.model.domain.Store;
import com.mtn.model.domain.UserProfile;
import com.mtn.model.view.SimpleInteractionView;
import com.mtn.repository.InteractionRepository;
import com.mtn.repository.ShoppingCenterRepository;
import com.mtn.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.mtn.repository.specification.InteractionSpecifications.idEquals;
import static com.mtn.repository.specification.InteractionSpecifications.isNotDeleted;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Created by Allen on 5/6/2017.
 */
@Service
public class InteractionService extends ValidatingDataService<Interaction> {

    @Autowired
    private InteractionRepository interactionRepository;
    @Autowired
    private ShoppingCenterRepository shoppingCenterRepository;
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private SecurityService securityService;

    @Transactional
    public Interaction addOne(Interaction request) {
        validateForInsert(request);

        UserProfile currentUser = securityService.getCurrentPersistentUser();
        request.setCreatedBy(currentUser);
        request.setUpdatedBy(currentUser);

        return interactionRepository.save(request);
    }

    @Transactional
    public void deleteOne(Integer id) {
        Interaction existing = findOneUsingSpecs(id);
        if (existing == null) {
            throw new IllegalArgumentException("No Interaction found with this id");
        }

        existing.setDeletedBy(securityService.getCurrentPersistentUser());
    }

    public List<Interaction> findAll() {
        return interactionRepository.findAll();
    }

    public List<Interaction> findAllUsingSpecs() {
        return interactionRepository.findAll(
                where(isNotDeleted())
        );
    }

    public Interaction findOne(Integer id) {
        return interactionRepository.findOne(id);
    }

    public Interaction findOneUsingSpecs(Integer id) {
        return interactionRepository.findOne(
                where(idEquals(id))
                        .and(isNotDeleted())
        );
    }

    @Transactional
    public Interaction updateOne(Integer id, Interaction request) {
        validateNotNull(request);
        validateForUpdate(request);

        Interaction existing = findOneUsingSpecs(id);
        if (existing == null) {
            throw new IllegalArgumentException("No Interaction found with this id");
        }
        if (!request.getVersion().equals(existing.getVersion())) {
            throw new VersionConflictException(new SimpleInteractionView(existing));
        }

        existing.setInteractionDate(request.getInteractionDate());
        existing.setProject(request.getProject());
        existing.setShoppingCenter(request.getShoppingCenter());
        existing.setShoppingCenterCasing(request.getShoppingCenterCasing());
        existing.setShoppingCenterSurvey(request.getShoppingCenterSurvey());
        existing.setStore(request.getStore());
        existing.setStoreCasing(request.getStoreCasing());
        existing.setStoreSurvey(request.getStoreSurvey());
        existing.setVersion(request.getVersion());
        existing.setUpdatedBy(securityService.getCurrentPersistentUser());

        return existing;
    }

    @Override
    public String getEntityName() {
        return "Interaction";
    }

    @Override
    public void validateForInsert(Interaction object) {
        super.validateForInsert(object);

        if (object.getVersion() == null) {
            throw new IllegalArgumentException("Interaction version must be provided");
        }
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

    @Override
    public void validateDoesNotExist(Interaction object) {
        //No unique contraints to enforce
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
}

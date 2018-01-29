package com.mtn.service;

import com.mtn.model.domain.Interaction;
import com.mtn.repository.InteractionRepository;
import com.mtn.validators.InteractionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.mtn.repository.specification.InteractionSpecifications.idEquals;
import static com.mtn.repository.specification.InteractionSpecifications.isNotDeleted;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Created by Allen on 5/6/2017.
 */
@Service
public class InteractionServiceImpl extends EntityServiceImpl<Interaction> implements InteractionService {

    @Autowired
    private InteractionRepository interactionRepository;
    @Autowired
    private InteractionValidator interactionValidator;

    @Override
    public InteractionRepository getEntityRepository() {
        return interactionRepository;
    }

    @Override
    public InteractionValidator getValidator() {
        return interactionValidator;
    }

    @Override
    public Page<Interaction> findAllUsingSpecs(Pageable page) {
        return getEntityRepository().findAll(
                where(isNotDeleted()),
                page
        );
    }

    @Override
    public Interaction findOneUsingSpecs(Integer id) {
        return getEntityRepository().findOne(
                where(idEquals(id))
                        .and(isNotDeleted())
        );
    }

    @Override
    public void handleAssociationsOnDeletion(Interaction existing) {

    }

    @Override
    public void handleAssociationsOnCreation(Interaction request) {

    }

    @Override
    public Interaction getUpdatedEntity(Interaction existing, Interaction request) {

        existing.setInteractionDate(request.getInteractionDate());
        existing.setProject(request.getProject());
        existing.setShoppingCenter(request.getShoppingCenter());
        existing.setShoppingCenterCasing(request.getShoppingCenterCasing());
        existing.setShoppingCenterSurvey(request.getShoppingCenterSurvey());
        existing.setStore(request.getStore());
        existing.setStoreCasing(request.getStoreCasing());
        existing.setStoreSurvey(request.getStoreSurvey());

        return existing;
    }

    @Override
    public String getEntityName() {
        return "Interaction";
    }

}

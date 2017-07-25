package com.mtn.service;

import com.mtn.model.domain.Identifiable;

/**
 * Created by Allen on 4/22/2017.
 */
public abstract class ValidatingDataService<T extends Identifiable> {

    public abstract String getEntityName();
    public abstract void validateBusinessRules(T object);
    public abstract void validateDoesNotExist(T object);

    public void validateForInsert(T object) {
        validateNotNull(object);
        validateBusinessRules(object);
        validateDoesNotExist(object);

        if (object.getId() != null) {
            throw new IllegalArgumentException(String.format("%s id must be null", getEntityName()));
        }
    }

    public void validateForUpdate(T object) {
        validateNotNull(object);
        if (object.getId() == null) {
            throw new IllegalArgumentException(String.format("%s id must be provided", getEntityName()));
        }

        validateBusinessRules(object);
    }

    public void validateNotNull(T object) {
        if (object == null) {
            throw new IllegalArgumentException(String.format("%s must not be null", getEntityName()));
        }
    }
}

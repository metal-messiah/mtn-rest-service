package com.mtn.service;

import com.mtn.exception.InvalidEntityException;
import com.mtn.model.domain.Identifiable;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * Created by Allen on 4/22/2017.
 */
public abstract class ValidatingDataService<T extends Identifiable> {

    public abstract String getEntityName();
    public abstract void validateBusinessRules(T object);
    public abstract void validateUnique(T object);

    public void validateForInsert(T object) {
        validateNotNull(object);
        validateConstraints(object);
        validateBusinessRules(object);
        validateUnique(object);

        if (object.getId() != null) {
            throw new IllegalArgumentException(String.format("%s id must be null", getEntityName()));
        }
    }

    public void validateForUpdate(T object) {
        validateNotNull(object);
        if (object.getId() == null) {
            throw new IllegalArgumentException(String.format("%s id must be provided", getEntityName()));
        }

        validateConstraints(object);
        validateBusinessRules(object);
    }

    public void validateNotNull(T object) {
        if (object == null) {
            throw new IllegalArgumentException(String.format("%s must not be null", getEntityName()));
        }
    }

    private void validateConstraints(T object) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set constraintViolations = validator.validate(object);
        if (constraintViolations.size() > 0) {
            throw new InvalidEntityException(object);
        }
    }
}

package com.mtn.validators;

import com.mtn.exception.InvalidEntityException;
import com.mtn.exception.VersionConflictException;
import com.mtn.model.domain.AuditingEntity;
import com.mtn.model.domain.Identifiable;
import com.mtn.service.EntityService;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * Created by Allen on 4/22/2017.
 */
public abstract class ValidatingDataService<T extends AuditingEntity & Identifiable> {

    public abstract EntityService<T> getEntityService();

    public void validateBusinessRules(T object) {
        // No Special business rules;
    }

    private void validateUnique(T object) {
        // No Unique value check
        Identifiable existing = getPotentialDuplicate(object);
        if (existing != null && !existing.getId().equals(object.getId())) {
            throw new IllegalArgumentException("Duplicate resource exists");
        }
    }

    /*
    Override this method to find duplicate by entity specific attribute (email, displayName, etc.)
     */
    public Identifiable getPotentialDuplicate(T object) {
        return object;
    }

    public void validateForInsert(T object) {
        validateNotNull(object);
        validateConstraints(object);
        validateBusinessRules(object);
        validateUnique(object);

        if (object.getId() != null) {
            throw new IllegalArgumentException(String.format("%s id must be null", getEntityService().getEntityName()));
        }
    }

    public void validateForUpdate(T object, T existing) {
        validateNotNull(object);
        validateHasId(object);
        validateUnique(object);
        validateConstraints(object);
        validateBusinessRules(object);
        validateIdsMatch(object, existing);
        validateVersion(object, existing);
    }

    public void validateForDelete(T object) {
        validateNotNull(object);
    }

    private void validateVersion(T object, T existing) {
        if (object.getVersion() == null) {
            throw new IllegalArgumentException("Client must supply the version of the resource to avoid overwriting changes");
        }
        if (!object.getVersion().equals(existing.getVersion())) {
            throw new VersionConflictException(existing);
        }
    }

    private void validateIdsMatch(T object, T existing) {
        if (!object.getId().equals(existing.getId())) {
            throw new IllegalArgumentException("URI ID and sent object ID must match! Cannot update one object using another. Copy data on client side.");
        }
    }

    private void validateHasId(T object) {
        if (object.getId() == null) {
            throw new IllegalArgumentException(String.format("%s id must be provided", getEntityService().getEntityName()));
        }
    }

    public void validateNotNull(T object) {
        if (object == null) {
            throw new IllegalArgumentException(String.format("%s must not be null", getEntityService().getEntityName()));
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

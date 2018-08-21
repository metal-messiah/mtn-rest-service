package com.mtn.validators;

import com.mtn.exception.InvalidEntityException;
import com.mtn.exception.VersionConflictException;
import com.mtn.model.domain.AuditingEntity;
import com.mtn.model.view.AuditingEntityView;
import com.mtn.repository.EntityRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * Created by Allen on 4/22/2017.
 */
@Service
public abstract class EntityValidator<T extends AuditingEntity, V extends AuditingEntityView> {

    private final EntityRepository<T> entityRepository;

    public EntityValidator(EntityRepository<T> entityRepository) {
        this.entityRepository = entityRepository;
    }

    protected void validateUpdateInsertBusinessRules(V request) {
        // No Special business rules; Override to enforce business rules.
        // Ex. No two companies may have the same exact name
    }

    protected void validateDeletionBusinessRules(Integer id) {
        // No Special business rules; Override to enforce business rules.
        // Ex. May not delete Role with id 1 (System Admin)
    }

    public void validateForInsert(V request) {
        // Check that we aren't saving an existing item a second time - should use POST for that
        if (request.getId() != null) {
            throw new IllegalArgumentException(("ID must be null. To Update use PUT"));
        }
        // ???
        validateConstraints(request);
        // Check that values being saved are legitimate
        validateUpdateInsertBusinessRules(request);
    }

    public void validateForUpdate(V request) {
        // Request must have id in an update statement
        if (request.getId() == null) {
            throw new IllegalArgumentException("ID must be provided!");
        }
        // The Id must correspond to a record in the DB
        validateObjectExists(request.getId());
        // ???
        validateConstraints(request);
        // Check that values being saved are legitimate
        validateUpdateInsertBusinessRules(request);
        // Ensure that a newer version hasn't already been saved
        validateVersion(request);
    }

    public void validateForDelete(Integer id) {
        // Should never reach this point without an id. Enforced in several places
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null for deletion request");
        }
        // Object must exist in DB before it can be deleted
        validateObjectExists(id);
        // Don't delete things according to business rules (ex. System Admin Role)
        validateDeletionBusinessRules(id);
    }

    private void validateVersion(V request) {
        if (request.getVersion() == null) {
            throw new IllegalArgumentException("Client must supply the version of the resource to avoid overwriting changes");
        }
        T existing = this.entityRepository.findOne(request.getId());
        if (!request.getVersion().equals(existing.getVersion())) {
            throw new VersionConflictException(existing);
        }
    }

    private void validateObjectExists(Integer id) {
        T object = this.entityRepository.findOne(id);
        if (object == null) {
            throw new EntityNotFoundException();
        }
    }

    // Not Sure what this does
    private void validateConstraints(V object) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set constraintViolations = validator.validate(object);
        if (constraintViolations.size() > 0) {
            throw new InvalidEntityException(object);
        }
    }
}

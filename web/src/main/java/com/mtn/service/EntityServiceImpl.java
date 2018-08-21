package com.mtn.service;

import com.mtn.model.domain.AuditingEntity;
import com.mtn.model.domain.UserProfile;
import com.mtn.model.view.AuditingEntityView;
import com.mtn.repository.EntityRepository;
import com.mtn.repository.specification.AuditingEntitySpecifications;
import com.mtn.validators.EntityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

import static org.springframework.data.jpa.domain.Specifications.where;

@Service
public abstract class EntityServiceImpl<T extends AuditingEntity, V extends AuditingEntityView> implements EntityService<T, V> {

	protected final UserProfileService userProfileService;
	protected final SecurityService securityService;
	protected final EntityRepository<T> repository;
	protected final EntityValidator<T, V> validator;

	@Autowired
	public EntityServiceImpl(EntityServiceDependencies services,
							 EntityRepository<T> repository,
							 EntityValidator<T, V> validator) {
		this.userProfileService = services.getUserProfileService();
		this.securityService = services.getSecurityService();
		this.repository = repository;
		this.validator = validator;
	}

	@Override
	final public T findOne(Integer id) {
		T object = repository.findOne(id);
		if (object == null) {
			throw new EntityNotFoundException();
		}
		return object;
	}

	@Override
	final public Page<T> findAll(Pageable page) {
		return repository.findAll(page);
	}

	@Override
	public Page<T> findAllUsingSpecs(Pageable page) {
		return this.repository.findAll(
				where(AuditingEntitySpecifications.isNotDeleted()),
				page
		);
	}

	@Override
	public T findOneUsingSpecs(Integer id) {
		T object = this.repository.findOne(
				where(AuditingEntitySpecifications.<T>idEquals(id))
						.and(AuditingEntitySpecifications.isNotDeleted()));
		if (object == null) {
			throw new EntityNotFoundException();
		}
		return object;
	}

	@Override
	@Transactional
	public T addOne(V request) {
		this.validator.validateForInsert(request);

		UserProfile currentUser = securityService.getCurrentUser();

		T newEntity = createNewEntityFromRequest(request);
		newEntity.setCreatedBy(currentUser);
		newEntity.setUpdatedBy(currentUser);

		return newEntity;
	}

	@Override
	@Transactional
	public T updateOne(V request) {
		this.validator.validateForUpdate(request);

		T updatedEntity = updateEntityFromRequest(request);
		updatedEntity.setUpdatedBy(securityService.getCurrentUser());

		return updatedEntity;
	}

	@Override
	@Transactional
	public void deleteOne(Integer id) {
		this.validator.validateForDelete(id);

		T existing = findOneUsingSpecs(id);
		handleAssociationsOnDeletion(existing);

		existing.setDeletedBy(securityService.getCurrentUser());
		existing.setDeletedDate(LocalDateTime.now());
	}

	@Override
	final public T createNewEntityFromRequest(V request) {
		T extractionField = this.createNewEntity();
		this.setEntityAttributesFromRequest(extractionField, request);
		return extractionField;
	}

	@Override
	final public T updateEntityFromRequest(V request) {
		T extractionField = this.repository.findOne(request.getId());
		this.setEntityAttributesFromRequest(extractionField, request);
		return extractionField;
	}

	protected abstract T createNewEntity();

	protected abstract void setEntityAttributesFromRequest(T entity, V request);

}

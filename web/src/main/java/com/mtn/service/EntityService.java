package com.mtn.service;

import com.mtn.model.domain.AuditingEntity;
import com.mtn.model.domain.UserProfile;
import com.mtn.model.view.AuditingEntityView;
import com.mtn.repository.EntityRepository;
import com.mtn.repository.specification.AuditingEntitySpecifications;
import com.mtn.validators.EntityValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.function.Supplier;

import static org.springframework.data.jpa.domain.Specifications.where;

public abstract class EntityService<T extends AuditingEntity, V extends AuditingEntityView> {

	protected final SecurityService securityService;
	protected final EntityRepository<T> repository;
	protected final EntityValidator<T, V> validator;
	private final Supplier<T> supplier;

	public EntityService(SecurityService securityService,
						 EntityRepository<T> repository,
						 EntityValidator<T, V> validator,
						 Supplier<T> supplier) {
		this.securityService = securityService;
		this.repository = repository;
		this.validator = validator;
		this.supplier = supplier;
	}

	public Page<T> findAll(Pageable page) {
		return repository.findAll(page);
	}

	public Page<T> findAllUsingSpecs(Pageable page) {
		return this.repository.findAll(
				where(AuditingEntitySpecifications.isNotDeleted()),
				page
		);
	}

	public T findOne(Integer id) {
		T object = repository.findOne(id);
		if (object == null) {
			throw new EntityNotFoundException();
		}
		return object;
	}

	public T findOneUsingSpecs(Integer id) {
		T object = this.repository.findOne(
				where(AuditingEntitySpecifications.<T>idEquals(id))
						.and(AuditingEntitySpecifications.isNotDeleted()));
		if (object == null) {
			throw new EntityNotFoundException();
		}
		return object;
	}

	public T addOne(V request) {
		this.validator.validateForInsert(request);

		UserProfile currentUser = securityService.getCurrentUser();

		T newEntity = createNewEntityFromRequest(request);
		newEntity.setCreatedBy(currentUser);
		newEntity.setUpdatedBy(currentUser);

		// Must call save to attach to transaction
		return this.repository.save(newEntity);
	}

	@Transactional
	public T updateOne(V request) {
		this.validator.validateForUpdate(request);

		T updatedEntity = this.repository.findOne(request.getId());
		this.setEntityAttributesFromRequest(updatedEntity, request);
		updatedEntity.setUpdatedBy(securityService.getCurrentUser());

		return updatedEntity;
	}

	@Transactional
	public void deleteOne(Integer id) {
		this.validator.validateForDelete(id);

		T existing = findOneUsingSpecs(id);
		handleAssociationsOnDeletion(existing);

		existing.setDeletedBy(securityService.getCurrentUser());
		existing.setDeletedDate(LocalDateTime.now());
	}

	public T createNewEntityFromRequest(V request) {
		T entity = this.supplier.get();
		this.setEntityAttributesFromRequest(entity, request);
		return entity;
	}

	protected abstract void setEntityAttributesFromRequest(T entity, V request);

	protected abstract void handleAssociationsOnDeletion(T existing);


}

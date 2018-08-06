package com.mtn.service;

import com.mtn.model.domain.AuditingEntity;
import com.mtn.model.domain.UserProfile;
import com.mtn.repository.EntityRepository;
import com.mtn.repository.specification.AuditingEntitySpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.springframework.data.jpa.domain.Specifications.where;

public abstract class EntityServiceImpl<T extends AuditingEntity> implements EntityService<T> {

	@Autowired
	protected UserProfileService userProfileService;
	@Autowired
	protected SecurityService securityService;
	@Autowired
	private EntityRepository<T> entityRepository;

	@Override
	final public T findOne(Integer id) {
		return entityRepository.findOne(id);
	}

	@Override
	final public Page<T> findAll(Pageable page) {
		return entityRepository.findAll(page);
	}

	@Override
	public Page<T> findAllUsingSpecs(Pageable page) {
		JpaSpecificationExecutor<T> executor = this.entityRepository;
		return executor.findAll(
				where(AuditingEntitySpecifications.isNotDeleted()),
				page
		);
	}

	@Override
	public T findOneUsingSpecs(Integer id) {
		JpaSpecificationExecutor<T> executor = this.entityRepository;
		return executor.findOne(
				where(AuditingEntitySpecifications.<T>idEquals(id))
						.and(AuditingEntitySpecifications.isNotDeleted())
		);
	}


	@Override
	@Transactional
	public T addOne(T request) {
		getValidator().validateForInsert(request);

		UserProfile currentUser = securityService.getCurrentUser();
		request.setCreatedBy(currentUser);
		request.setUpdatedBy(currentUser);

		handleAssociationsOnCreation(request);

		return entityRepository.save(request);
	}

	@Override
	@Transactional
	public void deleteOne(Integer id) {
		T existing = findOneUsingSpecs(id);
		getValidator().validateForDelete(existing);

		handleAssociationsOnDeletion(existing);

		existing.setDeletedBy(securityService.getCurrentUser());
		existing.setDeletedDate(LocalDateTime.now());
	}

	@Override
	@Transactional
	public T reactivateOne(T existingEntity, T request) {
		existingEntity.setDeletedBy(null);
		existingEntity.setDeletedDate(null);

		return updateEntityAttributes(existingEntity, request);
	}

	@Override
	@Transactional
	public T updateOne(Integer id, T request) {
		T existing = findOneUsingSpecs(id);
		getValidator().validateForUpdate(request, existing);
		updateEntityAttributes(existing, request);
		existing.setUpdatedBy(securityService.getCurrentUser());
		return existing;
	}

	@Override
	public void handleAssociationsOnCreation(T request) {

	}

	@Override
	public void handleAssociationsOnDeletion(T existing) {

	}
}

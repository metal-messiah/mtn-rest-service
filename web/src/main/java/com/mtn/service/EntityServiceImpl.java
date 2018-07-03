package com.mtn.service;

import com.mtn.model.domain.AuditingEntity;
import com.mtn.model.domain.Identifiable;
import com.mtn.model.domain.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;

public abstract class EntityServiceImpl<T extends AuditingEntity & Identifiable> implements EntityService<T> {

	@Autowired
	protected UserProfileService userProfileService;
	@Autowired
	protected SecurityService securityService;
	@PersistenceContext
	protected EntityManager entityManager;

	@Override
	final public T findOne(Integer id) {
		return getEntityRepository().findOne(id);
	}

	@Override
	final public Page<T> findAll(Pageable page) {
		return getEntityRepository().findAll(page);
	}

	@Override
	@Transactional
	public T addOne(T request) {
		getValidator().validateForInsert(request);

		UserProfile currentUser = securityService.getCurrentUser();
		request.setCreatedBy(currentUser);
		request.setUpdatedBy(currentUser);

		handleAssociationsOnCreation(request);

		return getEntityRepository().save(request);
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
		existing.setVersion(existing.getVersion() + 1);
		existing.setUpdatedBy(securityService.getCurrentUser());
		return existing;
	}
}

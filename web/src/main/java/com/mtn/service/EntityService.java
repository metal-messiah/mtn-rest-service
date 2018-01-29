package com.mtn.service;

import com.mtn.model.domain.AuditingEntity;
import com.mtn.model.domain.Identifiable;
import com.mtn.validators.ValidatingDataService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntityService<T extends AuditingEntity & Identifiable> {
	T addOne(T request);

	void deleteOne(Integer id);

	Page<T> findAll(Pageable page);

	Page<T> findAllUsingSpecs(Pageable page);

	T findOne(Integer id);

	T findOneUsingSpecs(Integer id);

	T reactivateOne(T existingEntity, T request);

	T updateOne(Integer id, T request);

	T getUpdatedEntity(T existing, T request);

	String getEntityName();

	void handleAssociationsOnDeletion(T existing);

	void handleAssociationsOnCreation(T request);

	JpaRepository<T, Integer> getEntityRepository();

	ValidatingDataService<T> getValidator();
}

package com.mtn.service;

import com.mtn.model.domain.AuditingEntity;
import com.mtn.validators.ValidatingDataService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EntityService<T extends AuditingEntity> {
	T addOne(T request);

	void deleteOne(Integer id);

	Page<T> findAll(Pageable page);

	Page<T> findAllUsingSpecs(Pageable page);

	T findOne(Integer id);

	T findOneUsingSpecs(Integer id);

	T reactivateOne(T existingEntity, T request);

	T updateOne(Integer id, T request);

	T updateEntityAttributes(T existing, T request);

	String getEntityName();

	void handleAssociationsOnDeletion(T existing);

	void handleAssociationsOnCreation(T request);

	ValidatingDataService<T> getValidator();

}

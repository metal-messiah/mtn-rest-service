package com.mtn.service;

import com.mtn.model.domain.AuditingEntity;
import com.mtn.model.view.AuditingEntityView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EntityService<T extends AuditingEntity, V extends AuditingEntityView> {
	T addOne(V request);

	void deleteOne(Integer id);

	Page<T> findAll(Pageable page);

	Page<T> findAllUsingSpecs(Pageable page);

	T findOne(Integer id);

	T findOneUsingSpecs(Integer id);

	T updateOne(V request);

	void handleAssociationsOnDeletion(T existing);

	T createNewEntityFromRequest(V request);

	T updateEntityFromRequest(V request);

}

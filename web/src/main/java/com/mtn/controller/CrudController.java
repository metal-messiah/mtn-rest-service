package com.mtn.controller;

import com.mtn.model.domain.AuditingEntity;
import com.mtn.model.view.AuditingEntityView;
import com.mtn.service.EntityService;
import org.springframework.http.ResponseEntity;

public interface CrudController<T extends AuditingEntity, V extends AuditingEntityView> {
	ResponseEntity addOne(T request);

	ResponseEntity deleteOne(Integer id);

	ResponseEntity findOne(Integer id);

	ResponseEntity updateOne(Integer id, T request);

	EntityService<T, V> getEntityService();

	Object getViewFromModel(T model);

	Object getSimpleViewFromModel(T model);
}

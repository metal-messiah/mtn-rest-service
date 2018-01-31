package com.mtn.controller;

import com.mtn.model.domain.AuditingEntity;
import com.mtn.model.domain.Identifiable;
import com.mtn.service.EntityService;
import org.springframework.http.ResponseEntity;

public interface CrudController<T extends AuditingEntity & Identifiable> {
	ResponseEntity addOne(T request);

	ResponseEntity deleteOne(Integer id);

	ResponseEntity findOne(Integer id);

	ResponseEntity updateOne(Integer id, T request);

	EntityService<T> getEntityService();

	Object getViewFromModel(T model);

	Object getSimpleViewFromModel(T model);
}

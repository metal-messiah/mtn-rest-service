package com.mtn.controller;

import com.mtn.model.domain.AuditingEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public abstract class CrudControllerImpl<T extends AuditingEntity> implements CrudController<T> {

	@RequestMapping(method = RequestMethod.POST)
	final public ResponseEntity addOne(@RequestBody T request) {
		T domainModel = getEntityService().addOne(request);
		return ResponseEntity.ok(getViewFromModel(domainModel));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	final public ResponseEntity deleteOne(@PathVariable("id") Integer id) {
		getEntityService().deleteOne(id);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	final public ResponseEntity findOne(@PathVariable("id") Integer id) {
		T domainModel = getEntityService().findOneUsingSpecs(id);
		if (domainModel == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(getViewFromModel(domainModel));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	final public ResponseEntity updateOne(@PathVariable("id") Integer id, @RequestBody T request) {
		T domainModel = getEntityService().updateOne(id, request);
		return ResponseEntity.ok(getViewFromModel(domainModel));
	}
}

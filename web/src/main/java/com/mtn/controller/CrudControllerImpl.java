package com.mtn.controller;

import com.mtn.model.domain.AuditingEntity;
import com.mtn.model.view.AuditingEntityView;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public abstract class CrudControllerImpl<T extends AuditingEntity, V extends AuditingEntityView> implements CrudController<T, V> {

	@PostMapping
	public ResponseEntity addOne(@RequestBody V request) {
		T domainModel = getEntityService().addOne(request);
		return ResponseEntity.ok(getViewFromModel(domainModel));
	}

	@DeleteMapping(value = "/{id}")
	final public ResponseEntity deleteOne(@PathVariable("id") Integer id) {
		getEntityService().deleteOne(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping(value = "/{id}")
	final public ResponseEntity findOne(@PathVariable("id") Integer id) {
		T domainModel = getEntityService().findOneUsingSpecs(id);
		if (domainModel == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(getViewFromModel(domainModel));
	}

	@PutMapping
	final public ResponseEntity updateOne(@RequestBody V request) {
		T domainModel = getEntityService().updateOne(request);
		return ResponseEntity.ok(getViewFromModel(domainModel));
	}
}

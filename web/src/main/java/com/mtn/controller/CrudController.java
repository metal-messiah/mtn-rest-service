package com.mtn.controller;

import com.mtn.model.domain.AuditingEntity;
import com.mtn.model.view.AuditingEntityView;
import com.mtn.service.EntityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.function.Function;

public abstract class CrudController<T extends AuditingEntity, V extends AuditingEntityView> {

	EntityService<T, V> entityService;
	private final Function<T, V> getViewForEntity;

	public CrudController(EntityService<T, V> entityService, Function<T, V> getViewForEntity) {
		this.entityService = entityService;
		this.getViewForEntity = getViewForEntity;
	}

	@PostMapping
	public ResponseEntity<V> addOne(@RequestBody V request) {
		T domainModel = this.entityService.addOne(request);
		Assert.notNull(domainModel.getId(), "Failed to save. ID is null");
		return ResponseEntity.ok(this.getViewForEntity.apply(domainModel));
	}

	@DeleteMapping(value = "/{id}")
	final public ResponseEntity deleteOne(@PathVariable("id") Integer id) {
		this.entityService.deleteOne(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping(value = "/{id}")
	final public ResponseEntity<V> findOne(@PathVariable("id") Integer id) {
		T domainModel = this.entityService.findOneUsingSpecs(id);
		if (domainModel == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(this.getViewForEntity.apply(domainModel));
	}

	@PutMapping
	public ResponseEntity<V> updateOne(@RequestBody V request) {
		T domainModel = this.entityService.updateOne(request);
		return ResponseEntity.ok(this.getViewForEntity.apply(domainModel));
	}

}

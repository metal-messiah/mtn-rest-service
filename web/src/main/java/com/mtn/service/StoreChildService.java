package com.mtn.service;

import com.mtn.model.domain.AuditingEntity;
import com.mtn.model.view.AuditingEntityView;
import com.mtn.repository.EntityRepository;
import com.mtn.repository.specification.AuditingEntitySpecifications;
import com.mtn.repository.specification.StoreChildSpecifications;
import com.mtn.validators.EntityValidator;

import java.util.List;
import java.util.function.Supplier;

import static org.springframework.data.jpa.domain.Specifications.where;

public abstract class StoreChildService<T extends AuditingEntity, V extends AuditingEntityView> extends EntityService<T, V> {

	StoreChildService(SecurityService securityService,
							 EntityRepository<T> repository,
							 EntityValidator<T, V> validator,
							 Supplier<T> supplier) {
		super(securityService, repository, validator, supplier);
	}

	public List<T> findAllByStoreId(Integer storeId) {
		return this.repository.findAll(where(StoreChildSpecifications.storeIdEquals(storeId)));
	}

	public List<T> findAllByStoreIdUsingSpecs(Integer storeId) {
		return this.repository.findAll(
				where(StoreChildSpecifications.<T>storeIdEquals(storeId))
						.and(AuditingEntitySpecifications.isNotDeleted()));
	}

}

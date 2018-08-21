package com.mtn.service;

import com.mtn.model.domain.AuditingEntity;
import com.mtn.model.view.AuditingEntityView;
import com.mtn.repository.EntityRepository;
import com.mtn.repository.specification.AuditingEntitySpecifications;
import com.mtn.repository.specification.StoreChildSpecifications;
import com.mtn.validators.EntityValidator;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.jpa.domain.Specifications.where;

@Service
public abstract class StoreChildServiceImpl<T extends AuditingEntity, V extends AuditingEntityView> extends EntityServiceImpl<T, V> {

	public StoreChildServiceImpl(EntityServiceDependencies services,
								 EntityRepository<T> repository,
								 EntityValidator<T, V> validator) {
		super(services, repository, validator);
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

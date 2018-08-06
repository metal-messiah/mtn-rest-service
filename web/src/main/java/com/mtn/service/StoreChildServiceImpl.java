package com.mtn.service;

import com.mtn.model.domain.AuditingEntity;
import com.mtn.repository.EntityRepository;
import com.mtn.repository.specification.AuditingEntitySpecifications;
import com.mtn.repository.specification.StoreChildSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.jpa.domain.Specifications.where;

@Service
public abstract class StoreChildServiceImpl<T extends AuditingEntity> extends EntityServiceImpl<T> implements StoreChildService<T> {

	@Autowired
	private EntityRepository<T> storeChildRepository;

	@Override
	public List<T> findAllByStoreId(Integer storeId) {
		return storeChildRepository.findAll(where(StoreChildSpecifications.storeIdEquals(storeId)));
	}

	@Override
	public List<T> findAllByStoreIdUsingSpecs(Integer storeId) {
		return storeChildRepository.findAll(
				where(StoreChildSpecifications.<T>storeIdEquals(storeId))
						.and(AuditingEntitySpecifications.isNotDeleted()));
	}

}

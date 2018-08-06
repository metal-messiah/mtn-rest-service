package com.mtn.repository;

import com.mtn.model.domain.AuditingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EntityRepository<T extends AuditingEntity> extends JpaRepository<T, Integer>, JpaSpecificationExecutor<T> {
}

package com.mtn.repository;

import com.mtn.model.domain.ResourceQuota;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ResourceQuotaRepository extends EntityRepository<ResourceQuota> {
    @Query(value = "SELECT r FROM ResourceQuota r " + "WHERE r.resourceName = :name " + "AND r.deletedDate IS NOT NULL ")
    ResourceQuota findNewestRecordByName(@Param("name") String name);
}

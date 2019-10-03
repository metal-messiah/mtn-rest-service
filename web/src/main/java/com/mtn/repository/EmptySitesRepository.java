package com.mtn.repository;

import com.mtn.model.domain.EmptySites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EmptySitesRepository extends JpaRepository<EmptySites, Integer>, JpaSpecificationExecutor<EmptySites> {

}

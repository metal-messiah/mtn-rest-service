package com.mtn.repository;

import com.mtn.model.domain.ExtractionField;
import com.mtn.model.domain.ExtractionFieldSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by Tyler on 7/5/2018.
 */
public interface ExtractionFieldSetRepository extends JpaRepository<ExtractionFieldSet, Integer>, JpaSpecificationExecutor<ExtractionFieldSet> {

}

package com.mtn.service;

import com.mtn.model.domain.ExtractionFieldSet;

import java.util.List;

public interface ExtractionFieldSetService extends EntityService<ExtractionFieldSet> {

	List<ExtractionFieldSet> findAll();
}

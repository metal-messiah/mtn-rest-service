package com.mtn.service;

import com.mtn.model.domain.ExtractionField;

import java.util.List;

public interface ExtractionFieldService extends EntityService<ExtractionField> {

	List<ExtractionField> findAll();
}

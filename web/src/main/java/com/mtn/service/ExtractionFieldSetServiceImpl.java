package com.mtn.service;

import com.mtn.model.domain.ExtractionFieldSet;
import com.mtn.repository.ExtractionFieldSetRepository;
import com.mtn.validators.ExtractionFieldSetValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;

import static com.mtn.repository.specification.ExtractionFieldSetSpecifications.*;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Created by Tyler on 2/14/2018.
 */
@Service
public class ExtractionFieldSetServiceImpl extends EntityServiceImpl<ExtractionFieldSet> implements ExtractionFieldSetService {

	@Autowired
	private ExtractionFieldSetRepository extractionFieldSetRepository;
	@Autowired
	private ExtractionFieldSetValidator extractionFieldSetValidator;

	@Override
	public void handleAssociationsOnCreation(ExtractionFieldSet request) {

	}

	@Override
	public void handleAssociationsOnDeletion(ExtractionFieldSet existing) {

	}

	@Override
	public List<ExtractionFieldSet> findAll() {
		return getEntityRepository().findAll(where(isNotDeleted()));
	}

	@Override
	public Page<ExtractionFieldSet> findAllUsingSpecs(Pageable page) {
		return getEntityRepository().findAll(
				where(isNotDeleted()),
				page
		);
	}

	@Override
	public ExtractionFieldSet findOneUsingSpecs(Integer id) {
		return getEntityRepository().findOne(
				where(idEquals(id))
						.and(isNotDeleted())
		);
	}

	@Override
	public ExtractionFieldSet updateEntityAttributes(ExtractionFieldSet existing, ExtractionFieldSet request) {
		existing.setFieldSetName(request.getFieldSetName());

		return existing;
	}

	@Override
	public String getEntityName() {
		return "ExtractionFieldSet";
	}

	@Override
	public ExtractionFieldSetRepository getEntityRepository() {
		return extractionFieldSetRepository;
	}

	@Override
	public ExtractionFieldSetValidator getValidator() {
		return extractionFieldSetValidator;
	}
}

package com.mtn.service;

import com.mtn.model.domain.ExtractionField;
import com.mtn.repository.ExtractionFieldRepository;
import com.mtn.validators.ExtractionFieldValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mtn.repository.specification.ExtractionFieldSpecifications.*;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * Created by Tyler on 2/14/2018.
 */
@Service
public class ExtractionFieldServiceImpl extends EntityServiceImpl<ExtractionField> implements ExtractionFieldService {

	@Autowired
	private ExtractionFieldRepository extractionFieldRepository;
	@Autowired
	private ExtractionFieldValidator extractionFieldValidator;

	@Override
	public void handleAssociationsOnCreation(ExtractionField request) {

	}

	@Override
	public void handleAssociationsOnDeletion(ExtractionField existing) {

	}

	@Override
	public List<ExtractionField> findAll() {
		return getEntityRepository().findAll(where(isNotDeleted()));
	}

	@Override
	public Page<ExtractionField> findAllUsingSpecs(Pageable page) {
		return getEntityRepository().findAll(
				where(isNotDeleted()),
				page
		);
	}

	@Override
	public ExtractionField findOneUsingSpecs(Integer id) {
		return getEntityRepository().findOne(
				where(idEquals(id))
						.and(isNotDeleted())
		);
	}

	@Override
	public ExtractionField updateEntityAttributes(ExtractionField existing, ExtractionField request) {
		existing.setExtractionDataType(request.getExtractionDataType());
		existing.setFieldMapping(request.getFieldMapping());
		existing.setHeader(request.getHeader());

		return existing;
	}

	@Override
	public String getEntityName() {
		return "ExtractionField";
	}

	@Override
	public ExtractionFieldRepository getEntityRepository() {
		return extractionFieldRepository;
	}

	@Override
	public ExtractionFieldValidator getValidator() {
		return extractionFieldValidator;
	}
}

package com.mtn.service;

import com.mtn.model.domain.ExtractionField;
import com.mtn.model.view.ExtractionFieldView;
import com.mtn.repository.ExtractionFieldRepository;
import com.mtn.validators.ExtractionFieldValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExtractionFieldService extends EntityServiceImpl<ExtractionField, ExtractionFieldView> {

	@Autowired
	public ExtractionFieldService(EntityServiceDependencies services,
								  ExtractionFieldRepository repository,
								  ExtractionFieldValidator validator) {
		super(services, repository, validator);
	}

	@Override
	protected void setEntityAttributesFromRequest(ExtractionField entity, ExtractionFieldView request) {
		entity.setDisplayName(request.getDisplayName());
		entity.setFieldMapping(request.getFieldMapping());
		entity.setHeader(request.getHeader());
		entity.setExtractionDataType(request.getExtractionDataType());
	}

	@Override
	public void handleAssociationsOnDeletion(ExtractionField existing) {
		existing.getFieldSets().forEach(fieldSet -> {
			fieldSet.getFields().remove(existing);
		});
		existing.getFieldSets().clear();
	}

	@Override
	protected ExtractionField createNewEntity() {
		return new ExtractionField();
	}

}

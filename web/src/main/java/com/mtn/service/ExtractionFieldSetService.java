package com.mtn.service;

import com.mtn.model.domain.ExtractionFieldSet;
import com.mtn.model.view.ExtractionFieldSetView;
import com.mtn.repository.ExtractionFieldSetRepository;
import com.mtn.validators.ExtractionFieldSetValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExtractionFieldSetService extends EntityServiceImpl<ExtractionFieldSet, ExtractionFieldSetView> {

	@Autowired
	public ExtractionFieldSetService(EntityServiceDependencies services,
									 ExtractionFieldSetRepository repository,
									 ExtractionFieldSetValidator validator) {
		super(services, repository, validator);
	}

	@Override
	protected ExtractionFieldSet createNewEntity() {
		return new ExtractionFieldSet();
	}

	@Override
	protected void setEntityAttributesFromRequest(ExtractionFieldSet entity, ExtractionFieldSetView request) {
		entity.setFieldSetName(request.getFieldSetName());
	}

	@Override
	public void handleAssociationsOnDeletion(ExtractionFieldSet existing) {
		existing.getFields().forEach(field -> field.getFieldSets().remove(existing));
	}
}

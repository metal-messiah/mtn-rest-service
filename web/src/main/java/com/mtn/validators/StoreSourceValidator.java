package com.mtn.validators;

import com.mtn.model.domain.StoreSource;
import com.mtn.model.view.StoreSourceView;
import com.mtn.repository.StoreSourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreSourceValidator extends EntityValidator<StoreSource, StoreSourceView> {

	@Autowired
	public StoreSourceValidator(StoreSourceRepository repository) {
		super(repository);
	}

	@Override
	protected void validateUpdateInsertBusinessRules(StoreSourceView request) {
		super.validateForInsert(request);

		if (request.getSourceName() == null) {
			throw new IllegalStateException("StoreSource must have a Source Name (ex. Planned Grocery)!");
		}
		if (request.getSourceNativeId() == null) {
			throw new IllegalStateException("StoreSource must have a Source Native Id!");
		}
	}
}

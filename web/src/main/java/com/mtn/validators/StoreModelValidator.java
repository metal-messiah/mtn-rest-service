package com.mtn.validators;

import com.mtn.model.domain.StoreModel;
import com.mtn.model.view.StoreModelView;
import com.mtn.repository.StoreModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreModelValidator extends EntityValidator<StoreModel, StoreModelView> {

	@Autowired
	public StoreModelValidator(StoreModelRepository repository) {
		super(repository);
	}


}

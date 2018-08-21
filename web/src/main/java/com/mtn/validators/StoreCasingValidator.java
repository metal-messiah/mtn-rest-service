package com.mtn.validators;

import com.mtn.model.domain.StoreCasing;
import com.mtn.model.view.StoreCasingView;
import com.mtn.repository.StoreCasingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreCasingValidator extends EntityValidator<StoreCasing, StoreCasingView> {

	@Autowired
	public StoreCasingValidator(StoreCasingRepository repository) {
		super(repository);
	}

}

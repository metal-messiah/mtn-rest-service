package com.mtn.validators;

import com.mtn.model.domain.ShoppingCenterCasing;
import com.mtn.model.view.ShoppingCenterCasingView;
import com.mtn.repository.ShoppingCenterCasingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCenterCasingValidator extends EntityValidator<ShoppingCenterCasing, ShoppingCenterCasingView> {

	@Autowired
	public ShoppingCenterCasingValidator(ShoppingCenterCasingRepository repository) {
		super(repository);
	}

}

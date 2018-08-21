package com.mtn.validators;

import com.mtn.model.domain.ShoppingCenter;
import com.mtn.model.view.ShoppingCenterView;
import com.mtn.repository.ShoppingCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCenterValidator extends EntityValidator<ShoppingCenter, ShoppingCenterView> {

	@Autowired
	public ShoppingCenterValidator(ShoppingCenterRepository repository) {
		super(repository);
	}

}

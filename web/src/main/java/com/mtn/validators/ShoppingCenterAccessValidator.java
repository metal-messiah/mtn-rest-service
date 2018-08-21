package com.mtn.validators;

import com.mtn.model.domain.ShoppingCenterAccess;
import com.mtn.model.view.ShoppingCenterAccessView;
import com.mtn.repository.ShoppingCenterAccessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCenterAccessValidator extends EntityValidator<ShoppingCenterAccess, ShoppingCenterAccessView> {

	@Autowired
	public ShoppingCenterAccessValidator(ShoppingCenterAccessRepository repository) {
		super(repository);
	}

}

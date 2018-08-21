package com.mtn.validators;

import com.mtn.model.domain.ShoppingCenterTenant;
import com.mtn.model.view.ShoppingCenterTenantView;
import com.mtn.repository.ShoppingCenterTenantRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCenterTenantValidator extends EntityValidator<ShoppingCenterTenant, ShoppingCenterTenantView> {

	@Autowired
	public ShoppingCenterTenantValidator(ShoppingCenterTenantRepository repository) {
		super(repository);
	}

	@Override
	protected void validateUpdateInsertBusinessRules(ShoppingCenterTenantView request) {
		if (StringUtils.isBlank(request.getName())) {
			throw new IllegalArgumentException("ShoppingCenterTenant name must not be null");
		}	}

}

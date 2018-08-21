package com.mtn.validators;

import com.mtn.model.domain.ClientAccessKey;
import com.mtn.model.view.ClientAccessKeyView;
import com.mtn.repository.ClientAccessKeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientAccessKeyValidator extends EntityValidator<ClientAccessKey, ClientAccessKeyView> {

	@Autowired
	public ClientAccessKeyValidator(ClientAccessKeyRepository repository) {
		super(repository);
	}
}

package com.mtn.validators;

import com.mtn.model.domain.ClientAccessKey;
import com.mtn.service.ClientAccessKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientAccessKeyValidator extends ValidatingDataService<ClientAccessKey> {

	@Autowired
	private ClientAccessKeyService clientAccessKeyService;

	@Override
	public ClientAccessKeyService getEntityService() {
		return clientAccessKeyService;
	}

}

package com.mtn.service;

import com.mtn.model.domain.ClientAccessKey;

public interface ClientAccessKeyService extends EntityService<ClientAccessKey> {

	ClientAccessKey validate(String accessKey, String clientUniqueIdentifier);

}

package com.mtn.service;

import com.mtn.model.domain.ClientAccessKey;
import com.mtn.model.view.ClientAccessKeyView;
import com.mtn.repository.ClientAccessKeyRepository;
import com.mtn.repository.specification.ClientAccessKeySpecifications;
import com.mtn.validators.ClientAccessKeyValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import java.time.LocalDateTime;

@Service
public class ClientAccessKeyService extends EntityService<ClientAccessKey, ClientAccessKeyView> {

	@Autowired
	public ClientAccessKeyService(SecurityService securityService,
								  ClientAccessKeyRepository repository,
								  ClientAccessKeyValidator validator) {
		super(securityService, repository, validator, ClientAccessKey::new);
	}

	@Transactional
	public ClientAccessKey validateKey(String accessKey, String clientUniqueIdentifier) {
		ClientAccessKey key = this.repository.findOne(
				Specifications.where(ClientAccessKeySpecifications.accessKeyEquals(accessKey))
				.and(ClientAccessKeySpecifications.isNotDeleted()));
		if (key == null) {
			throw new EntityNotFoundException(String.format("Access key '%s' does not exist", accessKey));
		}
		if (!key.getActive()) {
			throw new IllegalArgumentException("Key is no longer active");
		}
		if (key.getClientUniqueIdentifier() != null) {
			if (key.getClientUniqueIdentifier().equals(clientUniqueIdentifier)) {
				key.setLastVerified(LocalDateTime.now());
				key.setUpdatedBy(securityService.getCurrentUser());
				return key;
			} else {
				throw new IllegalArgumentException("Key is registered to a different device");
			}
		} else {
			key.setClientUniqueIdentifier(clientUniqueIdentifier);
			key.setLastVerified(LocalDateTime.now());
			key.setUpdatedBy(securityService.getCurrentUser());
			return key;
		}
	}

	@Override
	public void handleAssociationsOnDeletion(ClientAccessKey existing) {
		// No Associations
	}

	@Override
	protected void setEntityAttributesFromRequest(ClientAccessKey key, ClientAccessKeyView request) {
		key.setClientName(request.getClientName());
		key.setAccessKey(request.getAccessKey());
		key.setActive(request.getActive());
		key.setClientUniqueIdentifier(request.getClientUniqueIdentifier());
		key.setLastVerified(request.getLastVerified());
	}
}

package com.mtn.service;

import com.mtn.model.domain.ClientAccessKey;
import com.mtn.repository.ClientAccessKeyRepository;
import com.mtn.repository.specification.AuditingEntitySpecifications;
import com.mtn.repository.specification.ClientAccessKeySpecifications;
import com.mtn.validators.ClientAccessKeyValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import java.time.LocalDateTime;

/**
 * Created by Tyler on 7/3/2018.
 */
@Service
public class ClientAccessKeyServiceImpl extends EntityServiceImpl<ClientAccessKey> implements ClientAccessKeyService {

	@Autowired
	private ClientAccessKeyRepository clientAccessKeyRepository;
	@Autowired
	private ClientAccessKeyValidator clientAccessKeyValidator;

	@Override
	public ClientAccessKey updateEntityAttributes(ClientAccessKey existing, ClientAccessKey request) {
		existing.setAccessKey(request.getAccessKey());
		existing.setActive(request.getActive());
		existing.setClientUniqueIdentifier(request.getClientUniqueIdentifier());
		existing.setLastVerified(request.getLastVerified());

		return existing;
	}

	@Override
	public String getEntityName() {
		return "ClientAccessKey";
	}

	@Override
	public ClientAccessKeyValidator getValidator() {
		return clientAccessKeyValidator;
	}

	@Override
	@Transactional
	public ClientAccessKey validate(String accessKey, String clientUniqueIdentifier) {
		ClientAccessKey key = clientAccessKeyRepository.findOne(
				Specifications.where(ClientAccessKeySpecifications.accessKeyEquals(accessKey))
				.and(AuditingEntitySpecifications.isNotDeleted()));
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

}

package com.mtn.service;

import com.mtn.model.domain.ClientAccessKey;
import com.mtn.repository.ClientAccessKeyRepository;
import com.mtn.validators.ClientAccessKeyValidator;
import netscape.security.ForbiddenTargetException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import java.time.LocalDateTime;

import static com.mtn.repository.specification.ClientAccessKeySpecifications.*;
import static org.springframework.data.jpa.domain.Specifications.where;

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
	public void handleAssociationsOnCreation(ClientAccessKey request) {

	}

	@Override
	public void handleAssociationsOnDeletion(ClientAccessKey existing) {

	}

	@Override
	public Page<ClientAccessKey> findAllUsingSpecs(Pageable page) {
		return getEntityRepository().findAll(
				where(isNotDeleted()),
				page
		);
	}

	@Override
	public ClientAccessKey findOneUsingSpecs(Integer id) {
		return getEntityRepository().findOne(
				where(idEquals(id))
						.and(isNotDeleted())
		);
	}

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
	public ClientAccessKeyRepository getEntityRepository() {
		return clientAccessKeyRepository;
	}

	@Override
	public ClientAccessKeyValidator getValidator() {
		return clientAccessKeyValidator;
	}

	@Override
	@Transactional
	public ClientAccessKey validate(String accessKey, String clientUniqueIdentifier) {
		ClientAccessKey key = getEntityRepository().findOne(where(accessKeyEquals(accessKey))
				.and(isNotDeleted()));
		if (key == null) {
			throw new EntityNotFoundException(String.format("Access key '%s' does not exist", accessKey));
		}
		if (!key.getActive()) {
			throw new ForbiddenTargetException("Key is no longer active");
		}
		if (key.getClientUniqueIdentifier() != null) {
			if (key.getClientUniqueIdentifier().equals(clientUniqueIdentifier)) {
				key.setLastVerified(LocalDateTime.now());
				key.setVersion(key.getVersion() + 1);
				key.setUpdatedBy(securityService.getCurrentUser());
				return key;
			} else {
				throw new ForbiddenTargetException("Key is registered to a different device");
			}
		} else {
			key.setClientUniqueIdentifier(clientUniqueIdentifier);
			key.setLastVerified(LocalDateTime.now());
			key.setVersion(key.getVersion() + 1);
			key.setUpdatedBy(securityService.getCurrentUser());
			return key;
		}
	}

}

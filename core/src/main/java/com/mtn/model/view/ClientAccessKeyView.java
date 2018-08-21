package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.ClientAccessKey;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by Tyler on 2/14/2018.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientAccessKeyView extends AuditingEntityView {

	private String clientName;
	private String accessKey;
	private String clientUniqueIdentifier;
	private Boolean active;
	private LocalDateTime lastVerified;

	public ClientAccessKeyView(ClientAccessKey clientAccessKey) {
		super(clientAccessKey);

		this.clientName = clientAccessKey.getClientName();
		this.accessKey = clientAccessKey.getAccessKey();
		this.clientUniqueIdentifier = clientAccessKey.getClientUniqueIdentifier();
		this.active = clientAccessKey.getActive();
		this.lastVerified = clientAccessKey.getLastVerified();
	}

	public String getClientName() {
		return clientName;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public String getClientUniqueIdentifier() {
		return clientUniqueIdentifier;
	}

	public Boolean getActive() {
		return active;
	}

	public LocalDateTime getLastVerified() {
		return lastVerified;
	}

}

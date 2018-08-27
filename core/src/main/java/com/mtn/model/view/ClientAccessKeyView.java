package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.ClientAccessKey;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientAccessKeyView extends AuditingEntityView {

	private String clientName;
	private String accessKey;
	private String clientUniqueIdentifier;
	private Boolean active;
	private LocalDateTime lastVerified;

	public ClientAccessKeyView() {
	}

	public ClientAccessKeyView(ClientAccessKey clientAccessKey) {
		super(clientAccessKey);

		this.clientName = clientAccessKey.getClientName();
		this.accessKey = clientAccessKey.getAccessKey();
		this.clientUniqueIdentifier = clientAccessKey.getClientUniqueIdentifier();
		this.active = clientAccessKey.getActive();
		this.lastVerified = clientAccessKey.getLastVerified();
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public void setClientUniqueIdentifier(String clientUniqueIdentifier) {
		this.clientUniqueIdentifier = clientUniqueIdentifier;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public void setLastVerified(LocalDateTime lastVerified) {
		this.lastVerified = lastVerified;
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

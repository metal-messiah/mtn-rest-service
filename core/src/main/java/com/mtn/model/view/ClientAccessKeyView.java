package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.ClientAccessKey;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by Tyler on 2/14/2018.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientAccessKeyView extends AuditingEntityView implements Serializable {

	private Integer id;
	private String clientName;
	private String accessKey;
	private String clientUniqueIdentifier;
	private Boolean active;
	private LocalDateTime lastVerified;

	public ClientAccessKeyView(ClientAccessKey clientAccessKey) {
		super(clientAccessKey);

		this.id = clientAccessKey.getId();
		this.clientName = clientAccessKey.getClientName();
		this.accessKey = clientAccessKey.getAccessKey();
		this.clientUniqueIdentifier = clientAccessKey.getClientUniqueIdentifier();
		this.active = clientAccessKey.getActive();
		this.lastVerified = clientAccessKey.getLastVerified();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getClientUniqueIdentifier() {
		return clientUniqueIdentifier;
	}

	public void setClientUniqueIdentifier(String clientUniqueIdentifier) {
		this.clientUniqueIdentifier = clientUniqueIdentifier;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public LocalDateTime getLastVerified() {
		return lastVerified;
	}

	public void setLastVerified(LocalDateTime lastVerified) {
		this.lastVerified = lastVerified;
	}
}

package com.mtn.model.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by Tyler on 2/14/2018.
 */
@Entity
@Table
public class ClientAccessKey extends AuditingEntity implements Identifiable {

    private Integer id;
    private String accessKey;
    private String clientUniqueIdentifier;
    private Boolean active;
    private LocalDateTime lastVerified;

    @Id
    @GeneratedValue
    @Column(name = "client_access_key_id")
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
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

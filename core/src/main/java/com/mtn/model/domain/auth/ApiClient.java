package com.mtn.model.domain.auth;

import javax.persistence.*;

/**
 * Created by Allen on 5/11/2017.
 */
@Entity
@Table
public class ApiClient {

    private Integer id;
    private String name;
    private String clientId;
    private String clientSecret;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_api_access_token_id")
    @SequenceGenerator(name = "seq_api_access_token_id", sequenceName = "seq_api_access_token_id", allocationSize = 1)
    @Column(name = "api_client_id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
}

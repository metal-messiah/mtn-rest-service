package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.Company;

import java.io.Serializable;

/**
 * Created by Allen on 6/10/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleCompanyView implements Serializable {

    private Integer id;
    private String name;
    private String websiteUrl;

    public SimpleCompanyView() {
    }

    public SimpleCompanyView(Company company) {
        this.id = company.getId();
        this.name = company.getName();
        this.websiteUrl = company.getWebsiteUrl();
    }

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

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }
}

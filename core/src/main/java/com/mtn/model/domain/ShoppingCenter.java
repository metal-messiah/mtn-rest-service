package com.mtn.model.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@AttributeOverride(name="id", column=@Column(name="shopping_center_id"))
public class ShoppingCenter extends AuditingEntity {

    private String name;
    private String owner;
    private String centerType;
    private Integer legacyLocationId;

    private List<ShoppingCenterCasing> casings = new ArrayList<>();
    private List<Site> sites = new ArrayList<>();
    private List<ShoppingCenterSurvey> surveys = new ArrayList<>();

    @Column(name = "shopping_center_name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getCenterType() {
        return centerType;
    }

    public void setCenterType(String centerType) {
        this.centerType = centerType;
    }

    @OneToMany(mappedBy = "shoppingCenter")
    public List<Site> getSites() {
        return sites;
    }

    public void setSites(List<Site> sites) {
        this.sites = sites;
    }

    @OneToMany(mappedBy = "shoppingCenter")
    public List<ShoppingCenterSurvey> getSurveys() {
        return surveys;
    }

    public void setSurveys(List<ShoppingCenterSurvey> surveys) {
        this.surveys = surveys;
    }

    public Integer getLegacyLocationId() {
        return legacyLocationId;
    }

    public void setLegacyLocationId(Integer legacyLocationId) {
        this.legacyLocationId = legacyLocationId;
    }

    @OneToMany(mappedBy = "shoppingCenter")
    public List<ShoppingCenterCasing> getCasings() {
        return casings;
    }

    public void setCasings(List<ShoppingCenterCasing> casings) {
        this.casings = casings;
    }

}

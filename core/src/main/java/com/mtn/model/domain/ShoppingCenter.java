package com.mtn.model.domain;

import com.mtn.model.simpleView.SimpleShoppingCenterView;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allen on 4/23/2017.
 */
@Entity
@Table
public class ShoppingCenter extends AuditingEntity implements Identifiable {

    private Integer id;
    private String name;
    private String owner;
    private Integer legacyLocationId;

    private List<ShoppingCenterCasing> casings = new ArrayList<>();
    private List<Interaction> interactions = new ArrayList<>();
    private List<Site> sites = new ArrayList<>();
    private List<ShoppingCenterSurvey> surveys = new ArrayList<>();

    public ShoppingCenter() {
    }

    @Id
    @GeneratedValue
    @Column(name = "shopping_center_id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    @OneToMany(mappedBy = "shoppingCenter")
    public List<Interaction> getInteractions() {
        return interactions;
    }

    public void setInteractions(List<Interaction> interactions) {
        this.interactions = interactions;
    }
}

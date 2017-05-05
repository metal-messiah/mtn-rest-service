package com.mtn.model.domain;

import com.mtn.model.view.SimpleShoppingCenterView;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allen on 4/23/2017.
 */
@Entity
@Table
public class ShoppingCenter extends AuditingEntity {

    private Integer id;
    private String name;
    private String owner;
    private String nativeId;
    private String url;
    private Integer version;

    private List<Site> sites = new ArrayList<>();
    private List<ShoppingCenterSurvey> surveys = new ArrayList<>();

    public ShoppingCenter() {
    }

    public ShoppingCenter(SimpleShoppingCenterView shoppingCenterView) {
        this.id = shoppingCenterView.getId();
        this.name = shoppingCenterView.getName();
        this.owner = shoppingCenterView.getOwner();
        this.nativeId = shoppingCenterView.getNativeId();
        this.url = shoppingCenterView.getUrl();
        this.version = shoppingCenterView.getVersion();
    }

    @PrePersist
    public void prePersist() {
        version = 1;
    }

    @PreUpdate
    public void preUpdate() {
        version++;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_shopping_center_id")
    @SequenceGenerator(name = "seq_shopping_center_id", sequenceName = "seq_shopping_center_id", allocationSize = 1)
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getNativeId() {
        return nativeId;
    }

    public void setNativeId(String nativeId) {
        this.nativeId = nativeId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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
}

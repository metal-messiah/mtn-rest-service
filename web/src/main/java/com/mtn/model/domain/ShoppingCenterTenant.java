package com.mtn.model.domain;

import javax.persistence.*;

/**
 * Created by Allen on 5/4/2017.
 */
@Entity
@Table
public class ShoppingCenterTenant extends AuditingEntity implements Identifiable {

    private Integer id;
    private ShoppingCenterSurvey survey;
    private String name;
    private Boolean isAnchor = false;
    private Boolean isOutparcel = false;
    private Integer sqft;
    private Integer legacyCasingId;
    private Integer version;

    @PrePersist
    public void prePersist() {
        version = 1;
    }

    @PreUpdate
    public void preUpdate() {
        version++;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_shopping_center_tenant_id")
    @SequenceGenerator(name = "seq_shopping_center_tenant_id", sequenceName = "seq_shopping_center_tenant_id", allocationSize = 1)
    @Column(name = "shopping_center_tenant_id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "shopping_center_survey_id")
    public ShoppingCenterSurvey getSurvey() {
        return survey;
    }

    public void setSurvey(ShoppingCenterSurvey survey) {
        this.survey = survey;
    }

    @Column(name = "tenant_name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsOutparcel() {
        return isOutparcel;
    }

    public void setIsOutparcel(Boolean outparcel) {
        isOutparcel = outparcel;
    }

    public Integer getSqft() {
        return sqft;
    }

    public void setSqft(Integer sqft) {
        this.sqft = sqft;
    }

    public Boolean getIsAnchor() {
        return isAnchor;
    }

    public void setIsAnchor(Boolean anchor) {
        isAnchor = anchor;
    }

    public Integer getLegacyCasingId() {
        return legacyCasingId;
    }

    public void setLegacyCasingId(Integer legacyCasingId) {
        this.legacyCasingId = legacyCasingId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}

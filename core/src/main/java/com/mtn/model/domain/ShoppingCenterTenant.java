package com.mtn.model.domain;

import javax.persistence.*;

@Entity
@Table
@AttributeOverride(name="id", column=@Column(name="shopping_center_tenant_id"))
public class ShoppingCenterTenant extends AuditingEntity {

    private String name;
    private Boolean isAnchor = false;
    private Boolean outparcel = false;
    private Integer tenantSqft;
    private Integer legacyCasingId;

    private ShoppingCenterSurvey survey;

    public ShoppingCenterTenant() {
    }

    public ShoppingCenterTenant(ShoppingCenterTenant shoppingCenterTenant) {
        this.name = shoppingCenterTenant.name;
        this.isAnchor = shoppingCenterTenant.isAnchor;
        this.outparcel = shoppingCenterTenant.outparcel;
        this.tenantSqft = shoppingCenterTenant.tenantSqft;
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

    @Column(name = "is_outparcel")
    public Boolean getOutparcel() {
        return outparcel;
    }

    public void setOutparcel(Boolean outparcel) {
        this.outparcel = outparcel;
    }

    public Integer getTenantSqft() {
        return tenantSqft;
    }

    public void setTenantSqft(Integer tenantSqft) {
        this.tenantSqft = tenantSqft;
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

}

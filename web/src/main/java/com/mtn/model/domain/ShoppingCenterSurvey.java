package com.mtn.model.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allen on 5/4/2017.
 */
@Entity
@Table
public class ShoppingCenterSurvey extends AuditingEntity {

    private Integer id;
    private ShoppingCenter shoppingCenter;
    private Boolean hasAngledSpaces = false;
    private Boolean hasParkingHog = false;
    private Boolean hasSpeedBumps = false;

    private List<ShoppingCenterAccess> accesses = new ArrayList<>();
    private List<ShoppingCenterTenant> tenants = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_shopping_center_survey_id")
    @SequenceGenerator(name = "seq_shopping_center_survey_id", sequenceName = "seq_shopping_center_survey_id", allocationSize = 1)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "shopping_center_id")
    public ShoppingCenter getShoppingCenter() {
        return shoppingCenter;
    }

    public void setShoppingCenter(ShoppingCenter shoppingCenter) {
        this.shoppingCenter = shoppingCenter;
    }

    public Boolean getHasAngledSpaces() {
        return hasAngledSpaces;
    }

    public void setHasAngledSpaces(Boolean hasAngledSpaces) {
        this.hasAngledSpaces = hasAngledSpaces;
    }

    public Boolean getHasParkingHog() {
        return hasParkingHog;
    }

    public void setHasParkingHog(Boolean hasParkingHog) {
        this.hasParkingHog = hasParkingHog;
    }

    public Boolean getHasSpeedBumps() {
        return hasSpeedBumps;
    }

    public void setHasSpeedBumps(Boolean hasSpeedBumps) {
        this.hasSpeedBumps = hasSpeedBumps;
    }

    @OneToMany(mappedBy = "survey", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    public List<ShoppingCenterAccess> getAccesses() {
        return accesses;
    }

    public void setAccesses(List<ShoppingCenterAccess> accesses) {
        this.accesses = accesses;
    }

    @OneToMany(mappedBy = "survey", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    public List<ShoppingCenterTenant> getTenants() {
        return tenants;
    }

    public void setTenants(List<ShoppingCenterTenant> tenants) {
        this.tenants = tenants;
    }
}

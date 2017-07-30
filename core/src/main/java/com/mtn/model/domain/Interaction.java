package com.mtn.model.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
public class Interaction extends AuditingEntity implements Identifiable {

    private Integer id;
    private Project project;
    private ShoppingCenter shoppingCenter;
    private ShoppingCenterSurvey shoppingCenterSurvey;
    private ShoppingCenterCasing shoppingCenterCasing;
    private Store store;
    private StoreSurvey storeSurvey;
    private StoreCasing storeCasing;
    private LocalDateTime interactionDate;
    private Integer version;
    private Integer legacyCasingId;

    @PrePersist
    public void prePersist() {
        version = 1;
    }

    @PreUpdate
    public void preUpdate() {
        version++;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_interaction_id")
    @SequenceGenerator(name = "seq_interaction_id", sequenceName = "seq_interaction_id", allocationSize = 1)
    @Column(name = "interaction_id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "project_id")
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @ManyToOne
    @JoinColumn(name = "shopping_center_id")
    public ShoppingCenter getShoppingCenter() {
        return shoppingCenter;
    }

    public void setShoppingCenter(ShoppingCenter shoppingCenter) {
        this.shoppingCenter = shoppingCenter;
    }

    @ManyToOne
    @JoinColumn(name = "shopping_center_survey_id")
    public ShoppingCenterSurvey getShoppingCenterSurvey() {
        return shoppingCenterSurvey;
    }

    public void setShoppingCenterSurvey(ShoppingCenterSurvey shoppingCenterSurvey) {
        this.shoppingCenterSurvey = shoppingCenterSurvey;
    }

    @ManyToOne
    @JoinColumn(name = "shopping_center_casing_id")
    public ShoppingCenterCasing getShoppingCenterCasing() {
        return shoppingCenterCasing;
    }

    public void setShoppingCenterCasing(ShoppingCenterCasing shoppingCenterCasing) {
        this.shoppingCenterCasing = shoppingCenterCasing;
    }

    @ManyToOne
    @JoinColumn(name = "store_id")
    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    @ManyToOne
    @JoinColumn(name = "store_survey_id")
    public StoreSurvey getStoreSurvey() {
        return storeSurvey;
    }

    public void setStoreSurvey(StoreSurvey storeSurvey) {
        this.storeSurvey = storeSurvey;
    }

    @ManyToOne
    @JoinColumn(name = "store_casing_id")
    public StoreCasing getStoreCasing() {
        return storeCasing;
    }

    public void setStoreCasing(StoreCasing storeCasing) {
        this.storeCasing = storeCasing;
    }

    public LocalDateTime getInteractionDate() {
        return interactionDate;
    }

    public void setInteractionDate(LocalDateTime interactionDate) {
        this.interactionDate = interactionDate;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getLegacyCasingId() {
        return legacyCasingId;
    }

    public void setLegacyCasingId(Integer legacyCasingId) {
        this.legacyCasingId = legacyCasingId;
    }
}

package com.mtn.model.domain;

import com.mtn.constant.RatingType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class ShoppingCenterCasing extends AuditingEntity implements Identifiable {

    private Integer id;
    private LocalDateTime casingDate;
    private String note;
    private RatingType ratingParkingLot;
    private RatingType ratingBuildings;
    private RatingType ratingLighting;
    private RatingType ratingSynergy;
    private Integer legacyCasingId;

    private ShoppingCenter shoppingCenter;
    private ShoppingCenterSurvey shoppingCenterSurvey;
    private List<Project> projects = new ArrayList<>();
    private List<StoreCasing> storeCasings = new ArrayList();

    @Id
    @GeneratedValue
    @Column(name = "shopping_center_casing_id")
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) { this.id = id; }

    @ManyToOne
    @JoinColumn(name = "shopping_center_id")
    public ShoppingCenter getShoppingCenter() {
        return shoppingCenter;
    }

    public void setShoppingCenter(ShoppingCenter shoppingCenter) {
        this.shoppingCenter = shoppingCenter;
    }

    @Column(name = "shopping_center_casing_date")
    public LocalDateTime getCasingDate() {
        return casingDate;
    }

    public void setCasingDate(LocalDateTime casingDate) {
        this.casingDate = casingDate;
    }

    @Column(name = "shopping_center_casing_note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Enumerated(EnumType.STRING)
    public RatingType getRatingParkingLot() {
        return ratingParkingLot;
    }

    public void setRatingParkingLot(RatingType ratingParkingLot) {
        this.ratingParkingLot = ratingParkingLot;
    }

    @Enumerated(EnumType.STRING)
    public RatingType getRatingBuildings() {
        return ratingBuildings;
    }

    public void setRatingBuildings(RatingType ratingBuildings) {
        this.ratingBuildings = ratingBuildings;
    }

    @Enumerated(EnumType.STRING)
    public RatingType getRatingLighting() {
        return ratingLighting;
    }

    public void setRatingLighting(RatingType ratingLighting) {
        this.ratingLighting = ratingLighting;
    }

    @Enumerated(EnumType.STRING)
    public RatingType getRatingSynergy() {
        return ratingSynergy;
    }

    public void setRatingSynergy(RatingType ratingSynergy) {
        this.ratingSynergy = ratingSynergy;
    }

    public Integer getLegacyCasingId() {
        return legacyCasingId;
    }

    public void setLegacyCasingId(Integer legacyCasingId) {
        this.legacyCasingId = legacyCasingId;
    }

    @ManyToOne
    @JoinColumn(name = "shopping_center_survey_id")
    public ShoppingCenterSurvey getShoppingCenterSurvey() {
        return shoppingCenterSurvey;
    }

    public void setShoppingCenterSurvey(ShoppingCenterSurvey shoppingCenterSurvey) {
        this.shoppingCenterSurvey = shoppingCenterSurvey;
    }

    @ManyToMany
    @JoinTable(
            name = "shopping_center_casing_project",
            joinColumns = @JoinColumn(name = "shopping_center_casing_id", referencedColumnName = "shopping_center_casing_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id", referencedColumnName = "project_id")
    )
    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    @OneToMany(mappedBy = "shoppingCenterCasing")
    public List<StoreCasing> getStoreCasings() {
        return storeCasings;
    }

    public void setStoreCasings(List<StoreCasing> storeCasings) {
        this.storeCasings = storeCasings;
    }
}

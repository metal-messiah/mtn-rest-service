package com.mtn.model.domain;

import com.mtn.constant.RatingType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class ShoppingCenterCasing extends AuditingEntity implements Identifiable {

    private Integer id;
    private ShoppingCenter shoppingCenter;
    private String note;
    private RatingType ratingParkingLot;
    private RatingType ratingBuildings;
    private RatingType ratingLighting;
    private RatingType ratingSynergy;
    private Integer legacyCasingId;

    private List<Interaction> interactions = new ArrayList<>();

    @Id
    @GeneratedValue
    @Column(name = "shopping_center_casing_id")
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

    @OneToMany(mappedBy = "shoppingCenterCasing")
    public List<Interaction> getInteractions() {
        return interactions;
    }

    public void setInteractions(List<Interaction> interactions) {
        this.interactions = interactions;
    }
}

package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.constant.RatingType;
import com.mtn.model.domain.ShoppingCenterCasing;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleShoppingCenterCasingView {

    private Integer id;
    private LocalDateTime casingDate;
    private String note;
    private RatingType ratingParkingLot;
    private RatingType ratingBuildings;
    private RatingType ratingLighting;
    private RatingType ratingSynergy;
    private Integer legacyCasingId;

    public SimpleShoppingCenterCasingView(ShoppingCenterCasing casing) {
        this.id = casing.getId();
        this.casingDate = casing.getCasingDate();
        this.note = casing.getNote();
        this.ratingBuildings = casing.getRatingBuildings();
        this.ratingParkingLot = casing.getRatingParkingLot();
        this.ratingLighting = casing.getRatingLighting();
        this.ratingSynergy = casing.getRatingSynergy();
        this.legacyCasingId = casing.getLegacyCasingId();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getCasingDate() {
        return casingDate;
    }

    public void setCasingDate(LocalDateTime casingDate) {
        this.casingDate = casingDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public RatingType getRatingParkingLot() {
        return ratingParkingLot;
    }

    public void setRatingParkingLot(RatingType ratingParkingLot) {
        this.ratingParkingLot = ratingParkingLot;
    }

    public RatingType getRatingBuildings() {
        return ratingBuildings;
    }

    public void setRatingBuildings(RatingType ratingBuildings) {
        this.ratingBuildings = ratingBuildings;
    }

    public RatingType getRatingLighting() {
        return ratingLighting;
    }

    public void setRatingLighting(RatingType ratingLighting) {
        this.ratingLighting = ratingLighting;
    }

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

}

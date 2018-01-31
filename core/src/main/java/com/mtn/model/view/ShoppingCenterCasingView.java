package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.constant.RatingType;
import com.mtn.model.domain.ShoppingCenterCasing;
import com.mtn.model.simpleView.SimpleInteractionView;

import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShoppingCenterCasingView extends AuditingEntityView {

    private Integer id;
    private String note;
    private RatingType ratingParkingLot;
    private RatingType ratingBuildings;
    private RatingType ratingLighting;
    private RatingType ratingSynergy;
    private Integer legacyCasingId;

    private List<SimpleInteractionView> interactions;

    public ShoppingCenterCasingView(ShoppingCenterCasing casing) {
        super(casing);
        this.id = casing.getId();
        this.note = casing.getNote();
        this.ratingBuildings = casing.getRatingBuildings();
        this.ratingParkingLot = casing.getRatingParkingLot();
        this.ratingLighting = casing.getRatingLighting();
        this.ratingSynergy = casing.getRatingSynergy();
        this.legacyCasingId = casing.getLegacyCasingId();
        this.interactions = casing.getInteractions().stream().filter(interaction -> interaction.getDeletedDate() == null).map(SimpleInteractionView::new).collect(Collectors.toList());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public List<SimpleInteractionView> getInteractions() {
        return interactions;
    }

    public void setInteractions(List<SimpleInteractionView> interactions) {
        this.interactions = interactions;
    }
}

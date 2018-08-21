package com.mtn.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.constant.RatingType;
import com.mtn.model.domain.ShoppingCenterCasing;
import com.mtn.model.simpleView.SimpleProjectView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShoppingCenterCasingView extends AuditingEntityView {

    private LocalDateTime casingDate;
    private String note;
    private RatingType ratingParkingLot;
    private RatingType ratingBuildings;
    private RatingType ratingLighting;
    private RatingType ratingSynergy;
    private Integer legacyCasingId;

    private List<SimpleProjectView> projects = new ArrayList<>();
    private ShoppingCenterSurveyView shoppingCenterSurvey;

    public ShoppingCenterCasingView(ShoppingCenterCasing casing) {
        super(casing);

        this.casingDate = casing.getCasingDate();
        this.note = casing.getNote();
        this.ratingBuildings = casing.getRatingBuildings();
        this.ratingParkingLot = casing.getRatingParkingLot();
        this.ratingLighting = casing.getRatingLighting();
        this.ratingSynergy = casing.getRatingSynergy();
        this.legacyCasingId = casing.getLegacyCasingId();

        if (casing.getShoppingCenterSurvey() != null) {
            this.shoppingCenterSurvey = new ShoppingCenterSurveyView(casing.getShoppingCenterSurvey());
        }
        if(casing.getProjects() != null) {
            this.projects = casing.getProjects().stream()
                    .filter(project -> project.getDeletedDate() == null)
                    .map(SimpleProjectView::new)
                    .collect(Collectors.toList());
        }
    }

    public LocalDateTime getCasingDate() {
        return casingDate;
    }

    public String getNote() {
        return note;
    }

    public RatingType getRatingParkingLot() {
        return ratingParkingLot;
    }

    public RatingType getRatingBuildings() {
        return ratingBuildings;
    }

    public RatingType getRatingLighting() {
        return ratingLighting;
    }

    public RatingType getRatingSynergy() {
        return ratingSynergy;
    }

    public Integer getLegacyCasingId() {
        return legacyCasingId;
    }

    public ShoppingCenterSurveyView getShoppingCenterSurvey() {
        return shoppingCenterSurvey;
    }

    public List<SimpleProjectView> getProjects() {
        return projects;
    }

}

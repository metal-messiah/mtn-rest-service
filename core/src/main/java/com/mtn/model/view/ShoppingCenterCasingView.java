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
    private RatingType ratingBuildings;
    private RatingType ratingLighting;
    private RatingType ratingSynergy;
    private RatingType ratingTenantBuildings;
    private Integer legacyCasingId;

    private List<SimpleProjectView> projects = new ArrayList<>();
    private ShoppingCenterSurveyView shoppingCenterSurvey;

    public ShoppingCenterCasingView() {
    }

    public ShoppingCenterCasingView(ShoppingCenterCasing casing) {
        super(casing);

        this.casingDate = casing.getCasingDate();
        this.note = casing.getNote();
        this.ratingBuildings = casing.getRatingBuildings();
        this.ratingLighting = casing.getRatingLighting();
        this.ratingSynergy = casing.getRatingSynergy();
        this.ratingTenantBuildings = casing.getRatingTenantBuildings();
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

    public void setCasingDate(LocalDateTime casingDate) {
        this.casingDate = casingDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public RatingType getRatingTenantBuildings() {
        return ratingTenantBuildings;
    }

    public void setRatingTenantBuildings(RatingType ratingTenantBuildings) {
        this.ratingTenantBuildings = ratingTenantBuildings;
    }

    public Integer getLegacyCasingId() {
        return legacyCasingId;
    }

    public void setLegacyCasingId(Integer legacyCasingId) {
        this.legacyCasingId = legacyCasingId;
    }

    public List<SimpleProjectView> getProjects() {
        return projects;
    }

    public void setProjects(List<SimpleProjectView> projects) {
        this.projects = projects;
    }

    public ShoppingCenterSurveyView getShoppingCenterSurvey() {
        return shoppingCenterSurvey;
    }

    public void setShoppingCenterSurvey(ShoppingCenterSurveyView shoppingCenterSurvey) {
        this.shoppingCenterSurvey = shoppingCenterSurvey;
    }
}

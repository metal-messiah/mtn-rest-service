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

    public SimpleShoppingCenterCasingView(ShoppingCenterCasing casing) {
        this.id = casing.getId();
        this.casingDate = casing.getCasingDate();
        this.note = casing.getNote();
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

}

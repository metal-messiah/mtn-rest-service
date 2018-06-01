package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.StoreStatus;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleStoreStatusView {

    private Integer id;
    private String status;
    private LocalDate statusStartDate;

    public SimpleStoreStatusView(StoreStatus storeStatus) {
        this.id = storeStatus.getId();
        this.status = storeStatus.getStatus();
        this.statusStartDate = storeStatus.getStatusStartDate();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getStatusStartDate() {
        return statusStartDate;
    }

    public void setStatusStartDate(LocalDate statusStartDate) {
        this.statusStartDate = statusStartDate;
    }
}

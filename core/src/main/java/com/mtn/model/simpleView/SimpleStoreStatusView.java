package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.StoreStatus;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SimpleStoreStatusView {

    private Integer id;
    private String status;
    private LocalDateTime statusStartDate;

    public SimpleStoreStatusView() {

    }

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

    public LocalDateTime getStatusStartDate() {
        return statusStartDate;
    }

    public void setStatusStartDate(LocalDateTime statusStartDate) {
        this.statusStartDate = statusStartDate;
    }
}

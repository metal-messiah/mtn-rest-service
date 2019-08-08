package com.mtn.model.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table
public class StoreBestVolume {

    @Id
    private Integer storeId;
    private String decision;
    private Integer volumeTotal;
    private LocalDate volumeDate;

    public Integer getStoreId() {
        return storeId;
    }

    public String getDecision() {
        return decision;
    }

    public Integer getVolumeTotal() {
        return volumeTotal;
    }

    public LocalDate getVolumeDate() {
        return volumeDate;
    }
}

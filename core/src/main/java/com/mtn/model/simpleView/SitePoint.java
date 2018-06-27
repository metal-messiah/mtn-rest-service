package com.mtn.model.simpleView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.model.domain.Site;

/**
 * Created by Allen on 4/24/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SitePoint {

    private Integer id;
    private Float lat;
    private Float lng;

    public SitePoint(Site site) {
        this.id = site.getId();
        this.lat = site.getLatitude();
        this.lng = site.getLongitude();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLng() {
        return lng;
    }

    public void setLng(Float lng) {
        this.lng = lng;
    }
}

package com.mtn.model.view.geojson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.constant.GeometryType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allen on 4/29/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PolygonGeometry extends GeometryView {

    private List<Double[]> coordinates = new ArrayList<>();

    public PolygonGeometry() {
        this.type = GeometryType.Point;
    }

    public List<Double[]> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Double[]> coordinates) {
        this.coordinates = coordinates;
    }
}

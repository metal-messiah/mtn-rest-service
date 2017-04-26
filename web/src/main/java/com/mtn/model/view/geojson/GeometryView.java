package com.mtn.model.view.geojson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.constant.GeometryType;
import com.vividsolutions.jts.geom.Point;

/**
 * Created by Allen on 4/24/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeometryView {

    private GeometryType type;
    private Double[] coordinates = new Double[2];

    public GeometryView() {
    }

    public GeometryView(Point location) {
        this.type = GeometryType.Point;
        this.coordinates[0] = location.getCoordinate().getOrdinate(0);
        this.coordinates[1] = location.getCoordinate().getOrdinate(1);
    }

    public GeometryType getType() {
        return type;
    }

    public void setType(GeometryType type) {
        this.type = type;
    }

    public Double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Double[] coordinates) {
        this.coordinates = coordinates;
    }
}

package com.mtn.model.view.geojson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.constant.GeometryType;
import com.vividsolutions.jts.geom.Point;

/**
 * Created by Allen on 4/29/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PointGeometry extends GeometryView {

    private Double[] coordinates = new Double[2];

    public PointGeometry() {
        this.type = GeometryType.Point;
    }

    public PointGeometry(Point location) {
        this();
        this.coordinates[0] = location.getCoordinate().getOrdinate(0);
        this.coordinates[1] = location.getCoordinate().getOrdinate(1);
    }

    public Double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Double[] coordinates) {
        this.coordinates = coordinates;
    }
}

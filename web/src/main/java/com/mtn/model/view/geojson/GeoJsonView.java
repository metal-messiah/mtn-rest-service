package com.mtn.model.view.geojson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mtn.constant.GeoJsonType;
import com.vividsolutions.jts.geom.Point;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Allen on 4/24/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoJsonView {

    private GeoJsonType type;
    private Map<String, Object> properties = new HashMap<>();
    private GeometryView geometry;

    public GeoJsonView() {
    }

    public GeoJsonView(Point location) {
        this.type = GeoJsonType.Feature;
        this.geometry = new PointGeometry(location);
    }

    public GeoJsonType getType() {
        return type;
    }

    public void setType(GeoJsonType type) {
        this.type = type;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public GeometryView getGeometry() {
        return geometry;
    }

    public void setGeometry(GeometryView geometry) {
        this.geometry = geometry;
    }
}

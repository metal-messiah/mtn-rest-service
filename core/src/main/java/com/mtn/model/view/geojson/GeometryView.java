package com.mtn.model.view.geojson;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.mtn.constant.GeometryType;

/**
 * Created by Allen on 4/24/2017.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = PointGeometry.class, name = "Point"),
        @JsonSubTypes.Type(value = PolygonGeometry.class, name = "Polygon")
})
public abstract class GeometryView {

    protected GeometryType type;

    public GeometryType getType() {
        return type;
    }

    public void setType(GeometryType type) {
        this.type = type;
    }
}

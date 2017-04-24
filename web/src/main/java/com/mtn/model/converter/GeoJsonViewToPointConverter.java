package com.mtn.model.converter;

import com.mtn.model.view.geojson.GeoJsonView;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by Allen on 4/24/2017.
 */
public class GeoJsonViewToPointConverter implements Converter<GeoJsonView, Point> {

    @Override
    public Point convert(GeoJsonView geoJsonView) {
        return GeoJsonViewToPointConverter.build(geoJsonView);
    }

    public static Point build(GeoJsonView geoJsonView) {
        return new GeometryFactory().createPoint(new Coordinate(geoJsonView.getGeometry().getCoordinates()[0], geoJsonView.getGeometry().getCoordinates()[1]));
    }
}

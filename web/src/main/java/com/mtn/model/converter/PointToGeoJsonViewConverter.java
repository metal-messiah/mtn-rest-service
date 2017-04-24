package com.mtn.model.converter;

import com.mtn.constant.GeoJsonType;
import com.mtn.constant.GeometryType;
import com.mtn.model.view.geojson.GeoJsonView;
import com.mtn.model.view.geojson.GeometryView;
import com.vividsolutions.jts.geom.Point;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by Allen on 4/24/2017.
 */
public class PointToGeoJsonViewConverter implements Converter<Point, GeoJsonView> {

    @Override
    public GeoJsonView convert(Point point) {
        return PointToGeoJsonViewConverter.build(point);
    }

    public static GeoJsonView build(Point point) {
        GeometryView geometryView = new GeometryView();
        geometryView.setType(GeometryType.Point);
        geometryView.getCoordinates()[0] = point.getCoordinate().getOrdinate(0);
        geometryView.getCoordinates()[1] = point.getCoordinate().getOrdinate(1);

        GeoJsonView viewModel = new GeoJsonView();
        viewModel.setType(GeoJsonType.Feature);
        viewModel.setGeometry(geometryView);

        return viewModel;
    }
}

package com.mtn.service;

import com.mtn.constant.GeometryType;
import com.mtn.model.domain.search.ShoppingCenterSearchResult;
import com.mtn.model.view.geojson.PointGeometry;
import com.mtn.model.view.search.SearchRequest;
import com.mtn.util.DistanceUtil;
import com.mtn.util.QueryBuilder;
import com.mtn.util.QueryUtil;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Allen on 4/29/2017.
 */
@Service
public class ShoppingCenterSearchService {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private static final String QUERY = "" +
            "SELECT " +
            "  s.id AS site_id, " +
            "  s.address_1, " +
            "  s.address_2, " +
            "  s.city, " +
            "  s.state, " +
            "  s.postal_code, " +
            "  s.county, " +
            "  s.country, " +
            "  ST_ASEWKT(s.location) as location, " +
            "  s.intersection_street_primary, " +
            "  s.intersection_street_secondary, " +
            "  sh.id AS shopping_center_id, " +
            "  sh.name, " +
            "  sh.owner " +
            "FROM shopping_center sh " +
            "  LEFT JOIN site s ON s.shopping_center_id = sh.id " +
            "WHERE";

    public List<ShoppingCenterSearchResult> search(SearchRequest searchRequest) {
        ShoppingCenterSearchQueryBuilder queryBuilder = new ShoppingCenterSearchQueryBuilder(searchRequest);
        return jdbcTemplate.query(queryBuilder.getQuery(), queryBuilder.getParams(), new RowMapper<ShoppingCenterSearchResult>() {
            @Override
            public ShoppingCenterSearchResult mapRow(ResultSet resultSet, int i) throws SQLException {
                ShoppingCenterSearchResult model = new ShoppingCenterSearchResult();
                model.setShoppingCenterId(resultSet.getInt("shopping_center_id"));
                model.setName(resultSet.getString("name"));
                model.setOwner(resultSet.getString("owner"));
                model.setSiteId(resultSet.getInt("site_id"));
                model.setAddress1(resultSet.getString("address_1"));
                model.setAddress2(resultSet.getString("address_2"));
                model.setCity(resultSet.getString("city"));
                model.setState(resultSet.getString("state"));
                model.setCounty(resultSet.getString("county"));
                model.setCountry(resultSet.getString("country"));
                model.setIntersectionStreetPrimary(resultSet.getString("intersection_street_primary"));
                model.setIntersectionStreetSecondary(resultSet.getString("intersection_street_secondary"));

                Point point = null;
                try {
                    point = (Point) new WKTReader().read(resultSet.getString("location"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                model.setLocation(point);

                return model;
            }
        });
    }

    private class ShoppingCenterSearchQueryBuilder extends QueryBuilder {

        private static final String COLUMN_SHOPPING_CENTER_DELETED_DATE = "sh.deleted_date";
        private static final String COLUMN_SHOPPING_CENTER_NAME = "sh.name";
        private static final String COLUMN_SHOPPING_CENTER_OWNER = "sh.owner";
        private static final String COLUMN_SITE_CITY = "s.city";
        private static final String COLUMN_SITE_COUNTY = "s.county";
        private static final String COLUMN_SITE_DELETED_DATE = "s.deleted_date";
        private static final String COLUMN_SITE_LOCATION = "s.location";
        private static final String COLUMN_SITE_POSTAL_CODE = "s.postal_code";
        private static final String COLUMN_SITE_STATE = "s.state";

        private static final String PARAM_CITY = "city";
        private static final String PARAM_COUNTY = "county";
        private static final String PARAM_DISTANCE = "distance";
        private static final String PARAM_NAME = "name";
        private static final String PARAM_OWNER = "owner";
        private static final String PARAM_POLYGON = "polygon";
        private static final String PARAM_POSTAL_CODE = "postalCode";
        private static final String PARAM_STATE = "state";
        private static final String PARAM_X_COORDINATE = "xCoordinate";
        private static final String PARAM_Y_COORDINATE = "yCoordinate";

        public ShoppingCenterSearchQueryBuilder(SearchRequest searchRequest) {
            super(QUERY, COLUMN_SHOPPING_CENTER_NAME);

            if (searchRequest.isGeometryOfType(GeometryType.Point) && searchRequest.getDistance() != null) {
                clauses.add(QueryUtil.withinDistanceClause(COLUMN_SITE_LOCATION, PARAM_X_COORDINATE, PARAM_Y_COORDINATE, PARAM_DISTANCE));

                PointGeometry point = (PointGeometry) searchRequest.getGeoJson().getGeometry();

                params.put(PARAM_X_COORDINATE, point.getCoordinates()[0]);
                params.put(PARAM_Y_COORDINATE, point.getCoordinates()[1]);
                params.put(PARAM_DISTANCE, DistanceUtil.convertDistanceToMeters(searchRequest.getDistance(), searchRequest.getUnit())); //Expecting km or miles
            } else if (searchRequest.isGeometryOfType(GeometryType.Polygon)) {
                clauses.add(QueryUtil.withinClause(COLUMN_SITE_LOCATION, PARAM_POLYGON));
                params.put(PARAM_POLYGON, searchRequest.getGeoJson().getGeometry());
            }

            if (StringUtils.isNotBlank(searchRequest.getName())) {
                clauses.add(QueryUtil.likeClause(COLUMN_SHOPPING_CENTER_NAME, PARAM_NAME));
                params.put(PARAM_NAME, QueryUtil.contains(searchRequest.getName()));
            }
            if (StringUtils.isNotBlank(searchRequest.getOwner())) {
                clauses.add(QueryUtil.likeClause(COLUMN_SHOPPING_CENTER_OWNER, PARAM_OWNER));
                params.put(PARAM_OWNER, QueryUtil.contains(searchRequest.getOwner()));
            }
            if (StringUtils.isNotBlank(searchRequest.getPostalCode())) {
                clauses.add(QueryUtil.equalsClause(COLUMN_SITE_POSTAL_CODE, PARAM_POSTAL_CODE));
                params.put(PARAM_POSTAL_CODE, searchRequest.getPostalCode());
            }
            if (StringUtils.isNotBlank(searchRequest.getCity())) {
                clauses.add(QueryUtil.likeClause(COLUMN_SITE_CITY, PARAM_CITY));
                params.put(PARAM_CITY, QueryUtil.contains(searchRequest.getCity()));
            }
            if (StringUtils.isNotBlank(searchRequest.getCounty())) {
                clauses.add(QueryUtil.likeClause(COLUMN_SITE_COUNTY, PARAM_COUNTY));
                params.put(PARAM_COUNTY, QueryUtil.contains(searchRequest.getCounty()));
            }
            if (StringUtils.isNotBlank(searchRequest.getState())) {
                clauses.add(QueryUtil.likeClause(COLUMN_SITE_STATE, PARAM_STATE));
                params.put(PARAM_STATE, QueryUtil.contains(searchRequest.getState()));
            }

            clauses.add(QueryUtil.isNullClause(COLUMN_SITE_DELETED_DATE));
            clauses.add(QueryUtil.isNullClause(COLUMN_SHOPPING_CENTER_DELETED_DATE));

            prepareQuery();
        }
    }
}
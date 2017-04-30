package com.mtn.service;

import com.mtn.constant.DistanceUnit;
import com.mtn.constant.GeometryType;
import com.mtn.model.domain.search.ShoppingCenterSearchResult;
import com.mtn.model.view.geojson.PointGeometry;
import com.mtn.model.view.search.SearchRequest;
import com.mtn.util.QueryUtils;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        QueryBuilder queryBuilder = new QueryBuilder(searchRequest);
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

    private Double convertDistanceToMeters(SearchRequest searchRequest) {
        Double kilometers = searchRequest.getDistance();

        //Convert miles to kilometers if necessary
        if (searchRequest.getUnit() == DistanceUnit.MILE) {
            kilometers = kilometers * 1.60934;
        }

        //Convert kilometers to meters
        return kilometers * 1000;
    }

    private class QueryBuilder {

        private StringBuilder query = new StringBuilder(QUERY);
        private Map<String, Object> params = new HashMap<>();
        List<String> clauses = new ArrayList<>();

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

        public QueryBuilder(SearchRequest searchRequest) {
            if (searchRequest.isGeometryOfType(GeometryType.Point) && searchRequest.getDistance() != null) {
                clauses.add(QueryUtils.withinDistanceClause(COLUMN_SITE_LOCATION, PARAM_X_COORDINATE, PARAM_Y_COORDINATE, PARAM_DISTANCE));

                PointGeometry point = (PointGeometry) searchRequest.getGeoJson().getGeometry();

                params.put(PARAM_X_COORDINATE, point.getCoordinates()[0]);
                params.put(PARAM_Y_COORDINATE, point.getCoordinates()[1]);
                params.put(PARAM_DISTANCE, convertDistanceToMeters(searchRequest)); //Expecting km or miles
            } else if (searchRequest.isGeometryOfType(GeometryType.Polygon)) {
                clauses.add(QueryUtils.withinClause(COLUMN_SITE_LOCATION, PARAM_POLYGON));
                params.put(PARAM_POLYGON, searchRequest.getGeoJson().getGeometry());
            }

            if (StringUtils.isNotBlank(searchRequest.getName())) {
                clauses.add(QueryUtils.likeClause(COLUMN_SHOPPING_CENTER_NAME, PARAM_NAME));
                params.put(PARAM_NAME, QueryUtils.contains(searchRequest.getName()));
            }
            if (StringUtils.isNotBlank(searchRequest.getOwner())) {
                clauses.add(QueryUtils.likeClause(COLUMN_SHOPPING_CENTER_OWNER, PARAM_OWNER));
                params.put(PARAM_OWNER, QueryUtils.contains(searchRequest.getOwner()));
            }
            if (StringUtils.isNotBlank(searchRequest.getPostalCode())) {
                clauses.add(QueryUtils.equalsClause(COLUMN_SITE_POSTAL_CODE, PARAM_POSTAL_CODE));
                params.put(PARAM_POSTAL_CODE, searchRequest.getPostalCode());
            }
            if (StringUtils.isNotBlank(searchRequest.getCity())) {
                clauses.add(QueryUtils.likeClause(COLUMN_SITE_CITY, PARAM_CITY));
                params.put(PARAM_CITY, QueryUtils.contains(searchRequest.getCity()));
            }
            if (StringUtils.isNotBlank(searchRequest.getCounty())) {
                clauses.add(QueryUtils.likeClause(COLUMN_SITE_COUNTY, PARAM_COUNTY));
                params.put(PARAM_COUNTY, QueryUtils.contains(searchRequest.getCounty()));
            }
            if (StringUtils.isNotBlank(searchRequest.getState())) {
                clauses.add(QueryUtils.likeClause(COLUMN_SITE_STATE, PARAM_STATE));
                params.put(PARAM_STATE, QueryUtils.contains(searchRequest.getState()));
            }

            clauses.add(QueryUtils.isNull(COLUMN_SITE_DELETED_DATE));
            clauses.add(QueryUtils.isNull(COLUMN_SHOPPING_CENTER_DELETED_DATE));

            prepareQuery();
        }

        private String prepareQuery() {
            for (int i = 0; i < clauses.size(); i++) {
                String clause = clauses.get(i);

                //Append clause to query
                query.append(clause);

                //Append AND if not last clause in list
                if (i < clauses.size() - 1) {
                    query.append("AND");
                }
            }

            //This will likely need to become dynamic
            query.append("ORDER BY sh.name DESC");

            return query.toString();
        }

        public String getQuery() {
            return query.toString();
        }

        public Map<String, Object> getParams() {
            return params;
        }
    }
}

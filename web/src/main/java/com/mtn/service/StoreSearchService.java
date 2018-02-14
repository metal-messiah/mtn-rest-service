package com.mtn.service;

import com.mtn.constant.GeometryType;
import com.mtn.model.domain.search.StoreSearchResult;
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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * Created by Allen on 4/30/2017.
 */
@Service
public class StoreSearchService {

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
            "  st.id AS store_id, " +
            "  st.name, " +
            "  st.date_closed," +
            "  st.date_opened " +
            "FROM store st " +
            "  LEFT JOIN site s ON s.id = st.site_id " +
            "WHERE";

    public List<StoreSearchResult> search(SearchRequest searchRequest) {
        StoreSearchQueryBuilder queryBuilder = new StoreSearchQueryBuilder(searchRequest);
        return jdbcTemplate.query(queryBuilder.getQuery(), queryBuilder.getParams(), new RowMapper<StoreSearchResult>() {
            @Override
            public StoreSearchResult mapRow(ResultSet resultSet, int i) throws SQLException {
                StoreSearchResult model = new StoreSearchResult();
                model.setStoreId(resultSet.getInt("store_id"));
                model.setName(resultSet.getString("name"));
                model.setSiteId(resultSet.getInt("site_id"));
                model.setAddress1(resultSet.getString("address_1"));
                model.setAddress2(resultSet.getString("address_2"));
                model.setCity(resultSet.getString("city"));
                model.setState(resultSet.getString("state"));
                model.setCounty(resultSet.getString("county"));
                model.setCountry(resultSet.getString("country"));
                model.setIntersectionStreetPrimary(resultSet.getString("intersection_street_primary"));
                model.setIntersectionStreetSecondary(resultSet.getString("intersection_street_secondary"));

                Date dateClosed = resultSet.getDate("date_closed");
                if (dateClosed != null) {
                    model.setDateClosed(LocalDateTime.ofInstant(dateClosed.toInstant(), ZoneId.systemDefault()));
                }

                Date dateOpened = resultSet.getDate("date_opened");
                if (dateOpened != null) {
                    model.setDateOpened(LocalDateTime.ofInstant(dateOpened.toInstant(), ZoneId.systemDefault()));
                }

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

    private class StoreSearchQueryBuilder extends QueryBuilder {

        private static final String COLUMN_SITE_CITY = "s.city";
        private static final String COLUMN_SITE_COUNTY = "s.county";
        private static final String COLUMN_SITE_DELETED_DATE = "s.deleted_date";
        private static final String COLUMN_SITE_LOCATION = "s.location";
        private static final String COLUMN_SITE_POSTAL_CODE = "s.postal_code";
        private static final String COLUMN_SITE_STATE = "s.state";
        private static final String COLUMN_STORE_DATE_CLOSED = "st.date_closed";
        private static final String COLUMN_STORE_DELETED_DATE = "st.deleted_date";
        private static final String COLUMN_STORE_NAME = "st.name";
        private static final String COLUMN_STORE_DATE_OPENED = "st.date_opened";

        private static final String PARAM_CITY = "city";
        private static final String PARAM_DATE_CLOSED_END = "dateClosedEnd";
        private static final String PARAM_DATE_CLOSED_START = "dateClosedStart";
        private static final String PARAM_COUNTY = "county";
        private static final String PARAM_DISTANCE = "distance";
        private static final String PARAM_NAME = "name";
        private static final String PARAM_DATE_OPENED_START = "dateOpenedStart";
        private static final String PARAM_DATE_OPENED_END = "dateOpenedEnd";
        private static final String PARAM_POLYGON = "polygon";
        private static final String PARAM_POSTAL_CODE = "postalCode";
        private static final String PARAM_STATE = "state";
        private static final String PARAM_X_COORDINATE = "xCoordinate";
        private static final String PARAM_Y_COORDINATE = "yCoordinate";

        public StoreSearchQueryBuilder(SearchRequest searchRequest) {
            super(QUERY, COLUMN_STORE_NAME);

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
                clauses.add(QueryUtil.likeClause(COLUMN_STORE_NAME, PARAM_NAME));
                params.put(PARAM_NAME, QueryUtil.contains(searchRequest.getName()));
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
            if (searchRequest.getClosedBefore() != null && searchRequest.getClosedAfter() != null) {
                clauses.add(QueryUtil.betweenClause(COLUMN_STORE_DATE_CLOSED, PARAM_DATE_CLOSED_START, PARAM_DATE_CLOSED_END));
                params.put(PARAM_DATE_CLOSED_START, searchRequest.getClosedAfter());
                params.put(PARAM_DATE_CLOSED_END, searchRequest.getClosedBefore());
            } else if (searchRequest.getClosedBefore() != null) {
                clauses.add(QueryUtil.isLessThanClause(COLUMN_STORE_DATE_CLOSED, PARAM_DATE_CLOSED_END));
                params.put(PARAM_DATE_CLOSED_END, searchRequest.getClosedBefore());
            } else if (searchRequest.getClosedAfter() != null) {
                clauses.add(QueryUtil.isGreaterThanClause(COLUMN_STORE_DATE_CLOSED, PARAM_DATE_CLOSED_START));
                params.put(PARAM_DATE_CLOSED_START, searchRequest.getClosedAfter());
            }
            if (searchRequest.getOpenedBefore() != null && searchRequest.getOpenedAfter() != null) {
                clauses.add(QueryUtil.betweenClause(COLUMN_STORE_DATE_OPENED, PARAM_DATE_OPENED_START, PARAM_DATE_OPENED_END));
                params.put(PARAM_DATE_OPENED_START, searchRequest.getOpenedAfter());
                params.put(PARAM_DATE_OPENED_END, searchRequest.getOpenedBefore());
            } else if (searchRequest.getOpenedBefore() != null) {
                clauses.add(QueryUtil.isLessThanClause(COLUMN_STORE_DATE_OPENED, PARAM_DATE_OPENED_END));
                params.put(PARAM_DATE_OPENED_END, searchRequest.getOpenedBefore());
            } else if (searchRequest.getOpenedAfter() != null) {
                clauses.add(QueryUtil.isGreaterThanClause(COLUMN_STORE_DATE_OPENED, PARAM_DATE_OPENED_START));
                params.put(PARAM_DATE_OPENED_START, searchRequest.getOpenedAfter());
            }

            clauses.add(QueryUtil.isNullClause(COLUMN_SITE_DELETED_DATE));
            clauses.add(QueryUtil.isNullClause(COLUMN_STORE_DELETED_DATE));

            prepareQuery();
        }
    }
}

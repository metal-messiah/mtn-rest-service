package com.mtn.util;

/**
 * Created by Allen on 4/21/2017.
 */
public class QueryUtil {

    /**
     * Produces "%BOB%" if value equals "BOB"
     */
    public static String contains(String value) {
        return String.format("%%%s%%", value);
    }

    /**
     * Produces "BOB%" if value equals "BOB"
     */
    public static String startsWith(String value) {
        return String.format("%s%%", value);
    }

    /**
     * Produces "%BOB" if value equals "BOB"
     */
    public static String endsWithClause(String value) {
        return String.format("%%%s", value);
    }

    public static String equalsClause(String column, String namedParameter) {
        return String.format(" %s = :%s ", column, namedParameter);
    }

    public static String likeClause(String column, String namedParameter) {
        return String.format(" %s LIKE :%s ", column, namedParameter);
    }

    public static String betweenClause(String column, String startNamedParameter, String endNamedParameter) {
        return String.format(" %s BETWEEN :%s AND :%s ", column, startNamedParameter, endNamedParameter);
    }

    public static String withinClause(String column, String namedParameter) {
        return String.format(" ST_WITHIN(%s, :%s) ", column, namedParameter);
    }

    public static String withinDistanceClause(String column, String xCoordinateNamedParameter, String yCoordinateNamedParameter, String distanceNamedParameter) {
        return String.format(" ST_DWITHIN(%s, ST_MAKEPOINT(:%s, :%s), :%s) ", column, xCoordinateNamedParameter, yCoordinateNamedParameter, distanceNamedParameter);
    }

    public static String isNullClause(String column) {
        return String.format(" %s IS NULL ", column);
    }

    public static String isGreaterThanClause(String column, String namedParameter) {
        return String.format(" %s > %s ", column, namedParameter);
    }

    public static String isLessThanClause(String column, String namedParameter) {
        return String.format(" %s < %s ", column, namedParameter);
    }
}

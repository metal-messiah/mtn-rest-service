package com.mtn.util;

/**
 * Created by Allen on 4/21/2017.
 */
public class QueryUtils {

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
    public static String endsWith(String value) {
        return String.format("%%%s", value);
    }
}

package com.mtn.util;

import com.mtn.constant.DistanceUnit;

/**
 * Created by Allen on 4/30/2017.
 */
public class DistanceUtil {

    public static Double convertDistanceToMeters(Double distance, DistanceUnit unit) {
        Double kilometers = distance;

        //Convert miles to kilometers if necessary
        if (unit == DistanceUnit.MILE) {
            kilometers = kilometers * 1.60934;
        }

        //Convert kilometers to meters
        return kilometers * 1000;
    }

}

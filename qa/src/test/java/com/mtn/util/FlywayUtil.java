package com.mtn.util;

import com.mtn.constant.Constants;
import org.flywaydb.core.Flyway;

public class FlywayUtil {

    public static void cleanDatabase() {
        flyway().clean();
    }

    public static void initDatabase() {
        flyway().clean();
        flyway().migrate();
    }

    private static Flyway flyway() {
        Flyway flyway = new Flyway();
        flyway.setDataSource(Constants.DATASOURCE_URL, Constants.DATASOURCE_USERNAME, Constants.DATASOURCE_PASSWORD);
        flyway.setLocations(Constants.MIGRATIONS_LOCATION);
        return flyway;
    }
}

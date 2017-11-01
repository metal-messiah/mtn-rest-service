package com.mtn.constant;

public class Constants {

    public static final String AUTH0_API_AUDIENCE = "https//mtnra.herokuapp.com";
    public static final String AUTH0_API_CLIENT_ID = "WWaSJKlYh7u4p5dF9YXYdhtn81JWiqaL";
    public static final String AUTH0_API_SECRET = "_tjQVnbUm7RQdkdff-0Syb0-cK_P6TQAX4EoGyudchXGg6bpx3xo-I87n2fK3HiW";
    public static final String AUTH0_CLIENT_ID = "op5D7TLsM2wDZhg8JLnVh1bn9jgfPWrZ";
    public static final String AUTH0_DOMAIN = "mtnra.auth0.com";
    public static final String AUTH0_SECRET = "iUiP6LU6_ziZlbRL_f0mqf5tZ86AscDcGJzR30wNeSgS8MygedmrqMKFxr7fcS8f";
    public static final String AUTH0_REALM = "Username-Password-Authentication";

    //WARNING - DO NOT EVER SET THIS TO THE PRODUCTION DATABASE!
    //The test suite is set up to clean the database completely before and after running!
    public static final String DATASOURCE_URL = "jdbc:postgresql://localhost:5432/mtn";
    public static final String DATASOURCE_USERNAME = "mtn-service-user";
    public static final String DATASOURCE_PASSWORD = "4ccb15ce-0453-11e7-93ae-92361f002671";
    public static final String DATASOURCE_DRIVER = "org.postgresql.Driver";

    public static final String MIGRATIONS_LOCATION = "classpath:db/migration";

    public static final String SERVER_HOST = "http://localhost:8080";

    public static final String TEST_USER_USERNAME = "system.administrator@mtnra.com";
    public static final String TEST_USER_PASSWORD = "TK421Whyarentyouatyourpost?";
}

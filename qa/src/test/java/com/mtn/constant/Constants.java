package com.mtn.constant;

public class Constants {

    public static final String AUTH0_API_AUDIENCE = "https://mtnra.herokuapp.com";
    public static final String AUTH0_API_CLIENT_ID = "UtMThNghwlK13gzUuRXeF3kUHMQMGMiX";
    public static final String AUTH0_API_SECRET = "7m0ykt_SX4wb_aYOTHzNZUkVD73U7c8Cye5gWefdu4eih2oYLN-meuPHKnkwd3V8";
    public static final String AUTH0_CLIENT_ID = "FArOoQuRqPT1MZFsNE9qnxeykHp48cIO";
    public static final String AUTH0_DOMAIN = "asudweeks.auth0.com";
    public static final String AUTH0_SECRET = "cfI8g1OJ0QX8J-edJjypVCXGA6ef5dJMSG0OAiWGJ7Lzs9st-nsRhO0ccDXcAYEU";
    public static final String AUTH0_REALM = "Username-Password-Authentication";

    //WARNING - DO NOT EVER SET THIS TO THE PRODUCTION DATABASE!
    //The test suite is set up to clean the database completely before and after running!
    public static final String DATASOURCE_URL = "jdbc:postgresql://localhost:5433/mtn";
    public static final String DATASOURCE_USERNAME = "mtn-service-user";
    public static final String DATASOURCE_PASSWORD = "4ccb15ce-0453-11e7-93ae-92361f002671";
    public static final String DATASOURCE_DRIVER = "org.postgresql.Driver";

    public static final String MIGRATIONS_LOCATION = "classpath:db/migration";

    public static final String SERVER_HOST = "http://localhost:8080";

    public static final String TEST_USER_USERNAME = "system.administrator@mtnra.com";
    public static final String TEST_USER_PASSWORD = "TK421Whyarentyouatyourpost?";
}

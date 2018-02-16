package com.mtn.constant;

public class Constants {

    public static final String AUTH0_API_AUDIENCE = "http://mtnra.herokuapp.com";
    public static final String AUTH0_API_CLIENT_ID = "JBREN0tTl16ay6qf9vW9CbxqBnVg9qeQ";
    public static final String AUTH0_API_SECRET = "ao68uPu1JnyTdXP1avAOptn-mTorIv2xMD6-5oNdHxhEeWFw5_NmL4ZNbVlP8NiC";
    public static final String AUTH0_CLIENT_ID = "op5D7TLsM2wDZhg8JLnVh1bn9jgfPWrZ";
    public static final String AUTH0_DOMAIN = "mtnra.auth0.com";
    public static final String AUTH0_SECRET = "iUiP6LU6_ziZlbRL_f0mqf5tZ86AscDcGJzR30wNeSgS8MygedmrqMKFxr7fcS8f";
    public static final String AUTH0_REALM = "password";

    //WARNING - DO NOT EVER SET THIS TO THE PRODUCTION DATABASE!
    //The test suite is set up to clean the database completely before and after running!
    public static final String DATASOURCE_URL = "jdbc:mysql://localhost:3306/mtn_dev?useSSL=false";
    public static final String DATASOURCE_USERNAME = "tyjoti89";
    public static final String DATASOURCE_PASSWORD = "joseph89";
    public static final String DATASOURCE_DRIVER = "com.mysql.jdbc.Driver";

    public static final String MIGRATIONS_LOCATION = "classpath:db/migration";

    public static final String SERVER_HOST = "http://localhost:8080";

    public static final String TEST_USER_USERNAME = "system.administrator@mtnra.com";
    public static final String TEST_USER_PASSWORD = "joseph89";
}

package com.rest;

import io.restassured.config.LogConfig;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;

public class logging {
    @Test
    public void firstTest() {
        Set<String> headers = new HashSet<String>();
        headers.add("Accept");
        headers.add("x-api-key");

        given().
                baseUri("https://api.getpostman.com").
                header("x-api-key", "change_your_postman_secret").
                //                config(config.logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails())). //Code optimization
                config(config.logConfig(LogConfig.logConfig().blacklistHeader("x-api-key"))). //to hide single secret header
                config(config.logConfig(LogConfig.logConfig().blacklistHeaders(headers))). //to hide set of secret headers
                log().all(). // print log every time
/*      log().headers().
        log().cookies().
        log().uri().
        log().parameters().*/
//        log().ifValidationFails(). // only print when validation failed
                when().
                get("/workspaces").
                then().
//                log().all(). // print log every time
//        log().ifError(). // only print when error is there
//        log().ifValidationFails(). // only print when validation failed
                assertThat().statusCode(200);
    }
}

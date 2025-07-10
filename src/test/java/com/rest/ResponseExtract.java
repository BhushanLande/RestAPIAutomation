package com.rest;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ResponseExtract {
    @org.testng.annotations.Test
    public void firstTest(){
       Response rs = given().
                baseUri("https://api.getpostman.com").
                header("x-api-key","_change_this").
                when().
                get("/workspaces").
                then().
                log().all().
                assertThat().statusCode(200).extract().response();
       System.out.println(rs.asString()); // To get response as object we user .extract().response() methods
    }
}

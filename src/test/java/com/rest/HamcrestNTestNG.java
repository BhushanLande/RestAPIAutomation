package com.rest;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class HamcrestNTestNG {
    @org.testng.annotations.Test
    public void firstTest(){
        String name = given().
                baseUri("https://api.getpostman.com").
                header("x-api-key", "change_your_postman_secret").
                when().
                get("/workspaces").
                then().
                log().all().
                assertThat().statusCode(200).extract().response().path("workspaces[0].name").toString();

        assertThat(name, equalTo("My Workspace")); //Using hamcrest library
        Assert.assertEquals(name, "My Workspace"); //Using testng assertion
    }
}

package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ReqSpec {

    @BeforeMethod
    public void SetupMethod(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.
                setBaseUri("https://api.getpostman.com").
                addHeader("x-api-key", "_change_this_").log(LogDetail.ALL);

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).log(LogDetail.ALL);

        RestAssured.requestSpecification = requestSpecBuilder.build();
        RestAssured.responseSpecification  = responseSpecBuilder.build();
    }

    @Test
    public void firstTest(){

        String s = "ss";
        String name = given().
                when().
                get("/workspaces").
                then().
                log().all().
                assertThat().extract().response().path("workspaces[0].name").toString();

        assertThat(name, equalTo("My Workspace")); //Using hamcrest library
        Assert.assertEquals(name, "My Workspace"); //Using testng assertion
    }
}

package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Objects;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

public class PayLoadHandling {

    @BeforeMethod
    public void SetupMethod() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.
                setBaseUri("https://api.getpostman.com").
                addHeader("x-api-key", "_change_this_").
                setContentType(ContentType.JSON).
                log(LogDetail.ALL);

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).log(LogDetail.ALL);

        RestAssured.requestSpecification = requestSpecBuilder.build();
        RestAssured.responseSpecification = responseSpecBuilder.build();
    }

    @Test
    public void postRequestAutomation() {
        File file = new File("src/main/resources/WorkspacePayload.json");

        given().
                body(file).
                when().
                post("/workspaces").
                then().
                assertThat().
                body("workspace.name", equalTo("My WorkspaceFileObj"),
                        "workspace.id", matchesPattern("^[a-z0-9-]{36}$")).
                log().all();
    }

    @Test
    public void postRequestAutomationWithMap() {
        HashMap<String, Object> mainObject = new HashMap<>();
        HashMap<String, String> nestedObject = new HashMap<>();
        nestedObject.put("name","My WorkspaceMapObj");
        nestedObject.put("type","personal");
        nestedObject.put("description","This is from RestAssured map object");
        mainObject.put("workspace", nestedObject);
        given().
                body(mainObject).
                when().
                post("/workspaces").
                then().
                assertThat().
                body("workspace.name", equalTo("My WorkspaceMapObj"),
                        "workspace.id", matchesPattern("^[a-z0-9-]{36}$")).
                log().all();
    }

}

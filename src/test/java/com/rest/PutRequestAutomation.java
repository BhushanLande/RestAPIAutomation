package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

public class PutRequestAutomation {

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
    public void putRequestAutomationWithBdd() {
        String workspaceId = "2b2fd16f-8807-4923-a074-0ff6f28702eb";
        String payload = "{\n" +
                "    \"workspace\": {\n" +
                "        \"name\": \"My WorkspacePut\",\n" +
                "        \"type\": \"personal\",\n" +
                "        \"description\": \"This is updated using RestAssured\"\n" +
                "    }\n" +
                "}";

        given().
                pathParam("workspaceId", workspaceId).
                body(payload).
                when().
                put("/workspaces/{workspaceId}").
                then().
                assertThat().
                body("workspace.name", equalTo("My WorkspacePut"),
                        "workspace.id", matchesPattern("^[a-z0-9-]{36}$"),
                        "workspace.id", equalTo(workspaceId)).
                log().all();
    }

}

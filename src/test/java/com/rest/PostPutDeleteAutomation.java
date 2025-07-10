package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

public class PostPutDeleteAutomation {
    String workspaceId ="";
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

    @Test (priority = 1)
    public void postRequestAutomation() {
        String payload = "{\n" +
                "    \"workspace\": {\n" +
                "        \"name\": \"My Workspace55\",\n" +
                "        \"type\": \"personal\",\n" +
                "        \"description\": \"This is created using RestAssured\"\n" +
                "    }\n" +
                "}";

        Response response = given().
                body(payload).
                when().
                post("/workspaces").
                then().
                assertThat().
                body("workspace.name", equalTo("My Workspace55"),
                        "workspace.id", matchesPattern("^[a-z0-9-]{36}$")).
                log().all().extract().response();
        workspaceId = response.path("workspace.id");
        System.out.println("Extracted Workspace ID: " + workspaceId);
    }

    @Test (priority = 2)
    public void putRequestAutomationWithBdd() {
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

    @Test(priority = 3)
    public void deleteRequestAutomationWithBdd() {
        given().
                pathParam("workspaceId", workspaceId).
                when().
                delete("/workspaces/{workspaceId}").
                then().
                assertThat().
                body("workspace.id", matchesPattern("^[a-z0-9-]{36}$"),
                        "workspace.id", equalTo(workspaceId)).
                log().all();
    }

}

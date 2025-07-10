package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.LogConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PostReqAutomation {

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
        String payload = "{\n" +
                "    \"workspace\": {\n" +
                "        \"name\": \"My Workspace55\",\n" +
                "        \"type\": \"personal\",\n" +
                "        \"description\": \"This is created using RestAssured\"\n" +
                "    }\n" +
                "}";

        given().
                body(payload).
                when().
                post("/workspaces").
                then().
                assertThat().
                body("workspace.name", equalTo("My Workspace55"),
                        "workspace.id", matchesPattern("^[a-z0-9-]{36}$")).
                log().all();
    }

    @Test
    public void postRequestAutomationNonBDD() {
        String payload = "{\n" +
                "    \"workspace\": {\n" +
                "        \"name\": \"My Workspace5\",\n" +
                "        \"type\": \"personal\",\n" +
                "        \"description\": \"This is created using RestAssured\"\n" +
                "    }\n" +
                "}";

        Response response = with().
                body(payload).
                post("/workspaces");

        assertThat(response.<String>path("workspace.name"), equalTo("My Workspace5"));
        assertThat(response.<String>path("workspace.name"), matchesPattern("^[a-z0-9-]{36}$"));
    }

    @Test
    public void myTest(){
        /*Get Request Automation and email extraction*/
        RestAssured.requestSpecification = new RequestSpecBuilder().
                setBaseUri("base_uri_for_app").
                setContentType(ContentType.JSON).
                addHeader("key","value").build();

        RestAssured.responseSpecification = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON).
                expectHeader("key","value").
                expectStatusCode(200).build();

        Response response = given().
                when().
                get("/users");

        String userMail = response.then().extract().path("email").toString();

        /*.... Post request automation ....*/

        HashMap <String, String> request = new HashMap<>();
        request.put("email", "email");
        request.put("password","password");

        Response response1 = given()
                .config(config.logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails()))
                .body(request.toString())
                .when()
                .post("/auth/signup");

        response1.then()
                .statusCode(201)
                .body("message", equalTo("Signup successful"))
                .body("authToken", notNullValue());

        String authToken = response1.jsonPath().getString("authToken");
    }
}

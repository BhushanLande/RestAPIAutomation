package com.rest;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;


public class AssertionOnArray {

    @org.testng.annotations.Test
    public void firstTest() {
      given().
                baseUri("https://api.getpostman.com").
                header("x-api-key", "change_your_postman_secret").
                when().
                get("/workspaces").
                then().
//                log().all().
                assertThat().statusCode(200).
              body("workspaces.name",containsInAnyOrder("My Workspace", "Dev", "Team Workspace")). //It strictly checks order and no of elements
              body("workspaces.name", contains("My Workspace","Team Workspace", "Dev")).  //It does not check order only checks no of elements
              body("workspaces.name", not(empty())).  // Checks for empty value for collection
              body("workspaces.name", not(emptyArray())).  // Checks for empty value for specifically array
              body("workspaces.name", hasSize(3)).  // Checks for size of an array
              body("workspaces.name", everyItem(startsWithIgnoringCase("my")));  // Checks for values that starts with my text
    }
}

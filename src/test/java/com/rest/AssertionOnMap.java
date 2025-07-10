package com.rest;

import java.util.Collection;
import java.util.Collections;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class AssertionOnMap {
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
                body("workspaces[0]",hasKey("name")).  // Checks for map having key name
                body("workspaces[0]",hasValue("My Workspace")).  // Checks for values in array
                body("workspaces[0]",hasEntry("name","My Workspace")).  // Checks for both key and value
                body("workspaces[0]", not(equalTo(Collections.EMPTY_MAP))).  // Checks that collection is empty
                body("workspaces[0].name", allOf(startsWith("My"),contains(" Workspace"))).  // Checks for multiple assertion and that should match all validations
                body("workspaces[2].name", anyOf(startsWith("D"),contains(" Devops")));  // Checks for multiple assertion and if any one passed its passing method
    }
}

package com.rest;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class ExtUsingJsonPath {
    @org.testng.annotations.Test
    public void firstTest(){
        Response rs = given().
                baseUri("https://api.getpostman.com").
                header("x-api-key", "change_your_postman_secret").
                when().
                get("/workspaces").
                then().
                log().all().
                assertThat().statusCode(200).extract().response();
        System.out.println(rs.asString()); // To get response as object we user .extract().response() methods
        System.out.println("Workspace1 name: "+rs.path("workspaces[0].name")); // To get first workspace name
        // op: Workspace1 name: My Workspace

        JsonPath jsonPath = new JsonPath(rs.asString()); //Storing in jsonpath object

        System.out.println("Workspace1 name: "+jsonPath.getString("workspaces[0].name")); // To get first workspace name
        //op: Workspace1 name: My Workspace

        System.out.println("Workspace1 name: "+JsonPath.from(rs.asString()).getString("workspaces[0].name"));
        //op: Workspace1 name: My Workspace
    }
}

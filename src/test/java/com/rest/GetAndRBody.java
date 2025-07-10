package com.rest;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class GetAndRBody {

    @org.testng.annotations.Test
    public void firstTest(){
                given().
                         baseUri("https://api.getpostman.com").
                        header("x-api-key", "change_your_postman_secret").
                        when().
                       get("/workspaces").
                then().
                        log().all().
                        assertThat().statusCode(200).
                        body("workspaces.name", hasItems("My Workspace", "Team Workspace", "Dev"),"workspaces.type",hasItems("personal",
                                "team",
                                "team")). //Multiple assertion in same body
                        body("workspaces[0].name",is(equalTo("My Workspace"))). //To check first workspace name
                        body("workspaces.size()",equalTo(3)). //To check the workspace array size is 3
                        body("workspaces.name",hasItem("Dev")); //To check the dev workspace is present in list
    }
}

package tests;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import io.restassured.response.Response;
import org.testng.annotations.Test;

public class DeleteRequestExamples {
    @Test
    public void deleteTest() {
        baseURI = "https://reqres.in";

        Response response =
                given().
                        delete("/api/users/10").
                        then().
                        statusCode(204).
                        log().all().
                        extract().response();
    }
}

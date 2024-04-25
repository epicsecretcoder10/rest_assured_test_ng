package tests;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class PutRequestExamples {
    @Test
    public void putTest() throws JsonProcessingException {

        baseURI = "https://reqres.in";
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("name", "Ughur");
        requestMap.put("job", "Programmer");

        System.out.println(requestMap);

        String jsonBody = new ObjectMapper().writeValueAsString(requestMap);
        System.out.println(jsonBody);

        Response response =
                given().
                        contentType(ContentType.JSON).
                        accept(ContentType.JSON).
                        body(jsonBody).
                        when().
                        put("/api/users/2").
                        then().
                        statusCode(200).
                        and().
                        contentType(ContentType.JSON).
                        log().all().
                        extract().response();

        Assert.assertEquals(response.path("job"), "Programmer");
    }

}

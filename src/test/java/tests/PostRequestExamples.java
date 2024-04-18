package tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PostRequestExamples {

    @Test
    public void testPostRequest() throws JsonProcessingException {

        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("name", "Ughur");
        requestMap.put("job", "Hacker");

        System.out.println(requestMap);

        System.out.println("=====================");

        // Converting Java to JSON => Serialization

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(requestMap);

        System.out.println(jsonRequest); // compact print

        System.out.println("=====================");

        RestAssured.baseURI = "https://reqres.in";

        Response response = given().
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                body(jsonRequest).
                when().
                post("/api/users").
                then().
                extract().response();

        Assert.assertEquals(response.getStatusCode(), 201);
        Assert.assertEquals(response.path("name"), "Ughur");
        Assert.assertEquals(response.path("job"), "Hacker");

        // Converting JSON to JAVA is called => Deserialization
        Map<String, String> responseMap = objectMapper.readValue(response.asString(), Map.class);
        System.out.println(responseMap);

        Assert.assertEquals(responseMap.get("name"), "Ughur");
        Assert.assertEquals(responseMap.get("job"), "Hacker");

    }
}

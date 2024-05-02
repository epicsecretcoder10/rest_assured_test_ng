package utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ContactListUtils extends ContactListTestBase{

    public static String getToken(String email, String password) throws JsonProcessingException {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("email", email);
        userInfo.put("password", password);

        String jsonRequest = new ObjectMapper().writeValueAsString(userInfo);

        JsonPath jsonPath =
                given().
                        contentType(ContentType.JSON).
                        accept(ContentType.JSON).
                        body(jsonRequest).
                        when().
                        post("/users/login").
                        then().
                        statusCode(200).
                        contentType(ContentType.JSON).
                        log().all().
                        extract().response().jsonPath();

        String token = jsonPath.getString("token");

        return "Bearer " + token;
    }
}

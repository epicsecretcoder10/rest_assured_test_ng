package tests.contactList;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.ContactListTestBase;
import utilities.ContactListUtils;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class AddContactAPI extends ContactListTestBase {

    @Test
    public void addContactTest() throws JsonProcessingException {
        Map <String, Object> contactInfo = new HashMap<>();
        contactInfo.put("firstName", "Sam");
        contactInfo.put("lastName", "Sung");
        contactInfo.put("birthdate", "2003-10-04");
        contactInfo.put("email", "samsung@fake.com");
        contactInfo.put("phone", "5726394926");
        contactInfo.put("street1", "647 Main Street");
        contactInfo.put("street2", "Apartment P");
        contactInfo.put("city", "Palo Alto");
        contactInfo.put("stateProvince", "CA");
        contactInfo.put("postalCode", 92746);
        contactInfo.put("country", "US");

        JsonPath jsonPath =
        given().
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                header("Authorization", ContactListUtils.getToken("bendover@meme.com", "6qDRijk7u6Z!5ku")).
                body(contactInfo).
                        when().
                        post("/contacts").
                        then().
                        statusCode(201).
                        contentType(ContentType.JSON).
                        log().all().
                        extract().response().jsonPath();

        String lastName = jsonPath.getString("lastName");

        Assert.assertEquals(lastName, "Sung");

        String id = jsonPath.getString("id");

        Assert.assertTrue(id != null);


    }
}

package tests.contactList;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.ContactListTestBase;
import utilities.ContactListUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class GetContactListAPI extends ContactListTestBase {

    @Test
    public void getContactListTestUnauthorized() {
        JsonPath jsonPath =
                given().
                accept(ContentType.JSON).
                when().
                get("/contacts").
                then().
                assertThat().
                statusCode(401).
                contentType(ContentType.JSON).
                log().all().extract().jsonPath();

        String errorMessage = jsonPath.getString("error");

        System.out.println(errorMessage);

        Assert.assertEquals(errorMessage, "Please authenticate.");
    }

    @Test
    public void getContactListTest() {
        JsonPath jsonPath =
                given().
                        accept(ContentType.JSON).
                        when().
                        header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2NWM0MjBmZWUyNTgwMTAwMTMwODY2ZjUiLCJpYXQiOjE3MTQwODg0OTV9._KYg4FcQbV-rw_3jGuHTDYOewgZLb9kRPsClE-zn5XE").
                        get("/contacts").
                        then().
                        assertThat().
                        statusCode(200).
                        contentType(ContentType.JSON).
                        log().all().extract().jsonPath();
    }
    @Test
    public void getContactListTestWithUtil() throws JsonProcessingException {

        JsonPath jsonPath =
                given().
                        accept(ContentType.JSON).
                        header("Authorization", ContactListUtils.getToken("bendover@meme.com", "6qDRijk7u6Z!5ku")).
                        when().
                        get("/contacts").
                        then().
                        assertThat().
                        statusCode(200).
                        contentType(ContentType.JSON).
                        log().all().extract().jsonPath();

        List contacts = jsonPath.getList("");
        System.out.println("Number of Contacts" + contacts.size());

        List contactNames = jsonPath.getList("firstName");
        System.out.println("Contact first names: " + contactNames);

        Assert.assertTrue(contactNames.contains("Saad"));


    }
}

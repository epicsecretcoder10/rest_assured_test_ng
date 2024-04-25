package tests.contactList;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.ContactListTestBase;

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
}

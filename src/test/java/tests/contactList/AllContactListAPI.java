package tests.contactList;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.ContactListTestBase;
import utilities.ContactListUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class AllContactListAPI extends ContactListTestBase {

    String id;

    @Test (priority = 1)
    public void addContactTest() throws JsonProcessingException {
        Map<String, Object> contactInfo = new HashMap<>();
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
                        spec(ContactListUtils.setRequestSpecification(ContentType.JSON)).
                        body(contactInfo).
                        when().
                        post("/contacts").
                        then().
                        spec(ContactListUtils.setResponseSpecification(201, ContentType.JSON)).
                        extract().response().jsonPath();

        String lastName = jsonPath.getString("lastName");

        Assert.assertEquals(lastName, "Sung");

        id = jsonPath.getString("_id");
        System.out.println("id" + id);

    }

    @Test (priority = 2)
    public void getContactListTest() throws JsonProcessingException {
        JsonPath jsonPath =
                given().
                        spec(ContactListUtils.setRequestSpecification(ContentType.JSON)).
                        when().
                        get("/contacts").
                        then().
                        assertThat().
                        spec(ContactListUtils.setResponseSpecification(200, ContentType.JSON)).
                        extract().jsonPath();

        List contacts = jsonPath.getList("");
        System.out.println("Number of Contacts: " + contacts.size());

        List contactNames = jsonPath.getList("firstName");
        System.out.println("Contact first names: " + contactNames);

        Assert.assertTrue(contactNames.contains("Saad"));

        List ids = jsonPath.getList("_id");
        Assert.assertTrue(ids.contains(id));

    }

    @Test (priority = 3)
    public void updateContactTest() throws JsonProcessingException {
        Map<String, Object> contactInfo = new HashMap<>();
        contactInfo.put("firstName", "Tom");
        contactInfo.put("lastName", "Doe");
        contactInfo.put("birthdate", "1980-01-05");
        contactInfo.put("email", "eric@fake.com");
        contactInfo.put("phone", "773-999-0000");
        contactInfo.put("street1", "123 Main Street");
        contactInfo.put("street2", "Apartment C");
        contactInfo.put("city", "Chicago");
        contactInfo.put("stateProvince", "IL");
        contactInfo.put("postalCode", 60606);
        contactInfo.put("country", "US");

        JsonPath jsonPath =
                given().
                        spec(ContactListUtils.setRequestSpecification(ContentType.JSON)).
                        pathParam("contactId", id).
                        body(contactInfo).
                        when().
                        put("/contacts/{contactId}").
                        then().
                        spec(ContactListUtils.setResponseSpecification(200, ContentType.JSON)).
                        extract().response().jsonPath();

        String firstName = jsonPath.getString("firstName");
        Assert.assertEquals(firstName, "Tom");
    }
    @Test (priority = 4)
    public void deleteContactTest() throws JsonProcessingException {
        Response response =
        given().
                spec(ContactListUtils.setRequestSpecification(ContentType.HTML)).
                pathParam("contactId", id).
                when().
                delete("/contacts/{contactId}").
                then().
                spec(ContactListUtils.setResponseSpecification(200, ContentType.HTML)).
                extract().response();

        String message = response.body().asString();
        System.out.println("message: " + message);

        Assert.assertEquals(message, "Contact deleted");


    }

}

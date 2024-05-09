package tests.contactList;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.testng.annotations.Test;
import utilities.ContactListTestBase;
import utilities.ContactListUtils;

import static io.restassured.RestAssured.given;

public class DeleteContactAPI extends ContactListTestBase {

    @Test
    public void deleteContactTest() throws JsonProcessingException {

        given().
                header("Authorization", ContactListUtils.getToken("bendover@meme.com", "6qDRijk7u6Z!5ku")).
                pathParam("contactId", "66342462fddb5f00137d2af9").
                when().
                delete("/contacts/{contactId}").
                then().
                statusCode(200).
                log().all();
    }
}

package utilities;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.path.json.JsonPath.reset;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;

public class ContactListTestBase {
    @BeforeMethod
    public static void init(){
        baseURI = "https://thinking-tester-contact-list.herokuapp.com";
    }

    @AfterTest
    public  static void destroy(){
        reset();
    }
}

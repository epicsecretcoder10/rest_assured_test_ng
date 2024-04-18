package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UniversitiesAPITest {

    // Create Rest Assured API test
    // url http://universities.hipolabs.com/search?country=United+States&name=Technology&limit=5
    // get request
    // Validate all University names contain "Technology"

        public static boolean containsSpecificText(List<String> responseList, String searchText) {
            for (String response : responseList) {
                if (response.contains(searchText)) {
                    return true;
                }
            }
            return false;
        }


    @Test
    public void verifyUniversityNames(){
        Response response =
                given().
                        queryParam("country", "United States").
                        queryParam("name", "Technology").
                        queryParam("limit", 5).
                        when().
                        get("http://universities.hipolabs.com/search").
                        then().
                        assertThat().
                        statusCode(200).
                        and().
                        extract().response();

        String expectedText = "Detroit School of Technology";
        List actualText = response.path("name");

        System.out.println(actualText);

        Assert.assertTrue(actualText.contains(expectedText));

        // assert with index
        String actualText1 = response.path("name[0]");
        System.out.println(actualText1);
        Assert.assertTrue(actualText1.contains(expectedText));

        // String check
        System.out.println("============================");
        String responseBody = response.getBody().asString();
        System.out.print(responseBody);

        Assert.assertTrue(responseBody.contains(expectedText));

    }

    @Test
    public void verifyUniversityNamesWithHamcrestMatcher() {

        String expectedText = "Technology";

        Response response =
                given().
                        queryParam("country", "United States").
                        queryParam("name", "Technology").
                        queryParam("limit", 5).
                        when().
                        get("http://universities.hipolabs.com/search").
                        then().
                        assertThat().
                        statusCode(200).extract().response();

        List<String> universityList = response.path("name");
        System.out.println(universityList);

        for (String universityName : universityList) {
            Assert.assertTrue(universityName.contains(expectedText));
        }
    }

        @Test
        public void verifyUniversityNamesWithUtility() {
            RestAssured.baseURI = "http://universities.hipolabs.com";

            Response response =
                    given().
                            queryParam("country", "United States").
                            queryParam("name", "Technology").
                            queryParam("limit", 5).
                            when().
                            get("/search").
                            then().extract().response();

            String expectedText = "Technology";

            Assert.assertEquals(response.getStatusCode(), 200);
            Assert.assertEquals(response.getContentType(), "application/json");

            List<String> actualUnvList = response.path("name");

            Assert.assertTrue(ResponseValidator.containsSpecificText(actualUnvList, expectedText));
        }

    }


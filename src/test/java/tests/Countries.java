package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class Countries {

    @Test
    public void verifyCountryNameAndCurrency() {
        Response response =
                given().
        //                queryParam("name", "Azerbaijan").
        //                queryParam("currencies", "Azerbaijani manat").
                        when().
                        get("https://restcountries.com/v3.1/capital/baku").
                        then().
                        assertThat().
                        statusCode(200).
                        and().
                        extract().response();

        String expectedText = "Azerbaijan";
        List actualText = response.path("name.common");

        System.out.println(expectedText);
        System.out.println(actualText);

        Assert.assertTrue(actualText.contains(expectedText));
        Assert.assertEquals(actualText.get(0), expectedText);
    }


}

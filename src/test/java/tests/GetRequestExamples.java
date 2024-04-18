package tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;

public class GetRequestExamples {

    @Test
    public void testGetList() {
       given().
               log().all().
               get("https://reqres.in/api/users").
               then().
               statusCode(200).
               and().
               body("data[0].first_name", equalTo("George")).
               body("data[0].last_name", equalTo("Bluth")).
               body("data.first_name", hasItem("Charles")).
               body("data.last_name", hasItems("Weaver", "Bluth"));
   //            log().all();
    }

    @Test
    public void testGetListWithHeader() {
        given().
      //          log().all().
                accept(JSON).
                header("Connection", "keep-alive").
                when().
                get("https://reqres.in/api/users").
                then().
                assertThat().
                statusCode(200).
                and().
                body("data[0].first_name", equalTo("George")).
                body("data[0].last_name", equalTo("Bluth")).
                body("data.first_name", hasItem("Charles")).
                body("data.last_name", hasItems("Weaver", "Bluth")).log().all();
    }

    @Test
    public void testGetListWithParams() {

        String email =
        given().
                log().all().
                accept(JSON).
                header("Connection", "keep-alive").
                pathParams("id", 2).
                when().
                get("https://reqres.in/api/users/{id}").
                then().
                statusCode(200).
                and().
                body("data.first_name", equalTo("Janet")).
                log().all().
                extract().path("data.email");

        System.out.println("Email: " + email);

        Assert.assertEquals(email, "janet.weaver@reqres.in");
    }

    @Test
    public void testGetListWithAllResponse() {

        Response response =
                given().
                        log().all().
                        accept(JSON).
                        header("Connection", "keep-alive").
                        pathParams("id", 2).
                        when().
                        get("https://reqres.in/api/users/{id}").
                        then().
                        statusCode(200).
                        and().
       //                 body("data.first_name", equalTo("Janet")).
                        log().all().
                        extract().response();

        String email = response.path("data.email");
        System.out.println("Email: " + email);
        Assert.assertEquals(email, "janet.weaver@reqres.in");

        String firstName = response.path("data.first_name");
        System.out.println("First Name: " + firstName);
        Assert.assertEquals(firstName, "Janet");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.getContentType().contains(JSON.toString()));
    }

    @Test
    public void testGetListWithQueryParams() {

        Response response =
        given().
                log().all().
                queryParam("page", "3").
                queryParam("per_page", "5").
                when().
                get("https://reqres.in/api/users").
                then().
                assertThat().
                and().
                contentType(JSON).
                log().all().
                extract().
                response();

        String name = response.path("data[0].first_name");
        System.out.println(name);
        Assert.assertEquals(name, "George");
        Assert.assertEquals(response.path("support.url"), "https://reqres.in/#support-heading");

        Assert.assertEquals((Integer) response.path("per_page"), 5);

        String expectedField = "ReqRes";
        String actualField = response.path("support.text");
        Assert.assertTrue(actualField.contains(expectedField));
    }
}

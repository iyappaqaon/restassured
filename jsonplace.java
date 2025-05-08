package Demo1;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class jsonplace {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    public void GetAllUsers() {
        Response response = given()
            .when()
            .get("/users");

        response.then()
            .statusCode(200)
            .body("size()", greaterThan(0));

    }

    @Test
    public void GetUserById() {
        Response response = given()
            .pathParam("id", 1)
            .when()
            .get("/users/{id}");

        response.then()
            .statusCode(200)
            .body("id", equalTo(1))
            .body("name", notNullValue());

        System.out.println(response.getBody().asPrettyString());
    }

    @Test
    public void CreateUser() {
        String requestBody = "{ \"name\": \"Iyappan\", \"username\": \"Iyappa10\", \"email\": \"iyappan@qaoncloud.com\" }";

        Response response = given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when()
            .post("/users");

        response.then()
            .statusCode(201)
            .body("name", equalTo("Iyappan"))
            .body("username", equalTo("Iyappa10"))
            .body("email", equalTo("iyappan@qaoncloud.com"));

        System.out.println(response.getBody().asPrettyString());
    }

    @Test
    public void UpdateUser() {
        String requestBody = "{ \"name\": \"Iyappan S\", \"username\": \"Iyappa2710\", \"email\": \"iyappan@qaoncloud.com\" }";

        Response response = given()
            .pathParam("id", 1)
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when()
            .put("/users/{id}");

        response.then()
            .statusCode(200)
            .body("name", equalTo("Iyappan S"))
            .body("username", equalTo("Iyappa2710"))
            .body("email", equalTo("iyappa@qaoncloud.com"));

        System.out.println(response.getBody().asPrettyString());
    }

    @Test
    public void updateUserName() {
        String requestBody = "{ \"name\": \"Iyappa\" }";

        Response response = given()
            .pathParam("id", 1)
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when()
            .patch("/users/{id}");

        response.then()
            .statusCode(200)
            .body("name", equalTo("Iyappa"));

        System.out.println(response.getBody().asPrettyString());
    }

    @Test
    public void testDeleteUser() {
        Response response = given()
            .pathParam("id", 1)
            .when()
            .delete("/users/{id}");

        response.then()
            .statusCode(200);

        System.out.println(response.getBody().asPrettyString());
    }
}

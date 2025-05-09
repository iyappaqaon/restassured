package Demo1;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class message {

    private static final String BASE_URI = "https://petstore.swagger.io/v2";
    private static final String ENDPOINT = "/pet";
    private static final String GET_ENDPOINT = "/pet/{id}";

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = BASE_URI;
    }

    @Test
    public void createPet() {
        Map<String, Object> category = new HashMap<>();
        category.put("id", 0);
        category.put("name", "Brownie");

        List<String> photoUrls = Collections.singletonList(
                "https://images.theconversation.com/files/625049/original/file-20241010-15-95v3ha.jpg?ixlib=rb-4.1.0&rect=12%2C96%2C2671%2C1335&q=45&auto=format&w=1356&h=668&fit=crop"
        );

        Map<String, Object> tag = new HashMap<>();
        tag.put("id", 0);
        tag.put("name", "string");

        List<Map<String, Object>> tags = Collections.singletonList(tag);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("id", 0);
        requestBody.put("category", category);
        requestBody.put("name", "Brownie");
        requestBody.put("photoUrls", photoUrls);
        requestBody.put("tags", tags);
        requestBody.put("status", "available");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
        .when()
                .post(ENDPOINT);

        System.out.println("Response Body:\n" + response.asPrettyString());

        response.then()
                .statusCode(200)
                .body("name", equalTo("Brownie"))
                .body("status", equalTo("available"));
    }

    @Test
    public void PetbyId() {
        long petId = 9223372036854775807L;

        Response response = given()
                .contentType(ContentType.JSON)
                .pathParam("id", petId)
        .when()
                .get(GET_ENDPOINT);

        System.out.println("GET Response for Pet ID " + petId + ":\n" + response.asPrettyString());

        response.then()
                .statusCode(200)
        		.body("name", equalTo("Rex"))
        		.body("photoUrls[0]", equalTo("Test"))
        		.body("tags", equalTo(Collections.emptyList()));
    }	
    @Test
    public void petByStatus() {
        Response response = given()
                .contentType(ContentType.JSON)
                .queryParam("status", "available")
        .when()
                .get("/pet/findByStatus");

        response.then()
                .statusCode(200)
                .body("[0].status", equalTo("available"));
    }
    @Test
    public void updatePet() {
        Map<String, Object> category = new HashMap<>();
        category.put("id", 0);
        category.put("name", "BrownieUpdated");

        List<String> photoUrls = Collections.singletonList(
                "https://images.theconversation.com/files/625049/original/file-20241010-15-95v3ha.jpg?ixlib=rb-4.1.0&rect=12%2C96%2C2671%2C1335&q=45&auto=format&w=1356&h=668&fit=crop"
        );

        Map<String, Object> tag = new HashMap<>();
        tag.put("id", 0);
        tag.put("name", "updatedTag");

        List<Map<String, Object>> tags = Collections.singletonList(tag);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("id", 0); // Make sure this ID matches the pet you created
        requestBody.put("category", category);
        requestBody.put("name", "BrownieUpdated");
        requestBody.put("photoUrls", photoUrls);
        requestBody.put("tags", tags);
        requestBody.put("status", "sold");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
        .when()
                .put(ENDPOINT);

        System.out.println("PUT Response:\n" + response.asPrettyString());

        response.then()
                .statusCode(200)
                .body("name", equalTo("BrownieUpdated"))
                .body("status", equalTo("sold"));
    }
    @Test
    public void deletePet() {
        long petId = 9223372036854775807L; 

        Response response = given()
                .contentType(ContentType.JSON)
                .pathParam("id", petId)
        .when()
                .delete(GET_ENDPOINT);

        System.out.println("DELETE Response for Pet ID " + petId + ":\n" + response.asPrettyString());

        response.then()
                .statusCode(200)
                .body("message", equalTo(String.valueOf(petId)));
    }


}

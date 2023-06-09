package ApiReusedMethods.PetMethods;

import ApiReusedMethods.ReusedMethods;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static Core.ApiConfig.*;

public class UpdatePetApi extends ReusedMethods {

    public static Response updatePet(int petId, String petName, String petStatus,
                                     int categoryId, String categoryName,
                                     int tagId, String tagName) {

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("id", petId);
        requestBody.put("name", petName);
        requestBody.put("status", petStatus);

        Map<String, Object> categoryMap = new HashMap<>();
        categoryMap.put("id", categoryId);
        categoryMap.put("name", categoryName);
        requestBody.put("category", categoryMap);

        requestBody.put("photoUrls", new String[]{});

        Map<String, Object> tagMap = new HashMap<>();
        tagMap.put("id", tagId);
        tagMap.put("name", tagName);
        requestBody.put("tags", new Map[]{tagMap});

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .put(BASE_URL + "/pet");

        System.out.println("Response status code: " + response.statusCode());
        System.out.println("Response body: " + response.body().asString());

        return response;
    }
}

package ApiReusedMethods.PetMethods;

import ApiReusedMethods.ReusedMethods;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Core.ApiConfig.*;

public class AddPetImageApi extends ReusedMethods {

    public static Response uploadPetImage(int petId, String additionalMetadata, String filePath) {
        Map<String, Object> pathParams = new HashMap<>();
        pathParams.put("petId", petId);

        Map<String, Object> formData = new HashMap<>();
        formData.put("additionalMetadata", additionalMetadata);
        formData.put("file", new File(filePath));

        Response response = RestAssured.given()
                .contentType(ContentType.MULTIPART)
                .pathParams(pathParams)
                .multiPart("additionalMetadata", additionalMetadata)
                .multiPart("file", new File(filePath))
                .post(BASE_URL + UPLOAD_IMAGE_ENDPOINT);

        // Print response status code and body
        System.out.println("Response status code: " + response.statusCode());
        System.out.println("Response body: " + response.body().asString());

        return response;
    }
}

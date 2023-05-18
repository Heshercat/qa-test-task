package ApiTests;

import ApiReusedMethods.PetMethods.AddPetImageApi;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.*;
import io.restassured.response.Response;

import static ApiReusedMethods.ReusedMethods.*;
import static Core.ApiConfig.*;

@Feature("Upload image") //Update pet
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AddPetImageApiTest {

    AddPetImageApi addPetImageApi = new AddPetImageApi();

    @Test
    @Order(1)
    @DisplayName("Check that an image successfully uploaded when send Upload image request with valid data")
    public void successUploadOfValidImage() {

        int petId = getRandomPetIdFromList();
        String additionalMetadata = TEST_METADATA;
        String filePath = TEST_IMAGE_FILEPATH;

        Response response = addPetImageApi.uploadPetImage(petId, additionalMetadata, filePath);

        Assertions.assertEquals(200, response.getStatusCode());
        String responseBody = response.getBody().asString();
        Assertions.assertTrue(responseBody.contains("Image uploaded successfully") || responseBody.contains("File uploaded to"));
    }

    @Test
    @Order(2)
    @DisplayName("Check that validation error returns when send Upload image request with .pdf format instead of image formats")
    public void validationErrorFileFormat() {

        int petId = getRandomPetIdFromList();
        String additionalMetadata = TEST_METADATA;
        String filePath = TEST_PDF_FILEPATH;

        Response response = addPetImageApi.uploadPetImage(petId, additionalMetadata, filePath);

        Assertions.assertEquals(422, response.getStatusCode());
        String responseBody = response.getBody().asString();
        Assertions.assertTrue(responseBody.contains("Validation error: Only image files are allowed."));
    }
}

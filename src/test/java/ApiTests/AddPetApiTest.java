package ApiTests;

import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.*;

import static ApiReusedMethods.PetMethods.AddPetApi.*;


@Feature("Add new pet") //Create
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class AddPetApiTest {

    @Test
    @Order(1)
    @DisplayName("Check that a pet with corresponded data created when send Add pet request with valid data")
    public void petCreationWithValidData() {

        int categoryId = generateRandomNumber(0, 1000);
        String categoryName = getRandomCategory();
        int petId = generateRandomNumber(0, 1000);
        String petName = getPetByCategory(categoryName);
        String petStatus = getRandomPetStatus();
        int tagId = getRandomTagId();
        String tagName = getRandomTagById(tagId);

        Response response = createPet(petId, petName, petStatus, categoryId, categoryName, tagId, tagName);
        String responseBody = response.getBody().asString();

        // Verify the pet is added successfully
        Assertions.assertEquals(200, response.getStatusCode());
        Assertions.assertTrue(responseBody.contains("\"name\":\"" + petName + "\""));
        Assertions.assertTrue(responseBody.contains("\"status\":\"" + petStatus + "\""));
    }

    @Test
    @Order(2)
    @DisplayName("Check that validation error returns when send Add pet request with unlisted pet's status")
    public void validationForUnlistedStatus() {
        int categoryId = generateRandomNumber(0, 1000);
        String categoryName = getRandomCategory();
        int petId = generateRandomNumber(0, 1000);
        String petName = getPetByCategory(categoryName);
        String petStatus = "This status is out of list";
        int tagId = getRandomTagId();
        String tagName = getRandomTagById(tagId);

        Response response = createPet(petId, petName, petStatus, categoryId, categoryName, tagId, tagName);
        String responseBody = response.getBody().asString();

        // Verify that validation error returns
        Assertions.assertEquals(422, response.getStatusCode());
        Assertions.assertEquals("{\"message\":\"Status is not included in the list\"}", responseBody);
    }
}

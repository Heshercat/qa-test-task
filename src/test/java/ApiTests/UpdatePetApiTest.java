package ApiTests;

import ApiReusedMethods.PetMethods.UpdatePetApi;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static ApiReusedMethods.ReusedMethods.*;

@Feature("Update pet") //Update
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UpdatePetApiTest {

    UpdatePetApi updatePetApi = new UpdatePetApi();

    @Test
    @Order(1)
    @DisplayName("Check that a pet successfully updates when send Update pet request with valid data")
    public void petUpdatedWithValidData() {

        int petId = 10001;
        String petName = getStringWithCurrentTime("TEST PET FOR ALFIIA");
        String petStatus = getRandomPetStatus();
        int categoryId = generateRandomNumber(1, 10);
        String categoryName = getRandomCategory();
        int tagId = getRandomTagId();
        String tagName = getRandomTagById(tagId);

        Response createdPetResponse = createTestPet();
        Response updatedPetResponse = updatePetApi.updatePet(petId, petName, petStatus, categoryId, categoryName, tagId, tagName);

        Assertions.assertEquals(200, updatedPetResponse.getStatusCode());
        Assertions.assertFalse(createdPetResponse.equals(updatedPetResponse));
    }

    @Test
    @Order(2)
    @DisplayName("Check that validation error returns when send Update pet request with invalid id")
    public void validationForInvalidId() {
        int petId = -10001;
        String petName = getStringWithCurrentTime("TEST PET FOR ALFIIA");
        String petStatus = getRandomPetStatus();
        int categoryId = generateRandomNumber(1, 10);
        String categoryName = getRandomCategory();
        int tagId = getRandomTagId();
        String tagName = getRandomTagById(tagId);

        Response response = updatePetApi.updatePet(petId, petName, petStatus, categoryId, categoryName, tagId, tagName);

        Assertions.assertEquals(400, response.getStatusCode());
    }
}

package ApiTests;

import ApiReusedMethods.PetMethods.FindPetApi;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static ApiReusedMethods.PetMethods.DeletePetApi.*;

@Feature("Delete pet")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DeletePetApiTest {

    FindPetApi findPetApi = new FindPetApi();

    @Test
    @Order(1)
    @DisplayName("Check that existing pet successfully deleted by id") //but it's better check it via DB
    public void successDeletingOfExistingPet() {
        int testPetId = getCreatedPetId();
        Response response = deletePetById(testPetId);
        findPetApi.getPetById(testPetId);

        Assertions.assertEquals(404, response.getStatusCode());
    }

    @Test
    @Order(2)
    @DisplayName("Check that validation error returns when send request with id which not exist")
    public void validationErrorNotExistingId() {
        int testPetId = getCreatedPetId();
        deletePetById(testPetId);
        Response response = deletePetById(testPetId);
        String responseBody = response.body().asString();
        deletePetById(testPetId);
        Assertions.assertEquals(404, response.getStatusCode());
    }
}

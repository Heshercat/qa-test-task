package ApiTests;

import ApiReusedMethods.PetMethods.FindPetApi;
import ApiReusedMethods.ReusedMethods;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static ApiReusedMethods.PetMethods.DeletePetApi.deletePetById;
import static ApiReusedMethods.ReusedMethods.getCreatedPetId;
import static ApiReusedMethods.ReusedMethods.getRandomPetIdFromList;


@Feature("Get pet") //Read
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FindPetApiTest {

    FindPetApi findPetApi = new FindPetApi();

    @Test
    @Order(1)
    @DisplayName("Check that all returned pets have corresponded status when send request with valid data")
    public void allStatusesMatch() {
        String status = ReusedMethods.getRandomPetStatus();
        Response response = findPetApi.findPetsByStatus(status);
        boolean statusesMatch = ReusedMethods.compareSearchStatuses(response, status);

        Assertions.assertEquals(200, response.getStatusCode());
        Assertions.assertTrue(statusesMatch);
    }

    @Test
    @Order(2)
    @DisplayName("Check that validation error returned when send request with unlisted status")
    public void validationForUnlistedStatus() {
        String status = "This status is out of list";
        Response response = findPetApi.findPetsByStatus(status);
        String responseBody = response.getBody().asString();

        Assertions.assertEquals(404, response.getStatusCode());
        Assertions.assertEquals("{\"message\":\"Status is not included in the list\"}", responseBody);

    }

    @Test
    @Order(3)
    @DisplayName("Check that pet with corresponded id returned when send request with valid data")
    public void petReturnsById() {
        int testPetId = getRandomPetIdFromList();
        Response response = findPetApi.getPetById(testPetId);
        String responseBody = response.getBody().asString();

        Assertions.assertEquals(200, response.getStatusCode());
        Assertions.assertTrue(responseBody.startsWith("{\"id\":" + testPetId));
    }

    @Test
    @Order(4)
    @DisplayName("Check that validation error returned when send request with unlisted id")
    public void validationForUnlistedId() {
        int testPetId = getCreatedPetId();
        deletePetById(testPetId);
        Response response = findPetApi.getPetById(testPetId);
        String responseBody = response.body().asString();
        findPetApi.getPetById(testPetId);

        Assertions.assertEquals(404, response.getStatusCode());
        Assertions.assertEquals("{\"code\":1,\"type\":\"error\",\"message\":\"Pet not found\"}", responseBody);
    }
}

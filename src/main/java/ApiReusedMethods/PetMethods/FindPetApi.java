package ApiReusedMethods.PetMethods;

import ApiReusedMethods.ReusedMethods;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static Core.ApiConfig.*;

public class FindPetApi extends ReusedMethods {
    public static Response findPetsByStatus(String status) {
        Response response = RestAssured.given()
                .queryParam("status", status)
                .get(BASE_URL + FIND_PET_BY_STATUS_ENDPOINT);
        return response;
    }

    public static Response getPetById(int petId) {
        Response response = RestAssured.given()
                .pathParam("petId", petId)
                .get(BASE_URL + PET_BY_ID);

        return response;
    }
}

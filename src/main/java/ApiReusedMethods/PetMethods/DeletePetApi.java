package ApiReusedMethods.PetMethods;

import ApiReusedMethods.ReusedMethods;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static Core.ApiConfig.*;

public class DeletePetApi  extends ReusedMethods {

    public static Response deletePetById(long petId) {

        Response response = RestAssured.given()
                .header("api_key", API_KEY_HEADER)
                .pathParam("petId", petId)
                .delete(BASE_URL + "/pet/{petId}");

        return response;
    }
}

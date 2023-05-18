package Core;



public class ApiConfig {
    public static final String BASE_URL = "https://petstore.swagger.io/v2";
    public static final String API_KEY_HEADER = "special-key";
    public static final String PET_ENDPOINT = "/pet";
    public static final String UPLOAD_IMAGE_ENDPOINT = PET_ENDPOINT + "/{petId}/uploadImage";
    public static final String FIND_PET_BY_STATUS_ENDPOINT = PET_ENDPOINT + "/findByStatus";
    public static final String PET_BY_ID = PET_ENDPOINT + "{petId}";
    public static final String TEST_IMAGE_FILEPATH = "src/main/resources/petTestImage.png";
    public static final String TEST_PDF_FILEPATH = "src/main/resources/simpleNotes.pdf";
    public static final String TEST_METADATA = "This is pet image";
}

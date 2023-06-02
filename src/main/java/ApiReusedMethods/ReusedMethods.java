package ApiReusedMethods;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;


import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static ApiReusedMethods.PetMethods.AddPetApi.createPet;
import static ApiReusedMethods.PetMethods.FindPetApi.findPetsByStatus;

public class ReusedMethods {

    //DATA
    private static final Random random = new Random();
    private static final Map<String, List<String>> animalCategories = new HashMap<>();
    private static final String[] PET_STATUSES = {"available", "pending", "sold"};

    static {
        animalCategories.put("Small", List.of("Rabbit", "Hamster", "Guinea Pig", "Mouse", "Ferret"));
        animalCategories.put("Medium", List.of("Dog", "Cat", "Monkey", "Fox", "Pig"));
        animalCategories.put("Large", List.of("Elephant", "Giraffe", "Horse", "Lion", "Tiger"));
    }

    private static final Map<Integer, String> tagMap = new HashMap<>();

    static {
        tagMap.put(1, "Red");
        tagMap.put(2, "Blue");
        tagMap.put(3, "Green");
        tagMap.put(4, "Yellow");
        tagMap.put(5, "Orange");
        tagMap.put(6, "Purple");
        tagMap.put(7, "Black");
        tagMap.put(8, "White");
        tagMap.put(9, "Brown");
        tagMap.put(10, "Gray");
    }

    //METHODS
    public static int generateRandomNumber(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    //TODO: rework categories logic - create mapping with ids like for tags
    public static String getRandomCategory() {
        List<String> categories = new ArrayList<>(animalCategories.keySet());
        int categoryIndex = random.nextInt(categories.size());
        return categories.get(categoryIndex);
    }

    public static String getPetByCategory(String randomCategory) {
        List<String> animals = animalCategories.get(randomCategory);
        int animalIndex = random.nextInt(animals.size());
        return animals.get(animalIndex);
    }

    public static String getRandomPetStatus() {
        int index = random.nextInt(PET_STATUSES.length);
        return PET_STATUSES[index];

    }

    public static int getRandomTagId() {
        int index = random.nextInt(tagMap.size()) + 1;
        return index;
    }

    public static String getRandomTagById(int tagId) {
        return tagMap.get(tagId);
    }

    //TODO: rework method to gather all petIds for each status and add it to common list
    public static List<Object> extractValidPetIds() {
        List<Object> idList = new ArrayList<>();
        Response response = findPetsByStatus(getRandomPetStatus());
        JsonPath jsonPath = response.jsonPath();

        List<Object> ids = jsonPath.getList("id");
        for (Object id : ids) {
            if (id instanceof Integer) {
                idList.add(id);
            } else if (id instanceof String) {
                idList.add(Long.parseLong((String) id));
            }
        }

        return idList;
    }

    public static int getRandomPetIdFromList() {
        List<Object> petIds = extractValidPetIds();
        if (!petIds.isEmpty()) {
            int randomIndex = new Random().nextInt(petIds.size());
            Object randomPetId = petIds.get(randomIndex);
            if (randomPetId instanceof Integer) {
                return (int) randomPetId;
            } else if (randomPetId instanceof Long) {
                return ((Long) randomPetId).intValue();
            }
        }
        throw new IllegalStateException("No valid pet IDs found.");
    }

    public static String getStringWithCurrentTime(String stringContext) {
        Instant now = Instant.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneOffset.UTC);
        return stringContext + "-" + formatter.format(now);
    }

    public static Response createTestPet() {
        int petId = 10001;
        String petName = getStringWithCurrentTime("TEST PET FOR ALFIIA");
        String petStatus = getRandomPetStatus();
        int categoryId = generateRandomNumber(1, 10);
        String categoryName = getRandomCategory();
        int tagId = getRandomTagId();
        String tagName = getRandomTagById(tagId);

        Response response = createPet(petId, petName, petStatus, categoryId, categoryName, tagId, tagName);
        System.out.println("Response body: " + response.body().asString());
        return response;
    }

    public static int getCreatedPetId() {

        Response response = createTestPet();
        int createdPetId = response.jsonPath().getInt("id");

        return createdPetId;
    }

    public static boolean compareSearchStatuses(Response response, String status) {
        JsonPath jsonPath = response.jsonPath();

        int itemCount = jsonPath.getList("$").size();
        for (int i = 0; i < itemCount; i++) {
            String itemStatus = jsonPath.getString("[" + i + "].status");
            if (itemStatus.equals(status)) {
                return true;
            }
        }
        return false;
    }
}

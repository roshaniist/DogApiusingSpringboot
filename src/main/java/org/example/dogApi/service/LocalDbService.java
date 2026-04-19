package org.example.dogApi.service;

import org.json.JSONObject;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class LocalDbService {
    private static final String BASE_URL = "https://dog.ceo/api/";


    private String fetchJson(String endpoint) {
        StringBuilder response = new StringBuilder();
        try {
            URL url = new URL(BASE_URL + endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(7000);
            conn.setReadTimeout(7000);

            int status = conn.getResponseCode();
            BufferedReader reader;
            if (status >= 200 && status < 300) {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            String line;
            while ((line = reader.readLine()) != null) response.append(line);
            reader.close();
            conn.disconnect();
        } catch (Exception e) {
            System.out.println(" Error fetching API: " + e.getMessage());
        }
        return response.toString();
    }


    public void viewAllBreeds() {
        try {
            JSONObject json = new JSONObject(fetchJson("breeds/list/all"));
            JSONObject message = json.getJSONObject("message");
            ArrayList<String> breeds = new ArrayList<>(message.keySet());
            System.out.println("\n========= ALL BREEDS =========");
            System.out.println(breeds);
            System.out.println("==============================\n");
        } catch (Exception e) {
            System.out.println("Error fetching breeds: " + e.getMessage());
        }
    }

    public void viewAllSubBreeds() {
        try {
            JSONObject json = new JSONObject(fetchJson("breeds/list/all"));
            JSONObject message = json.getJSONObject("message");
            boolean any = false;
            System.out.println("\n========= ALL SUB-BREEDS =========");
            for (String breed : message.keySet()) {
                JSONArray arr = message.getJSONArray(breed);
                if (arr.length() > 0) {
                    any = true;
                    System.out.println(breed + " -> " + arr.toString());
                }
            }
            if (!any) System.out.println("No sub-breeds stored.");
            System.out.println("==================================\n");
        } catch (Exception e) {
            System.out.println("Error fetching sub-breeds: " + e.getMessage());
        }
    }


    public void viewSubBreedsOfBreed(String breed) {
        if (breed == null || breed.trim().isEmpty()) {
            System.out.println("Please enter a valid breed name.");
            return;
        }
        breed = breed.trim().toLowerCase();
        try {
            JSONObject json = new JSONObject(fetchJson("breed/" + breed + "/list"));
            JSONArray arr = json.getJSONArray("message");

            System.out.println("\n Sub-Breeds of " + breed + ":");
            if (arr.length() == 0) {
                System.out.println("No sub-breeds found.");
            } else {
                System.out.println(arr.toString());
            }
            System.out.println();
        } catch (Exception e) {
            System.out.println("Error fetching sub-breeds for '" + breed + "': " + e.getMessage());
        }
    }


    public void viewAllImages() {
        try {
            JSONObject json = new JSONObject(fetchJson("breeds/list/all"));
            JSONObject message = json.getJSONObject("message");
            System.out.println("\n========= RANDOM IMAGE PER BREED =========");
            for (String breed : message.keySet()) {
                // skip extremely rare calls if needed, but default: try to fetch one random image per breed
                try {
                    JSONObject imgJson = new JSONObject(fetchJson("breed/" + breed + "/images/random"));
                    if (imgJson.has("status") && "success".equalsIgnoreCase(imgJson.optString("status"))) {
                        System.out.println(breed + " -> " + imgJson.getString("message"));
                    } else {
                        System.out.println(breed + " -> (no image found)");
                    }
                } catch (Exception inner) {
                    System.out.println(breed + " -> (error fetching image: " + inner.getMessage() + ")");
                }
            }
            System.out.println("==========================================\n");
        } catch (Exception e) {
            System.out.println("Error fetching breeds for images: " + e.getMessage());
        }
    }


    public void viewImagesOfBreed(String breed) {
        if (breed == null || breed.trim().isEmpty()) {
            System.out.println("Please enter a valid breed name.");
            return;
        }
        breed = breed.trim().toLowerCase();
        try {
            JSONObject json = new JSONObject(fetchJson("breed/" + breed + "/images"));
            JSONArray arr = json.getJSONArray("message");
            if (arr.length() == 0) {
                System.out.println("No images found for breed: " + breed);
                return;
            }
            System.out.println("\nImages of " + breed + " (showing up to 20):");
            int limit = Math.min(arr.length(), 20);
            for (int i = 0; i < limit; i++) {
                System.out.println((i + 1) + ") " + arr.getString(i));
            }
            System.out.println("Total images available: " + arr.length() + "\n");
        } catch (Exception e) {
            System.out.println("Error fetching images for breed '" + breed + "': " + e.getMessage());
        }
    }


    public void viewImagesOfSubBreed(String breed, String subBreed) {
        if (breed == null || subBreed == null || breed.trim().isEmpty() || subBreed.trim().isEmpty()) {
            System.out.println("Please enter valid breed and sub-breed names.");
            return;
        }
        breed = breed.trim().toLowerCase();
        subBreed = subBreed.trim().toLowerCase();
        try {
            JSONObject json = new JSONObject(fetchJson("breed/" + breed + "/" + subBreed + "/images"));
            JSONArray arr = json.getJSONArray("message");
            if (arr.length() == 0) {
                System.out.println("No images found for sub-breed: " + subBreed + " of breed: " + breed);
                return;
            }
            System.out.println("\nImages of " + subBreed + " (" + breed + ") (showing up to 20):");
            int limit = Math.min(arr.length(), 20);
            for (int i = 0; i < limit; i++) {
                System.out.println((i + 1) + ") " + arr.getString(i));
            }
            System.out.println("Total images available: " + arr.length() + "\n");
        } catch (Exception e) {
            System.out.println("Error fetching images for sub-breed '" + subBreed + "': " + e.getMessage());
        }
    }


    public void countStatistics() {
        try {
            JSONObject json = new JSONObject(fetchJson("breeds/list/all"));
            JSONObject message = json.getJSONObject("message");

            int breedCount = message.keySet().size();
            int subBreedCount = 0;
            for (String breed : message.keySet()) {
                JSONArray subs = message.getJSONArray(breed);
                subBreedCount += subs.length();
            }

            System.out.println("\n========= COUNT STATISTICS =========");
            System.out.println("Total breeds: " + breedCount);
            System.out.println("Total sub-breed entries: " + subBreedCount);

            int breedsWithImage = 0;
            for (String breed : message.keySet()) {
                try {
                    JSONObject imgResp = new JSONObject(fetchJson("breed/" + breed + "/images/random"));
                    if (imgResp.has("status") && "success".equalsIgnoreCase(imgResp.optString("status"))) {
                        breedsWithImage++;
                    }
                } catch (Exception ignored) {}
            }
            System.out.println("Breeds that returned at least one random image: " + breedsWithImage);
            System.out.println("====================================\n");
        } catch (Exception e) {
            System.out.println("Error calculating statistics: " + e.getMessage());
        }
    }
}

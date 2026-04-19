package org.example.dogApi.service;

import org.json.JSONObject;
import org.json.JSONArray;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DogApiService {
    private static final String BASE_URL = "https://dog.ceo/api/";

    private String getJsonResponse(String endpoint) {
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
            System.out.println("Error fetching API: " + e.getMessage());
        }
        return response.toString();
    }

    public void listAllBreeds() {
        try {
            JSONObject json = new JSONObject(getJsonResponse("breeds/list/all"));
            JSONObject message = json.getJSONObject("message");

            List<String> breeds = new ArrayList<>(message.keySet());
            System.out.println("\n All Breeds:");
            System.out.println(breeds.toString());
        } catch (Exception e) {
            System.out.println("Error parsing breed list: " + e.getMessage());
        }
    }

    public void listAllSubBreeds() {
        try {
            JSONObject json = new JSONObject(getJsonResponse("breeds/list/all"));
            JSONObject message = json.getJSONObject("message");
            System.out.println("\n All Sub-Breeds (breed -> [sub-breeds]):");
            int total = 0;
            for (String breed : message.keySet()) {
                JSONArray subs = message.getJSONArray(breed);
                if (subs.length() > 0) {
                    System.out.println(breed + " -> " + subs.toString());
                    total += subs.length();
                }
            }
            System.out.println("Total sub-breed entries shown: " + total);
        } catch (Exception e) {
            System.out.println("Error fetching sub-breeds: " + e.getMessage());
        }
    }

    public void listSubBreedsOfBreed(String breed) {
        if (breed == null || breed.trim().isEmpty()) {
            System.out.println("Please enter a valid breed name.");
            return;
        }
        breed = breed.trim().toLowerCase();
        try {
            JSONObject json = new JSONObject(getJsonResponse("breed/" + breed + "/list"));
            JSONArray arr = json.getJSONArray("message");

            if (arr.length() == 0) {
                System.out.println("\n " + breed + " has no sub-breeds.");
                return;
            }

            System.out.println("\n Sub-Breeds of " + breed + ": " + arr.toString());
        } catch (Exception e) {
            System.out.println("Error fetching sub-breeds for '" + breed + "': " + e.getMessage());
        }
    }


    public void randomImage() {
        try {
            JSONObject json = new JSONObject(getJsonResponse("breeds/image/random"));
            System.out.println("\n Random Dog Image: " + json.getString("message"));
        } catch (Exception e) {
            System.out.println("Error fetching random image: " + e.getMessage());
        }
    }


    public void imageOfBreed(String breed) {
        if (breed == null || breed.trim().isEmpty()) {
            System.out.println("Please enter a valid breed name.");
            return;
        }
        breed = breed.trim().toLowerCase();
        try {
            JSONObject json = new JSONObject(getJsonResponse("breed/" + breed + "/images/random"));
            if (json.has("status") && "success".equalsIgnoreCase(json.optString("status"))) {
                System.out.println("\n Image of " + breed + ": " + json.getString("message"));
            } else {
                System.out.println("No image found for breed: " + breed);
            }
        } catch (Exception e) {
            System.out.println("Error fetching image for breed '" + breed + "': " + e.getMessage());
        }
    }


    public void multipleImagesOfBreed(String breed, int count) {
        if (breed == null || breed.trim().isEmpty() || count <= 0) {
            System.out.println("Please enter a valid breed and a positive count.");
            return;
        }
        breed = breed.trim().toLowerCase();
        try {
            JSONObject json = new JSONObject(getJsonResponse("breed/" + breed + "/images/random/" + count));
            JSONArray arr = json.getJSONArray("message");
            System.out.println("\n " + arr.length() + " Images of " + breed + ":");
            for (int i = 0; i < arr.length(); i++) {
                System.out.println((i + 1) + ") " + arr.getString(i));
            }
        } catch (Exception e) {
            System.out.println("Error fetching multiple images for breed '" + breed + "': " + e.getMessage());
        }
    }


    public void imageOfSubBreed(String breed, String subBreed) {
        if (breed == null || subBreed == null || breed.isBlank() || subBreed.isBlank()) {
            System.out.println("Please enter valid breed and sub-breed names.");
            return;
        }
        breed = breed.trim().toLowerCase();
        subBreed = subBreed.trim().toLowerCase();
        try {
            JSONObject json = new JSONObject(getJsonResponse("breed/" + breed + "/" + subBreed + "/images/random"));
            if (json.has("status") && "success".equalsIgnoreCase(json.optString("status"))) {
                System.out.println("\n Image of " + subBreed + " (" + breed + "): " + json.getString("message"));
            } else {
                System.out.println("No image found for sub-breed: " + subBreed + " of breed: " + breed);
            }
        } catch (Exception e) {
            System.out.println("Error fetching image for sub-breed '" + subBreed + "': " + e.getMessage());
        }
    }

    public void multipleImagesOfSubBreed(String breed, String subBreed, int count) {
        if (breed == null || subBreed == null || breed.isBlank() || subBreed.isBlank() || count <= 0) {
            System.out.println("Please enter valid breed, sub-breed and count.");
            return;
        }
        breed = breed.trim().toLowerCase();
        subBreed = subBreed.trim().toLowerCase();
        try {
            JSONObject json = new JSONObject(getJsonResponse("breed/" + breed + "/" + subBreed + "/images/random/" + count));
            JSONArray arr = json.getJSONArray("message");
            System.out.println("\n " + arr.length() + " Images of " + subBreed + " (" + breed + "):");
            for (int i = 0; i < arr.length(); i++) {
                System.out.println((i + 1) + ") " + arr.getString(i));
            }
        } catch (Exception e) {
            System.out.println("Error fetching multiple images for sub-breed '" + subBreed + "': " + e.getMessage());
        }
    }
}

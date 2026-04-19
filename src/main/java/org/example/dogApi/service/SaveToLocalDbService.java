package org.example.dogApi.service;

import org.example.dogApi.util.DBConnection;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SaveToLocalDbService {

    private static final String BASE_URL = "https://dog.ceo/api/";


    private String getJsonResponse(String endpoint) {
        StringBuilder response = new StringBuilder();
        try {
            URL url = new URL(BASE_URL + endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) response.append(line);
            reader.close();
        } catch (Exception e) {
            System.out.println("API error: " + e.getMessage());
        }
        return response.toString();
    }

    //  Save all breeds
    public void saveListOfBreeds() {
        try (Connection conn = DBConnection.getConnection()) {
            JSONObject json = new JSONObject(getJsonResponse("breeds/list/all"));
            JSONObject message = json.getJSONObject("message");

            PreparedStatement ps = conn.prepareStatement(
                    "INSERT IGNORE INTO breeds(name) VALUES(?)"
            );

            for (String breed : message.keySet()) {
                ps.setString(1, breed);
                ps.executeUpdate();
            }
            System.out.println("All breeds saved to local DB!");
        } catch (Exception e) {
            System.out.println("Error saving breeds: " + e.getMessage());
        }
    }

    // Save all sub-breeds
    public void saveListOfSubBreeds() {
        try (Connection conn = DBConnection.getConnection()) {
            JSONObject json = new JSONObject(getJsonResponse("breeds/list/all"));
            JSONObject message = json.getJSONObject("message");

            PreparedStatement ps = conn.prepareStatement(
                    "INSERT IGNORE INTO subbreeds(breed_name, subbreed_name) VALUES(?, ?)"
            );

            int count = 0;
            for (String breed : message.keySet()) {
                JSONArray subs = message.getJSONArray(breed);
                for (int i = 0; i < subs.length(); i++) {
                    ps.setString(1, breed);
                    ps.setString(2, subs.getString(i));
                    ps.executeUpdate();
                    count++;
                }
            }
            System.out.println(count + " sub-breeds saved!");
        } catch (Exception e) {
            System.out.println("Error saving sub-breeds: " + e.getMessage());
        }
    }

    // Save sub-breeds of a specific breed
    public void saveSubBreedsOfBreed(String breed) {
        try (Connection conn = DBConnection.getConnection()) {
            JSONObject json = new JSONObject(getJsonResponse("breed/" + breed + "/list"));
            JSONArray subs = json.getJSONArray("message");

            PreparedStatement ps = conn.prepareStatement(
                    "INSERT IGNORE INTO subbreeds(breed_name, subbreed_name) VALUES(?, ?)"
            );

            for (int i = 0; i < subs.length(); i++) {
                ps.setString(1, breed);
                ps.setString(2, subs.getString(i));
                ps.executeUpdate();
            }
            System.out.println("Sub-breeds of " + breed + " saved!");
        } catch (Exception e) {
            System.out.println("Error saving sub-breeds: " + e.getMessage());
        }
    }

    // Save random image
    public void saveRandomImage() {
        try (Connection conn = DBConnection.getConnection()) {
            JSONObject json = new JSONObject(getJsonResponse("breeds/image/random"));
            String imageUrl = json.getString("message");

            PreparedStatement ps = conn.prepareStatement(
                    "INSERT IGNORE INTO images(image_url) VALUES(?)"
            );
            ps.setString(1, imageUrl);
            ps.executeUpdate();

            System.out.println("Random image saved: " + imageUrl);
        } catch (Exception e) {
            System.out.println("Error saving image: " + e.getMessage());
        }
    }

    // Save image of a specific breed
    public void saveImageOfBreed(String breed) {
        try (Connection conn = DBConnection.getConnection()) {
            JSONObject json = new JSONObject(getJsonResponse("breed/" + breed + "/images/random"));
            String imageUrl = json.getString("message");

            PreparedStatement ps = conn.prepareStatement(
                    "INSERT IGNORE INTO images(breed_name, image_url) VALUES(?, ?)"
            );
            ps.setString(1, breed);
            ps.setString(2, imageUrl);
            ps.executeUpdate();

            System.out.println("Image of " + breed + " saved!");
        } catch (Exception e) {
            System.out.println("Error saving breed image: " + e.getMessage());
        }
    }

    // Save multiple images of a breed
    public void saveMultipleImagesOfBreed(String breed, int count) {
        try (Connection conn = DBConnection.getConnection()) {
            JSONObject json = new JSONObject(getJsonResponse("breed/" + breed + "/images/random/" + count));
            JSONArray images = json.getJSONArray("message");

            PreparedStatement ps = conn.prepareStatement(
                    "INSERT IGNORE INTO images(breed_name, image_url) VALUES(?, ?)"
            );

            for (int i = 0; i < images.length(); i++) {
                ps.setString(1, breed);
                ps.setString(2, images.getString(i));
                ps.executeUpdate();
            }

            System.out.println(count + " images of " + breed + " saved!");
        } catch (Exception e) {
            System.out.println("Error saving images: " + e.getMessage());
        }
    }

    // Save image of a sub-breed
    public void saveImageOfSubBreed(String breed, String sub) {
        try (Connection conn = DBConnection.getConnection()) {
            JSONObject json = new JSONObject(getJsonResponse("breed/" + breed + "/" + sub + "/images/random"));
            String imageUrl = json.getString("message");

            PreparedStatement ps = conn.prepareStatement(
                    "INSERT IGNORE INTO images(breed_name, subbreed_name, image_url) VALUES(?, ?, ?)"
            );
            ps.setString(1, breed);
            ps.setString(2, sub);
            ps.setString(3, imageUrl);
            ps.executeUpdate();

            System.out.println("Image of " + sub + " (" + breed + ") saved!");
        } catch (Exception e) {
            System.out.println("Error saving sub-breed image: " + e.getMessage());
        }
    }

    // Save multiple images of a sub-breed
    public void saveMultipleImagesOfSubBreed(String breed, String sub, int count) {
        try (Connection conn = DBConnection.getConnection()) {
            JSONObject json = new JSONObject(getJsonResponse("breed/" + breed + "/" + sub + "/images/random/" + count));
            JSONArray images = json.getJSONArray("message");

            PreparedStatement ps = conn.prepareStatement(
                    "INSERT IGNORE INTO images(breed_name, subbreed_name, image_url) VALUES(?, ?, ?)"
            );

            for (int i = 0; i < images.length(); i++) {
                ps.setString(1, breed);
                ps.setString(2, sub);
                ps.setString(3, images.getString(i));
                ps.executeUpdate();
            }

            System.out.println(count + " images of " + sub + " (" + breed + ") saved!");
        } catch (Exception e) {
            System.out.println("Error saving multiple sub-breed images: " + e.getMessage());
        }
    }
}

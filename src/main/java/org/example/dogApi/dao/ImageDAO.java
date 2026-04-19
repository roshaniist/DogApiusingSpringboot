package org.example.dogApi.dao;

import org.example.dogApi.model.Image;
import org.example.dogApi.util.DBConnection;

import java.sql.*;

public class ImageDAO {

    public void save(Image image) {
        String sql = "INSERT INTO images (url, breed_id, sub_breed_id) " +
                "SELECT ?, ?, ? FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM images WHERE url = ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, image.getUrl());
            if (image.getBreedId() == 0) ps.setNull(2, Types.INTEGER);
            else ps.setInt(2, image.getBreedId());
            if (image.getSubBreedId() == 0) ps.setNull(3, Types.INTEGER);
            else ps.setInt(3, image.getSubBreedId());
            ps.setString(4, image.getUrl());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printAllImages() {
        String sql = "SELECT url FROM images ORDER BY id DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            System.out.println("\n========= ALL IMAGES =========");
            int count = 0;
            while (rs.next()) {
                count++;
                System.out.println(count + ") " + rs.getString("url"));
            }
            if (count == 0) System.out.println("No images stored.");
            System.out.println("==============================\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printImagesByBreedName(String breedName) {
        String sql = "SELECT i.url FROM images i JOIN breeds b ON i.breed_id = b.id WHERE b.name = ? ORDER BY i.id DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, breedName);
            try (ResultSet rs = ps.executeQuery()) {
                System.out.println("\nImages of breed: " + breedName);
                int count = 0;
                while (rs.next()) {
                    count++;
                    System.out.println(count + ") " + rs.getString("url"));
                }
                if (count == 0) System.out.println("No images found for this breed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printImagesBySubBreedName(String breedName, String subBreedName) {
        String sql = "SELECT i.url FROM images i " +
                "JOIN breeds b ON i.breed_id = b.id " +
                "JOIN sub_breeds sb ON i.sub_breed_id = sb.id " +
                "WHERE b.name = ? AND sb.name = ? ORDER BY i.id DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, breedName);
            ps.setString(2, subBreedName);
            try (ResultSet rs = ps.executeQuery()) {
                System.out.println("\nImages of sub-breed: " + subBreedName + " (" + breedName + ")");
                int count = 0;
                while (rs.next()) {
                    count++;
                    System.out.println(count + ") " + rs.getString("url"));
                }
                if (count == 0) System.out.println("No images found for this sub-breed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int countImages() {
        String sql = "SELECT COUNT(*) FROM images";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}

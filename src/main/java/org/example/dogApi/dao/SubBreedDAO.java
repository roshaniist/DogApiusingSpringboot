package org.example.dogApi.dao;

import org.example.dogApi.model.SubBreed;
import org.example.dogApi.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubBreedDAO {

    public void save(SubBreed subBreed) {
        String sql = "INSERT INTO sub_breeds (name, breed_id) SELECT ?, ? FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM sub_breeds WHERE name = ? AND breed_id = ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, subBreed.getName());
            ps.setInt(2, subBreed.getBreedId());
            ps.setString(3, subBreed.getName());
            ps.setInt(4, subBreed.getBreedId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getAllSubBreeds() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT CONCAT(b.name, ' → ', sb.name) AS full_name FROM sub_breeds sb JOIN breeds b ON sb.breed_id = b.id ORDER BY b.name, sb.name";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(rs.getString("full_name"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void printAllSubBreeds() {
        List<String> subs = getAllSubBreeds();
        System.out.println("\n========= ALL SUB-BREEDS =========");
        if (subs.isEmpty()) System.out.println("No sub-breeds stored.");
        else System.out.println(subs);
        System.out.println("==================================\n");
    }

    public void printSubBreedsByBreedName(String breedName) {
        String sql = "SELECT sb.name FROM sub_breeds sb JOIN breeds b ON sb.breed_id = b.id WHERE b.name = ? ORDER BY sb.name";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, breedName);
            try (ResultSet rs = ps.executeQuery()) {
                List<String> subs = new ArrayList<>();
                while (rs.next()) subs.add(rs.getString("name"));
                System.out.println("\n📋 Sub-Breeds of " + breedName + ":");
                System.out.println(subs.isEmpty() ? "No sub-breeds found." : subs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int countSubBreeds() {
        String sql = "SELECT COUNT(*) FROM sub_breeds";
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

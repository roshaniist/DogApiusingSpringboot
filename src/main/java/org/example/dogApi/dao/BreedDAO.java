package org.example.dogApi.dao;

import org.example.dogApi.model.Breed;
import org.example.dogApi.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BreedDAO {

    public void save(Breed breed) {
        String sql = "INSERT INTO breeds (name) SELECT ? FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM breeds WHERE name = ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, breed.getName());
            ps.setString(2, breed.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Breed> getAllBreeds() {
        List<Breed> breeds = new ArrayList<>();
        String sql = "SELECT id, name FROM breeds ORDER BY name";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Breed b = new Breed();
                b.setId(rs.getInt("id"));
                b.setName(rs.getString("name"));
                breeds.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return breeds;
    }

    public void printAllBreeds() {
        List<Breed> list = getAllBreeds();
        System.out.println("\n========= ALL BREEDS =========");
        if (list.isEmpty()) {
            System.out.println("No breeds stored.");
        } else {
            System.out.println(list.stream().map(Breed::getName).toList());
        }
        System.out.println("==============================\n");
    }

    public int countBreeds() {
        String sql = "SELECT COUNT(*) FROM breeds";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Integer getIdByName(String breedName) {
        String sql = "SELECT id FROM breeds WHERE name = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, breedName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

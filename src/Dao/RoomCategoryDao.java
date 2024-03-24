package Dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Model.RoomCategory;

public class RoomCategoryDao {
    private Connection connection;

    public RoomCategoryDao() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hoteldb", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addRoomCategory(String categoryName) {
        String query = "INSERT INTO category (name) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, categoryName);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void updateRoomCategory(int categoryId, String categoryName) {
        String query = "UPDATE category SET name = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, categoryName);
            statement.setInt(2, categoryId);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteRoomCategory(int categoryId) {
        String query = "DELETE FROM category WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, categoryId);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<RoomCategory> getAllRoomCategories() {
        List<RoomCategory> roomCategories = new ArrayList<>();
        String query = "SELECT * FROM category";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hoteldb", "root", "");
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                RoomCategory roomCategory = new RoomCategory(id, name);
                roomCategories.add(roomCategory);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return roomCategories;
    }
}
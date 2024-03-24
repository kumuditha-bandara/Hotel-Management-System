package Dao;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Model.Room;

public class RoomDao {
    private Connection connection;

    public RoomDao() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hoteldb", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();
        String query = "SELECT * FROM room";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String roomNumber = resultSet.getString("roomNumber");
                int categoryID = resultSet.getInt("categoryId");
                double price = resultSet.getDouble("price");
                Room room = new Room(id, roomNumber, categoryID, price);
                rooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    public void addRoom(Room room) {
        String query = "INSERT INTO room (roomNumber, categoryId, price) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, room.getRoomNumber());
            statement.setInt(2, room.getCategoryID());
            statement.setDouble(3, room.getPrice());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public double getRoomPrice(String roomId) throws SQLException {
        String query = "SELECT price FROM room WHERE roomNumber=?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, roomId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble("price");
            } else {
                return -1;
            }
        }
    }

    public void updateRoom(Room room) {
        String query = "UPDATE room SET roomNumber=?, categoryId=?, price=? WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, room.getRoomNumber());
            statement.setInt(2, room.getCategoryID());
            statement.setDouble(3, room.getPrice());
            statement.setInt(4, room.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteRoom(int roomId) {
        String query = "DELETE FROM room WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, roomId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}



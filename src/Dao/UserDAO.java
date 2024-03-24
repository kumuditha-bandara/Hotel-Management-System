package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.User;

public class UserDAO {
    private Connection connection;

    public UserDAO() {
    	   try {
              
               connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hoteldb", "root", "");
           } catch (SQLException e) {
               e.printStackTrace();
           }
    }

    public User getUserByUsername(String username) {
        User user = null;
        String query = "SELECT username, password, role FROM user WHERE username=?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String fetchedUsername = resultSet.getString("username");
                String password = resultSet.getString("password");
                String role = resultSet.getString("role");
                user = new User(fetchedUsername, password, role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}

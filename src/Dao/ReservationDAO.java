package Dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.JOptionPane;

import Model.Reservation;
import Model.Room;
import Model.SessionManager;

public class ReservationDAO {
    private Connection connection;

    public ReservationDAO() {
       
        try {
          
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hoteldb", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Reservation> getAllReservations() {
        List<Reservation> reservations = new ArrayList<>();
        String currentUser = SessionManager.getInstance().getUsername();
        String currentUserRole = SessionManager.getInstance().getRole();
        if("user".equals(currentUserRole)) {
        	 String query = "SELECT * FROM reservation WHERE customerName='"+ currentUser +"'";
        	  try ( Statement statement = connection.createStatement();
        			  ResultSet resultSet = statement.executeQuery(query)) {
        	            while (resultSet.next()) {
        	                int id = resultSet.getInt("id");
        	                String roomId = resultSet.getString("roomId");
        	                String customerName = resultSet.getString("customerName");
        	                String packageType = resultSet.getString("packageType");
        	                double amount = resultSet.getDouble("amount");
        	                Date reservationDate = resultSet.getDate("reservationDate");
        	                Reservation reservation = new Reservation(id, roomId, customerName, packageType, amount, reservationDate);
        	                reservations.add(reservation);
        	            }
        	        } catch (SQLException e) {
        	            e.printStackTrace();
        	        }
        }else {
        	 String query = "SELECT * FROM reservation";
        	  try (Statement statement = connection.createStatement();
     	             ResultSet resultSet = statement.executeQuery(query)) {
     	            while (resultSet.next()) {
     	                int id = resultSet.getInt("id");
     	                String roomId = resultSet.getString("roomId");
     	                String customerName = resultSet.getString("customerName");
     	                String packageType = resultSet.getString("packageType");
     	                double amount = resultSet.getDouble("amount");
     	                Date reservationDate = resultSet.getDate("reservationDate");
     	                Reservation reservation = new Reservation(id, roomId, customerName, packageType, amount, reservationDate);
     	                reservations.add(reservation);
     	            }
     	        } catch (SQLException e) {
     	            e.printStackTrace();
     	        }
        }
       
      
        return reservations;
    }
    
    

    public void addReservation(Reservation reservation) {
        String query = "INSERT INTO reservation (roomId, customerName, packageType, amount, reservationDate) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, reservation.getRoomId());
            statement.setString(2, reservation.getCustomerName());
            statement.setString(3, reservation.getPackageType());
            statement.setDouble(4, reservation.getAmount());
            statement.setDate(5, reservation.getReservationDate());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateReservation(Reservation reservation) {
        String query = "UPDATE reservation SET roomId=?, customerName=?, packageType=?, amount=?, reservationDate=? WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, reservation.getRoomId());
            statement.setString(2, reservation.getCustomerName());
            statement.setString(3, reservation.getPackageType());
            statement.setDouble(4, reservation.getAmount());
            statement.setDate(5, reservation.getReservationDate());
            statement.setInt(6, reservation.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public double calculateReservationAmount(double price, String packageType) {
        double basePrice = price; 
        double packagePrice = 0.0;

        
        if (packageType.equalsIgnoreCase("Full Board")) {
            packagePrice = 50.0; 
        } else if (packageType.equalsIgnoreCase("Half Board")) {
            packagePrice = 30.0; 
        } else if (packageType.equalsIgnoreCase("Bed and Breakfast")) {
            packagePrice = 20.0; 
        }

       
        return basePrice + packagePrice;
    }

  
  
    public void cancelReservation(int reservationId) {
        String selectQuery = "SELECT reservationDate FROM reservation WHERE id=?";
        String deleteQuery = "DELETE FROM reservation WHERE id=?";
        String currentUserRole = SessionManager.getInstance().getRole();
        try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
             PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
            
            selectStatement.setInt(1, reservationId);
            ResultSet resultSet = selectStatement.executeQuery();
            
            if (resultSet.next()) {
                Date reservationDate = resultSet.getDate("reservationDate");
                if("user".equals(currentUserRole)) {
                	  Calendar calendar = Calendar.getInstance();
                      calendar.setTime(reservationDate);
                      calendar.add(Calendar.HOUR, 24);
                      Date currentDate = new Date(reservationId);
                      
                      if (currentDate.before(calendar.getTime())) {
                     
                          deleteStatement.setInt(1, reservationId);
                          deleteStatement.executeUpdate();
                      } else {
                    	  JOptionPane.showInputDialog(this, "Please select a reservation to cancel.");
                        
                      }
                }else {
                	  deleteStatement.setInt(1, reservationId);
                      deleteStatement.executeUpdate();
                }
               
              
            } else {
                System.out.println("Reservation not found.");
              
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

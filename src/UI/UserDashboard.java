// UserDashboard.java
package UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class UserDashboard extends JFrame {
    public UserDashboard() {
        setTitle("User Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add button for reservation CRUD functionality
        // Customize layout and components as needed
        // Example:
        JPanel panel = new JPanel();
        JButton reservationButton = new JButton("Manage Reservations");
        panel.add(reservationButton);
        add(panel);

        reservationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openReservationUI(); // Open ReservationUI when the button is clicked
            }
        });
        setVisible(true);
    }
    
    private void openReservationUI() {
        // Instantiate and display the ReservationUI
        new ReservationUI().setVisible(true);
    }
    
    public static void main(String[] args) {
        // Run the UserDashboard
      
    	new UserDashboard();
    }

}

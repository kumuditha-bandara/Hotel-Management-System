package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminDashboard extends JFrame {
    public AdminDashboard() {
        setTitle("Hotel Management System Admin Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        buttonPanel.setBackground(Color.WHITE);

        JButton roomButton = createButton("Rooms", Color.decode("#4CAF50")); 
        JButton categoryButton = createButton("Categories", Color.decode("#2196F3")); 
        JButton reservationButton = createButton("Reservations", Color.decode("#FFC107")); 
        JButton customersButton = createButton("Customers", Color.decode("#FF5722")); 

    
        buttonPanel.add(roomButton);
        buttonPanel.add(categoryButton);
        buttonPanel.add(reservationButton);
        buttonPanel.add(customersButton);

       
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

      
        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
    }


    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(150, 50)); 
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 16)); 
        button.setFocusPainted(false);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
                openManagementUI(text);
            }
        });
        return button;
    }


    private void openManagementUI(String buttonText) {
        switch (buttonText) {
            case "Rooms":
                new RoomUI(); 
                break;
            case "Categories":
                new RoomCategoryUI(); 
                break;
            case "Reservations":
                new ReservationUI(); 
                break;
            case "Customers":
                new CustomerUI(); 
                break;
            default:
                break;
        }
    }

    public static void main(String[] args) {
        new AdminDashboard();
    }
}

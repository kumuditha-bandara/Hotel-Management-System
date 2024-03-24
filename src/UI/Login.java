// LoginForm.java
package UI;

import Dao.UserDAO;
import Model.SessionManager;
import Model.User;

import javax.swing.*;

import Controller.UserController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField roleField;
    private JButton loginButton;

    private UserDAO userDAO;
    private UserController userController;

    public Login(UserDAO userDAO) {
        this.userDAO = new UserDAO();
        this.userController = new UserController();

        setTitle("Login Form");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(30, 30, 80, 25);
        add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(110, 30, 160, 25);
        add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(30, 70, 80, 25);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(110, 70, 160, 25);
        add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setBounds(110, 110, 80, 25);
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                authenticateUser();
            }
        });
        add(loginButton);
    }

    private void authenticateUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        User user = userDAO.getUserByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
        	SessionManager.getInstance().setUser(user.getUsername(),user.getRole());

            String userRole = user.getRole();
            if ("admin".equals(userRole)) {
                // Redirect to admin dashboard
                new AdminDashboard();
            } else {
                // Redirect to user dashboard
                new UserDashboard();
            }
            dispose(); 
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password");
        }
    }

    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();
        Login loginForm = new Login(userDAO);
        loginForm.setVisible(true);
    }
}

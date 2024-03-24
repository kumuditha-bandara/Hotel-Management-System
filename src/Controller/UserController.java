package Controller;

import Dao.UserDAO;
import Model.User;

//UserController.java
public class UserController {
 private UserDAO userDAO;

 public UserController() {
     this.userDAO = new UserDAO();
 }

 public boolean authenticate(String username, String password) {
     User user = userDAO.getUserByUsername(username);
     return user != null && user.getPassword().equals(password);
 }

 public String getUserRole(String username) {
     User user = userDAO.getUserByUsername(username);
     return user != null ? user.getRole() : null;
 }
}

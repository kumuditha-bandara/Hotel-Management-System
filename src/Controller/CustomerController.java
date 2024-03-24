package Controller;

//CustomerController.java (Business Logic Layer)
import java.util.List;

import Dao.CustomerDAO;
import Model.Customer;

public class CustomerController {
 private CustomerDAO customerDAO;

 public CustomerController() {
     this.customerDAO = new CustomerDAO();
 }

 public List<Customer> getAllCustomers() {
     return customerDAO.getAllCustomers();
 }

 public void addCustomer(Customer customer) {
     customerDAO.addCustomer(customer);
 }

 public void updateCustomer(Customer customer) {
     customerDAO.updateCustomer(customer);
 }

 public void deleteCustomer(int customerId) {
     customerDAO.deleteCustomer(customerId);
 }
}


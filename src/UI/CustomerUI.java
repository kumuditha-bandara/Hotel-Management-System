package UI;

import Dao.CustomerDAO;
import Model.Customer;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import Controller.CustomerController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CustomerUI extends JFrame {

    private CustomerDAO customerDAO;
    private CustomerController customerController;
    private DefaultTableModel tableModel;
    private JTable customerTable;

    private JTextField idField;
    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneNumberField;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;

    public CustomerUI() {
    	 this.customerDAO = new CustomerDAO();
    	 this.customerController = new CustomerController();

        setTitle("Customer CRUD");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel();		
        customerTable = new JTable(tableModel);
        tableModel.addColumn("ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Email");
        tableModel.addColumn("Phone Number");
        add(new JScrollPane(customerTable), BorderLayout.CENTER);
        

        refreshTable();

        JPanel formPanel = new JPanel(new GridLayout(5, 2));
        JLabel idLabel = new JLabel("ID:");
        idField = new JTextField();
        idField.setEditable(false); 
        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();
        JLabel phoneNumberLabel = new JLabel("Phone Number:");
        phoneNumberField = new JTextField();
        formPanel.add(idLabel);
        formPanel.add(idField);
        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(emailLabel);
        formPanel.add(emailField);
        formPanel.add(phoneNumberLabel);
        formPanel.add(phoneNumberField);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCustomer();
            }
        });
        updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCustomer();
            }
        });
        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteCustomer();
            }
        });
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        add(formPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
        
        customerTable.getSelectionModel().addListSelectionListener((ListSelectionListener) new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                populateFieldsFromSelectedRow();
            }
        });


        setVisible(true);
    }

    private void refreshTable() {
        List<Customer> customers = customerDAO.getAllCustomers();
        tableModel.setRowCount(0); 
        for (Customer customer : customers) {
            Object[] rowData = {customer.getId(), customer.getName(), customer.getEmail(), customer.getPhoneNumber()};
            tableModel.addRow(rowData);
        }
    }
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }


    private void addCustomer() {
        String name = nameField.getText();
        String email = emailField.getText();
        String phoneNumber = phoneNumberField.getText();
        if (!name.isEmpty() && !email.isEmpty() && !phoneNumber.isEmpty()) {
            Customer customer = new Customer(0, name, email, phoneNumber); 
            customerController.addCustomer(customer);
            refreshTable();
            clearFields();
        }else if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email address."); 
        
        }else {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
        }
    }

    private void updateCustomer() {
        int selectedRow = customerTable.getSelectedRow();
        System.out.println("selectedRow"+selectedRow);
        if (selectedRow != -1) {
        	   System.out.println("333333333");
            String idText = idField.getText();
            String name = nameField.getText();
            String email = emailField.getText();
            String phoneNumber = phoneNumberField.getText();
            System.out.println("idText"+idText);
            System.out.println("name"+name);
            System.out.println("email"+email);
            System.out.println("phoneNumber"+phoneNumber);
            if (!idText.isEmpty() && !name.isEmpty() && !email.isEmpty() && !phoneNumber.isEmpty()) {
            	   System.out.println("yessssss");
                int id = Integer.parseInt(idText);
                Customer customer = new Customer(id, name, email, phoneNumber);
                System.out.println("yessssssdddddddd"+customer);
                customerController.updateCustomer(customer);
                refreshTable();
                clearFields();
            }else if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(this, "Please enter a valid email address."); 
                
            } 
            else {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a customer to update.");
        }
    }

   
    private void populateFieldsFromSelectedRow() {
        int selectedRow = customerTable.getSelectedRow();
        if (selectedRow != -1) {
            idField.setText(customerTable.getValueAt(selectedRow, 0).toString());
            nameField.setText(customerTable.getValueAt(selectedRow, 1).toString());
            emailField.setText(customerTable.getValueAt(selectedRow, 2).toString());
            phoneNumberField.setText(customerTable.getValueAt(selectedRow, 3).toString());
        }
    }
    private void deleteCustomer() {
        int selectedRow = customerTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) customerTable.getValueAt(selectedRow, 0);
            customerController.deleteCustomer(id);
            refreshTable();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a customer to delete.");
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        emailField.setText("");
        phoneNumberField.setText("");
    }

    public static void main(String[] args) {
        CustomerDAO customerDAO = new CustomerDAO(); 
        new CustomerUI();
    }
}

package UI;


import Model.Reservation;
import Model.SessionManager;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import Controller.ReservationController;
import Dao.ReservationDAO;
import Dao.RoomDao;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class ReservationUI extends JFrame {

    private ReservationDAO reservationDAO;
    private ReservationController reservationController;
    private RoomDao roomDao;
    private DefaultTableModel tableModel;
    private JTable reservationTable;
    private JComboBox<String> packageTypeComboBox;

    private JTextField idField;
    private JTextField roomIdField;
    private JTextField customerIdField;
    private JTextField packageTypeField;
    private JTextField amountField;
    private JTextField dateField; 

    private JButton addButton;
    private JButton updateButton;
    private JButton cancelButton;

    public ReservationUI() {
        this.reservationDAO = new ReservationDAO();
        this.reservationController = new ReservationController();
        this.roomDao = new RoomDao();
        setTitle("Reservation CRUD");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel();
        reservationTable = new JTable(tableModel);
        tableModel.addColumn("ID");
        tableModel.addColumn("Room ID");
        tableModel.addColumn("Customer Name");
        tableModel.addColumn("Package Type");
        tableModel.addColumn("Amount");
        tableModel.addColumn("Reservation Date"); 
        add(new JScrollPane(reservationTable), BorderLayout.CENTER);
        
    
        refreshTable();

        
        JPanel formPanel = new JPanel(new GridLayout(7, 2)); 
        String[] packageTypes = {"Full Board", "Half Board", "Bed and Breakfast"};
        String currentUserRole = SessionManager.getInstance().getRole();
      

       
        JLabel idLabel = new JLabel("ID:");
        idField = new JTextField();
        idField.setEditable(false); 
        JLabel roomIdLabel = new JLabel("Room ID:");
        roomIdField = new JTextField();
        JLabel customerIdLabel = new JLabel("Customer Name:");
        customerIdField = new JTextField();
        JLabel packageTypeLabel = new JLabel("Package Type:");
        packageTypeField = new JTextField();
        JLabel amountLabel = new JLabel("Amount:");
        amountField = new JTextField();
        JLabel dateLabel = new JLabel("Reservation Date:"); 
        dateField = new JTextField(); 
        packageTypeComboBox = new JComboBox<>(packageTypes);
        if("admin".equals(currentUserRole)) {
        	 formPanel.add(idLabel);
             formPanel.add(idField);
             formPanel.add(roomIdLabel);
             formPanel.add(roomIdField);
             formPanel.add(customerIdLabel);
             formPanel.add(customerIdField);
             formPanel.add(new JLabel("Package Type:"));
             formPanel.add(packageTypeComboBox);
             formPanel.add(dateLabel);
             formPanel.add(dateField); 
        }
       
        
       
       
        JPanel buttonPanel = new JPanel(new FlowLayout());
        if("admin".equals(currentUserRole)) {
        	
        	 addButton = new JButton("Add");
             addButton.addActionListener(new ActionListener() {
                 @Override
                 public void actionPerformed(ActionEvent e) {
                     try {
     					addReservation();
     				} catch (SQLException e1) {
     					// TODO Auto-generated catch block
     					e1.printStackTrace();
     				}
                 }
             });
             updateButton = new JButton("Update");
             updateButton.addActionListener(new ActionListener() {
                 @Override
                 public void actionPerformed(ActionEvent e) {
                     updateReservation();
                 }
             });
             cancelButton = new JButton("Cancel");
             cancelButton.addActionListener(new ActionListener() {
                 @Override
                 public void actionPerformed(ActionEvent e) {
                     cancelReservation();
                 }
             });
             buttonPanel.add(addButton);
             buttonPanel.add(updateButton);
             buttonPanel.add(cancelButton);
        }else {
        	 cancelButton = new JButton("Cancel");
             cancelButton.addActionListener(new ActionListener() {
                 @Override
                 public void actionPerformed(ActionEvent e) {
                     cancelReservation();
                 }
             });
             buttonPanel.add(cancelButton);
        }
       
        


        add(formPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
        
        reservationTable.getSelectionModel().addListSelectionListener((ListSelectionListener) new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                populateFieldsFromSelectedRow();
            }
        });

        setVisible(true);
    }

    private void refreshTable() {
        List<Reservation> reservations = reservationController.getAllReservations();
        tableModel.setRowCount(0); // Clear existing rows
        for (Reservation reservation : reservations) {
            Object[] rowData = {reservation.getId(), reservation.getRoomId(), reservation.getCustomerName(),
                    reservation.getPackageType(), reservation.getAmount(), reservation.getReservationDate()};
            tableModel.addRow(rowData);
        }
    }
    
    private void populateFieldsFromSelectedRow() {
        int selectedRow = reservationTable.getSelectedRow();
        if (selectedRow != -1) {
            idField.setText(reservationTable.getValueAt(selectedRow, 0).toString());
            roomIdField.setText(reservationTable.getValueAt(selectedRow, 1).toString());
            customerIdField.setText(reservationTable.getValueAt(selectedRow, 2).toString());
            packageTypeField.setText(reservationTable.getValueAt(selectedRow, 3).toString());
            amountField.setText(reservationTable.getValueAt(selectedRow, 4).toString());
            dateField.setText(reservationTable.getValueAt(selectedRow, 5).toString());
        }
    }
    
    private void cancelReservation() {
        int selectedRow = reservationTable.getSelectedRow();
        if (selectedRow != -1) {
            int reservationId = (int) reservationTable.getValueAt(selectedRow, 0);
            reservationController.cancelReservation(reservationId);
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a reservation to cancel.");
        }
    }

    private void clearFields() {
        idField.setText("");
        roomIdField.setText("");
        customerIdField.setText("");
        packageTypeField.setText("");
        amountField.setText("");
        dateField.setText(""); 
    }

    private void addReservation() throws SQLException {
        String roomId = roomIdField.getText();
        String customerName = customerIdField.getText();
        String packageType = (String) packageTypeComboBox.getSelectedItem();
//        double amount = Double.parseDouble(amountField.getText());
        String dateString = dateField.getText(); 
        Date reservationDate = Date.valueOf(dateString);
        double roomPrice = roomDao.getRoomPrice(roomId);
        double totalAmount = reservationDAO.calculateReservationAmount(roomPrice, packageType);
        amountField.setText(String.valueOf(totalAmount));
        if (!roomId.isEmpty() && !customerName.isEmpty() && reservationDate != null) {
        	 Reservation reservation = new Reservation(roomId, customerName, packageType, totalAmount, reservationDate);
        	 reservationController.addReservation(reservation);
             refreshTable();
             clearFields();
        }else {
        	 JOptionPane.showMessageDialog(this, "Please fill in all fields.");

        }
    }

    private void updateReservation() {
        int id = Integer.parseInt(idField.getText());
        String roomId = roomIdField.getText();
        String customerName = customerIdField.getText();
        String packageType = packageTypeField.getText();
        double amount = Double.parseDouble(amountField.getText());
        String dateString = dateField.getText(); 
        Date reservationDate = Date.valueOf(dateString); 
        if (!roomId.isEmpty() && !customerName.isEmpty() && !((CharSequence) reservationDate).isEmpty()) {
        	 Reservation reservation = new Reservation(id, roomId, customerName, packageType, amount, reservationDate);
        	 reservationController.updateReservation(reservation);
             refreshTable();
        }else {
        	 JOptionPane.showMessageDialog(this, "Please fill in all fields.");
        }
       
       
}
    
    public static void main(String[] args) {
        ReservationDAO reservationDAO = new ReservationDAO(); 
        new ReservationUI();
    }
}

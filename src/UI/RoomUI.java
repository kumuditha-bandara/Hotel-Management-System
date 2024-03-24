package UI;

import Dao.RoomDao;
import Model.Room;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import Controller.RoomController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RoomUI extends JFrame {

    private RoomDao roomDAO;
    private RoomController roomController;
    private DefaultTableModel tableModel;
    private JTable roomTable;

    private JTextField idField;
    private JTextField numberField;
    private JTextField categoryIdField;
    private JTextField priceField;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;

    public RoomUI() {
    	
        this.roomDAO = new RoomDao();
        this.roomController = new RoomController();

        setTitle("Room CRUD");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

     
        tableModel = new DefaultTableModel();
        roomTable = new JTable(tableModel);
        tableModel.addColumn("ID");
        tableModel.addColumn("Room Number");
        tableModel.addColumn("Category ID");
        tableModel.addColumn("Price");
        add(new JScrollPane(roomTable), BorderLayout.CENTER);

        
        refreshTable();

        
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        JLabel idLabel = new JLabel("ID:");
        idField = new JTextField();
        idField.setEditable(false);
        JLabel numberLabel = new JLabel("Room Number:");
        numberField = new JTextField();
        JLabel categoryLabel = new JLabel("Category ID:");
        categoryIdField = new JTextField();
        JLabel priceLabel = new JLabel("Price:");
        priceField = new JTextField();
        formPanel.add(idLabel);
        formPanel.add(idField);
        formPanel.add(numberLabel);
        formPanel.add(numberField);
        formPanel.add(categoryLabel);
        formPanel.add(categoryIdField);
        formPanel.add(priceLabel);
        formPanel.add(priceField);

        // Create buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        addButton = new JButton("Add");
        addButton.setPreferredSize(new Dimension(100, 30));
        addButton.setBackground(Color.decode("#4CAF50")); 
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addRoom();
            }
        });
        updateButton = new JButton("Update");
        updateButton.setPreferredSize(new Dimension(100, 30));
        updateButton.setBackground(Color.decode("#FFC107")); 
        updateButton.setForeground(Color.WHITE);
        updateButton.setFocusPainted(false);
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateRoom();
            }
        });
        deleteButton = new JButton("Delete");
        deleteButton.setPreferredSize(new Dimension(100, 30));
        deleteButton.setBackground(Color.decode("#F44336")); 
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteRoom();
            }
        });
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

       
        add(formPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
        
        roomTable.getSelectionModel().addListSelectionListener((ListSelectionListener) new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                populateFieldsFromSelectedRow();
            }
        });

        setVisible(true);
    }

    private void refreshTable() {
        List<Room> rooms = roomDAO.getAllRooms();
        tableModel.setRowCount(0); 
        for (Room room : rooms) {
            Object[] rowData = {room.getId(), room.getRoomNumber(), room.getCategoryID(), room.getPrice()};
            tableModel.addRow(rowData);
        }
    }

    private void addRoom() {
        String number = numberField.getText();
        int categoryId = Integer.parseInt(categoryIdField.getText());
        double price = Double.parseDouble(priceField.getText());
        if (!number.isEmpty()) {
            Room room = new Room(number, categoryId, price);
            roomController.addRoom(room);
            refreshTable();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a room number.");
        }
    }

    private void updateRoom() {
        String idText = idField.getText();
        String number = numberField.getText();
        int categoryId = Integer.parseInt(categoryIdField.getText());
        double price = Double.parseDouble(priceField.getText());
        if (!idText.isEmpty() && !number.isEmpty()) {
            int id = Integer.parseInt(idText);
            Room room = new Room(id, number, categoryId, price);
            roomController.updateRoom(room);
            refreshTable();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a room and enter new details.");
        }
    }

    private void deleteRoom() {
        int selectedRow = roomTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) roomTable.getValueAt(selectedRow, 0);
            roomController.deleteRoom(id);
            refreshTable();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a room to delete.");
        }
    }
    
    private void populateFieldsFromSelectedRow() {
        int selectedRow = roomTable.getSelectedRow();
        if (selectedRow != -1) {
            idField.setText(roomTable.getValueAt(selectedRow, 0).toString());
            numberField.setText(roomTable.getValueAt(selectedRow, 1).toString());
            categoryIdField.setText(roomTable.getValueAt(selectedRow, 2).toString());
            priceField.setText(roomTable.getValueAt(selectedRow, 3).toString());
        }
    }

    private void clearFields() {
        idField.setText("");
        numberField.setText("");
        categoryIdField.setText("");
        priceField.setText("");
    }

    public static void main(String[] args) {
        RoomDao roomDAO = new RoomDao();
        new RoomUI();
    }
}

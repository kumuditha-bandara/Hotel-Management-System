package UI;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import Controller.RoomCategoryController;
import Dao.RoomCategoryDao;
import Model.RoomCategory;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RoomCategoryUI extends JFrame {

    private RoomCategoryDao roomCategoryDao;
    private RoomCategoryController roomCategoryController;
    private DefaultTableModel tableModel;
    private JTable roomCategoryTable;

    private JTextField idField;
    private JTextField categoryField;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;

    public RoomCategoryUI() {
        this.roomCategoryDao = new RoomCategoryDao();
        this.roomCategoryController = new RoomCategoryController();
        setTitle("Room Category Management");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create table model and table
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Category");
        roomCategoryTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(roomCategoryTable);

        // Fetch room categories and populate table
        refreshTable();

        // Create form panel
        JPanel formPanel = new JPanel(new GridLayout(2, 2));
        JLabel idLabel = new JLabel("ID:");
        idField = new JTextField();
        idField.setEditable(false); 
        JLabel categoryLabel = new JLabel("Category:");
        categoryField = new JTextField();
        formPanel.add(idLabel);
        formPanel.add(idField);
        formPanel.add(categoryLabel);
        formPanel.add(categoryField);

        // Create buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addRoomCategory();
            }
        });
        updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateRoomCategory();
            }
        });
        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteRoomCategory();
            }
        });
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        
        add(formPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        roomCategoryTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                populateFieldsFromSelectedRow();
            }
        });

        setVisible(true);
    }

    private void refreshTable() {
        List<RoomCategory> roomCategories = roomCategoryController.getAllRoomCategories();
        tableModel.setRowCount(0); // Clear existing rows
        for (RoomCategory category : roomCategories) {
            Object[] rowData = {category.getId(), category.getName()};
            tableModel.addRow(rowData);
        }
    }

    private void addRoomCategory() {
        String category = categoryField.getText();
        if (!category.isEmpty()) {
        	roomCategoryController.addRoomCategory(category);
            refreshTable();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a category.");
        }
    }

    private void updateRoomCategory() {
        int selectedRow = roomCategoryTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) roomCategoryTable.getValueAt(selectedRow, 0);
            String category = categoryField.getText();
            if (!category.isEmpty()) {
            	roomCategoryController.updateRoomCategory(id, category);
                refreshTable();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a category.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a room category to update.");
        }
    }

    private void deleteRoomCategory() {
        int selectedRow = roomCategoryTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) roomCategoryTable.getValueAt(selectedRow, 0);
            roomCategoryController.deleteRoomCategory(id);
            refreshTable();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a room category to delete.");
        }
    }

    private void populateFieldsFromSelectedRow() {
        int selectedRow = roomCategoryTable.getSelectedRow();
        if (selectedRow != -1) {
            idField.setText(roomCategoryTable.getValueAt(selectedRow, 0).toString());
            categoryField.setText(roomCategoryTable.getValueAt(selectedRow, 1).toString());
        }
    }

    private void clearFields() {
        idField.setText("");
        categoryField.setText("");
    }

    public static void main(String[] args) {
     new RoomCategoryUI(); 
    }
}

package com.mycompany.mavenproject3;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

public class CustomerForm extends JFrame {
    private JTable drinkTable;
    private DefaultTableModel tableModel;
    private JTextField usernameField;
    private JTextField emailField;
    private JButton saveButton;
    private JButton deleteButton;
    private JButton editButton;
    private List<Customer> customers;
    private int nextId = 0;

    public CustomerForm(List<Customer> customers) {
        this.customers = customers;
        setTitle("WK. Cuan | Data Customer");
        setSize(800, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Panel form pemesanan
        JPanel formPanel = new JPanel();
        formPanel.add(new JLabel("Username:"));
        usernameField = new JTextField(15);
        formPanel.add(usernameField);
        
        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField(15);
        formPanel.add(emailField);
        
        saveButton = new JButton("Simpan");
        formPanel.add(saveButton);
        saveButton.setEnabled(false);

        deleteButton = new JButton("Hapus");
        formPanel.add(deleteButton);
        deleteButton.setEnabled(false);

        editButton = new JButton("Ubah");
        formPanel.add(editButton);
        editButton.setEnabled(false);
        
        tableModel = new DefaultTableModel(new String[]{"Username", "Email"}, 0);
        drinkTable = new JTable(tableModel);
        loadCustomerData(customers);

        add(formPanel, "South");// tambahkan tombol dibagian bawah
        JScrollPane scrollPane = new JScrollPane(drinkTable);
        add(scrollPane);//tambahkan si tabel arraynya

        //tempat function
        //function untuk munculin datanya di bar
        drinkTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = drinkTable.getSelectedRow();
            if (selectedRow != -1) {
                usernameField.setText(String.valueOf(tableModel.getValueAt(selectedRow, 0)));
                emailField.setText(String.valueOf(tableModel.getValueAt(selectedRow, 1)));
            }
        });

        DocumentListener inputListener = new DocumentListener() {
            void updateButtons() {
                boolean filled = !usernameField.getText().trim().isEmpty() && !emailField.getText().trim().isEmpty();
                boolean rowSelected = drinkTable.getSelectedRow() != -1;

                saveButton.setEnabled(filled && !rowSelected);
                editButton.setEnabled(filled && rowSelected);
                deleteButton.setEnabled(filled && rowSelected);
            }

            public void insertUpdate(DocumentEvent e) { updateButtons(); }
            public void removeUpdate(DocumentEvent e) { updateButtons(); }
            public void changedUpdate(DocumentEvent e) { updateButtons(); }
        };

        usernameField.getDocument().addDocumentListener(inputListener);
        emailField.getDocument().addDocumentListener(inputListener);

        //tambah
        saveButton.addActionListener(e -> {
            String email = emailField.getText();
            String username = usernameField.getText();

            try {
                JOptionPane.showMessageDialog(this, "Customer berhasil ditambah!");
                Customer customer = new Customer(nextId++, username, email);
                customers.add(customer);
                tableModel.addRow(new Object[]{username, email});

                usernameField.setText("");
                emailField.setText("");
                saveButton.setEnabled(false);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error");
            }
        });

        //edit
        editButton.addActionListener(e -> {
            int selectedRow = drinkTable.getSelectedRow();
            String email = emailField.getText();
            String username = usernameField.getText();

            try {
                JOptionPane.showMessageDialog(this, "Customer berhasil diubah!");
                Customer Customer = customers.get(selectedRow);
                Customer.setUsername(username);
                Customer.setEmail(email);

                tableModel.setValueAt(username, selectedRow, 0);
                tableModel.setValueAt(email, selectedRow, 1);

                usernameField.setText("");
                emailField.setText("");
                editButton.setEnabled(false);
                deleteButton.setEnabled(false);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error");
            }
        });

        //hapus
        deleteButton.addActionListener(e -> {
            int selectedRow = drinkTable.getSelectedRow();
            if (selectedRow != -1) {
                JOptionPane.showMessageDialog(this, "Customer berhasil dihapus!");
                customers.remove(selectedRow);
                tableModel.removeRow(selectedRow);

                usernameField.setText("");
                emailField.setText("");
                deleteButton.setEnabled(false);
                editButton.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(this, "Error");
            }
        });
    }

    private void loadCustomerData(List<Customer> CustomerList) {
        for (Customer Customer : CustomerList) {
            tableModel.addRow(new Object[]{
                Customer.getUsername(), Customer.getEmail()
            });
        }
    }
}
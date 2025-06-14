/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3;

/**
 *
 * @author ASUS
 */
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

public class ProductForm extends JFrame {
    private JTable drinkTable;
    private DefaultTableModel tableModel;
    private JTextField codeField;
    private JTextField nameField;
    private JComboBox<String> categoryField;
    private JTextField priceField;
    private JTextField stockField;
    private JButton saveButton;
    private JButton deleteButton;
    private JButton editButton;
    private int nextId = 0;
    private List<Product> products;
    private ProductSell productSell;
    private Mavenproject3 mainApp;

    public ProductForm(List<Product> products, Mavenproject3 mainApp) {
        this.products = products;
        this.mainApp = mainApp;
        
        setTitle("WK. Cuan | Stok Barang");
        setSize(1200, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Panel form pemesanan
        JPanel formPanel = new JPanel();
        formPanel.add(new JLabel("Kode Barang"));
        codeField = new JTextField(5);
        formPanel.add(codeField);
        
        formPanel.add(new JLabel("Nama Barang:"));
        nameField = new JTextField(10);
        formPanel.add(nameField);
        
        formPanel.add(new JLabel("Kategori:"));
        categoryField = new JComboBox<>(new String[]{"Coffee", "Dairy", "Juice", "Soda", "Tea"});
        formPanel.add(categoryField);
        
        formPanel.add(new JLabel("Harga Jual:"));
        priceField = new JTextField(10);
        formPanel.add(priceField);
        
        formPanel.add(new JLabel("Stok Tersedia:"));
        stockField = new JTextField(5);
        formPanel.add(stockField);
        
        saveButton = new JButton("Simpan");
        formPanel.add(saveButton);
        saveButton.setEnabled(false);

        deleteButton = new JButton("Hapus");
        formPanel.add(deleteButton);
        deleteButton.setEnabled(false);

        editButton = new JButton("Ubah");
        formPanel.add(editButton);
        editButton.setEnabled(false);
        
        tableModel = new DefaultTableModel(new String[]{"Kode", "Nama", "Kategori", "Harga Jual", "Stok"}, 0);
        drinkTable = new JTable(tableModel);
        loadProductData(products);

        add(formPanel, "South");// tambahkan tombol dibagian bawah
        JScrollPane scrollPane = new JScrollPane(drinkTable);
        add(scrollPane);//tambahkan si tabel arraynya

        //tempat function
        //function untuk munculin datanya di bar
        drinkTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = drinkTable.getSelectedRow();
            if (selectedRow != -1) {
                codeField.setText(String.valueOf(tableModel.getValueAt(selectedRow, 0)));
                nameField.setText(String.valueOf(tableModel.getValueAt(selectedRow, 1)));
                categoryField.setSelectedItem(tableModel.getValueAt(selectedRow, 2));
                priceField.setText(String.valueOf(tableModel.getValueAt(selectedRow, 3)));
                stockField.setText(String.valueOf(tableModel.getValueAt(selectedRow, 4)));
            }
        });

        DocumentListener inputListener = new DocumentListener() {
            void updateButtons() {
                boolean filled = !codeField.getText().trim().isEmpty() && !nameField.getText().trim().isEmpty() && !priceField.getText().trim().isEmpty() && !stockField.getText().trim().isEmpty();
                boolean rowSelected = drinkTable.getSelectedRow() != -1;

                saveButton.setEnabled(filled && !rowSelected);
                editButton.setEnabled(filled && rowSelected);
                deleteButton.setEnabled(filled && rowSelected);
            }

            public void insertUpdate(DocumentEvent e) { updateButtons(); }
            public void removeUpdate(DocumentEvent e) { updateButtons(); }
            public void changedUpdate(DocumentEvent e) { updateButtons(); }
        };

        codeField.getDocument().addDocumentListener(inputListener);
        nameField.getDocument().addDocumentListener(inputListener);
        priceField.getDocument().addDocumentListener(inputListener);
        stockField.getDocument().addDocumentListener(inputListener);

        //tambah
        saveButton.addActionListener(e -> {
            String name = nameField.getText();
            String code = codeField.getText();
            String category = (String) categoryField.getSelectedItem();
            String priceText = priceField.getText();
            String stockText = stockField.getText();
            if (code.isEmpty() || name.isEmpty() || category.isEmpty() || priceText.isEmpty() || stockText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Kode, Nama, Kategori, Harga dan Stok harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                JOptionPane.showMessageDialog(this, "Produk berhasil ditambah!");
                double price = Double.parseDouble(priceText);
                int stock = Integer.parseInt(stockText);
                Product product = new Product(nextId++, code, name, category, price, stock);
                products.add(product);
                tableModel.addRow(new Object[]{code, name, category, price, stock});

                codeField.setText("");
                nameField.setText("");
                categoryField.setSelectedItem(0);
                priceField.setText("");
                stockField.setText("");
                saveButton.setEnabled(false);
                productSell.refreshData();
                mainApp.LoadBanner();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error");
            }
        });

        //edit
        editButton.addActionListener(e -> {
            int selectedRow = drinkTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Pilih produk yang ingin diubah!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String name = nameField.getText();
            String code = codeField.getText();
            String category = (String) categoryField.getSelectedItem();
            String priceText = priceField.getText();
            String stockText = stockField.getText();
            if (code.isEmpty() || name.isEmpty() || category.isEmpty() || priceText.isEmpty() || stockText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Kode, Nama, Kategori, Harga dan Stok harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                JOptionPane.showMessageDialog(this, "Produk berhasil diubah!");
                double price = Double.parseDouble(priceText);
                int stock = Integer.parseInt(stockText);
                Product product = products.get(selectedRow);
                product.setName(name);
                product.setCode(code);
                product.setCategory(category);
                product.setPrice(price);
                product.setStock(stock);

                tableModel.setValueAt(code, selectedRow, 0);
                tableModel.setValueAt(name, selectedRow, 1);
                tableModel.setValueAt(category, selectedRow, 2);
                tableModel.setValueAt(price, selectedRow, 3);
                tableModel.setValueAt(stock, selectedRow, 4);

                codeField.setText("");
                nameField.setText("");
                categoryField.setSelectedIndex(0);
                priceField.setText("");
                stockField.setText("");
                editButton.setEnabled(false);
                deleteButton.setEnabled(false);
                productSell.refreshData();
                mainApp.LoadBanner();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error");
            }
        });

        //hapus
        deleteButton.addActionListener(e -> {
            int selectedRow = drinkTable.getSelectedRow();
            if (selectedRow != -1) {
                JOptionPane.showMessageDialog(this, "Produk berhasil dihapus!");
                products.remove(selectedRow);
                tableModel.removeRow(selectedRow);

                codeField.setText("");
                nameField.setText("");
                categoryField.setSelectedIndex(0);
                priceField.setText("");
                stockField.setText("");
                deleteButton.setEnabled(false);
                editButton.setEnabled(false);
                productSell.refreshData();
                mainApp.LoadBanner();
            } else {
                JOptionPane.showMessageDialog(this, "Error");
            }
        });
    }

    private void loadProductData(List<Product> productList) {
        for (Product product : productList) {
            tableModel.addRow(new Object[]{
                product.getCode(), product.getName(), product.getCategory(), product.getPrice(), product.getStock()
            });
        }
    }

    public void setProductSell(ProductSell productSell) {
        this.productSell = productSell;
    }

    public void refreshProduct() {
        tableModel.setRowCount(0);

        for(Product product : products) {
            tableModel.addRow(new Object[] {
                product.getCode(),
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getStock()
            });
        }
    }
}

package com.mycompany.mavenproject3;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ProductSell extends JFrame {
    public JComboBox nameBox;
    public JTextField stockField;
    public JTextField priceField;
    public JTextField quantityField;
    public JButton calculateButton;

    private List<Product> productList;
    private ProductForm productForm;
    private Mavenproject3 mainApp;

    public ProductSell(List<Product> productList, Mavenproject3 mainApp) {
        this.productList = productList;
        this.mainApp = mainApp;

        setTitle("WK. Cuan | Form Penjualan");
        setSize(600, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Barang"));
        nameBox = new JComboBox<>();
        for (Product product : productList) {
            nameBox.addItem(product.getName());  // Menambah nama produk ke JComboBox
        }
        inputPanel.add(nameBox);

        inputPanel.add(new JLabel("Stok tersedia"));
        stockField = new JTextField(5);
        stockField.setEditable(false);
        inputPanel.add(stockField);

        inputPanel.add(new JLabel("Harga jual"));
        priceField = new JTextField(10);
        priceField.setEditable(false);
        inputPanel.add(priceField);

        inputPanel.add(new JLabel("Quantity"));
        quantityField = new JTextField(5);
        inputPanel.add(quantityField);

        //tombol proses
        calculateButton = new JButton("Proses");
        inputPanel.add(calculateButton);

        add(inputPanel, BorderLayout.CENTER);// tambahkan tombol dan posisi letaknya

        refreshData();

        nameBox.addActionListener(e -> updateFields());

        calculateButton.addActionListener(e -> {
            int selectedIndex = nameBox.getSelectedIndex();
            if (selectedIndex == -1) {
                JOptionPane.showMessageDialog(this, "Pilih Produknya!");
                return;
            }

            String quantityText = quantityField.getText();
            if (quantityText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Kuantitas harus diisi!");
                return;
            }

            try {
                int quantity = Integer.parseInt(quantityText);
                Product selectedProduct = productList.get(selectedIndex);

                if (quantity > selectedProduct.getStock()) {
                    JOptionPane.showMessageDialog(this, "Kuantitas melebihi stok!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int stockLeft = selectedProduct.getStock() - quantity;
                selectedProduct.setStock(stockLeft);
                
                JOptionPane.showMessageDialog(this, "Sisa stok: " + stockLeft);
                updateFields(); //untuk update tampilan stok
                productForm.refreshProduct();
                mainApp.LoadBanner();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Kuantitas harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public void refreshData() {
        nameBox.removeAllItems(); // Hapus semua item lama
        for (Product product : productList) {
            nameBox.addItem(product.getName());
        }
    }

    private void updateFields() {
        int selectedIndex = nameBox.getSelectedIndex();
        if (selectedIndex != -1) {
            Product selectedProduct = productList.get(selectedIndex);
            stockField.setText(String.valueOf(selectedProduct.getStock()));
            priceField.setText(String.valueOf(selectedProduct.getPrice()));
        }
    }

    public void setProductForm(ProductForm productForm) {
        this.productForm = productForm;
    }
}
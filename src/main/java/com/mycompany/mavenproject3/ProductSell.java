package com.mycompany.mavenproject3;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ProductSell extends JFrame {
    public JTextField nameField;
    public JTextField stockField;
    public JTextField priceField;
    public JTextField quantityField;
    public JButton calculateButton;

    public ProductSell() {
        setTitle("WK. Cuan | Form Penjualan");
        setSize(600, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Barang"));
        nameField = new JTextField(10);
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Stok tersedia"));
        stockField = new JTextField(5);
        inputPanel.add(stockField);

        inputPanel.add(new JLabel("Harga jual"));
        priceField = new JTextField(10);
        inputPanel.add(priceField);

        inputPanel.add(new JLabel("Quantity"));
        quantityField = new JTextField(5);
        inputPanel.add(quantityField);

        //tombol proses
        calculateButton = new JButton("Proses");
        inputPanel.add(calculateButton);

        add(inputPanel, BorderLayout.CENTER);// tambahkan tombol dan posisi letaknya

        calculateButton.addActionListener(e -> {
            String name = nameField.getText();
            String priceText = priceField.getText();
            String stockText = stockField.getText();
            String quantityText = quantityField.getText();
            if (name.isEmpty() || stockText.isEmpty() || priceText.isEmpty() || quantityText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nama, Stok, Harga dan Kuantitas harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                JOptionPane.showMessageDialog(this, "Total Stok Produk: ");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error");
            }
        });
    }
}

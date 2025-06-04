package com.mycompany.mavenproject3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Mavenproject3 extends JFrame implements Runnable {
    private String text;
    private int x;
    private int width;
    private BannerPanel bannerPanel;
    private JButton addProductButton;
    private JButton sellProductButton;
    private JTextField codeField;
    private JTextField nameField;
    private JComboBox<String> categoryField;
    private JTextField priceField;
    private JTextField stockField;
    private ProductForm productForm;
    private ProductSell productSell;
    private List<Product> products;
    private List<Customer> customers;
    private List<DataProduct> dataProduct;
    private String bannerSentence = "";
    private JButton customerButton;
    private CustomerForm customerForm;

    public Mavenproject3() {
        this.products = new ArrayList<>();
        products.add(new Product(1, "P001", "Americano", "Coffee", 18000, 10));
        products.add(new Product(2, "P002", "Pandan Latte", "Coffee", 15000, 8));

        this.dataProduct = new ArrayList<>();

        this.customers = new ArrayList<>();
            
        setTitle("WK. STI Chill");
        setSize(600, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel teks berjalan
        bannerPanel = new BannerPanel();
        add(bannerPanel, BorderLayout.CENTER);
        LoadBanner();

        // Tombol "Kelola Produk" dan "Form Penjualan"
        JPanel bottomPanel = new JPanel();
        addProductButton = new JButton("Kelola Produk");
        sellProductButton = new JButton("Form Penjualan");
        customerButton = new JButton("Data Pelanggan");
        bottomPanel.add(addProductButton);
        bottomPanel.add(sellProductButton);
        bottomPanel.add(customerButton);
        add(bottomPanel, BorderLayout.SOUTH);

        productForm = new ProductForm(products, this);
        productSell = new ProductSell(products, this);

        productSell.setProductForm(productForm);
        productForm.setProductSell(productSell);
        
        addProductButton.addActionListener(e -> {
            if (productForm == null) {
                productForm = new ProductForm(products, this);
            }
            if (productSell == null) {
                productSell = new ProductSell(products, this);
            }
            productForm.setVisible(true);
        });

        sellProductButton.addActionListener(e -> {
            if (productForm == null) {
                productForm = new ProductForm(products, this);
            }
            if (productSell == null) {
                productSell = new ProductSell(products, this);
            }
            productSell.setVisible(true);
        });

        customerButton.addActionListener(e -> {
            if (customerForm == null) {
                customerForm = new CustomerForm(customers);
            }
            customerForm.setVisible(true);
        });

        setVisible(true);

        Thread thread = new Thread(this);
        thread.start();
    }

    class BannerPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 18));
            g.drawString(bannerSentence, x, getHeight() / 2);
        }
    }

    @Override
    public void run() {
        width = getWidth();
        while (true) {
            x += 5;
            if (x > width) {
                x = -getFontMetrics(new Font("Arial", Font.BOLD, 18)).stringWidth(bannerSentence);
            }
            bannerPanel.repaint();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public void LoadBanner() {
        StringBuilder text = new StringBuilder("Menu yang tersedia: ");

        for (Product products : products) {
            if (products.getStock() > 0) {
                text.append(products.getName()).append(" | ");
            }
            bannerSentence = text.toString();
            bannerPanel.repaint();
        }
    }

    public static void main(String[] args) {
        // Jalankan aplikasi utama
        new Mavenproject3();
    }
}
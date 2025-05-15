package com.mycompany.mavenproject3;

import javax.swing.*;
import java.awt.*;

public class Mavenproject3 extends JFrame implements Runnable {
    private String text;
    private int x;
    private int width;
    private BannerPanel bannerPanel;
    private JButton addProductButton;
    private JTextField codeField;
    private JTextField nameField;
    private JComboBox<String> categoryField;
    private JTextField priceField;
    private JTextField stockField;

    public Mavenproject3(String text) {
        this.text = text;
        setTitle("WK. STI Chill");
        setSize(600, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel teks berjalan
        bannerPanel = new BannerPanel();
        add(bannerPanel, BorderLayout.CENTER);

        // Tombol "Kelola Produk"
        JPanel bottomPanel = new JPanel();
        addProductButton = new JButton("Kelola Produk");
        bottomPanel.add(addProductButton);
        add(bottomPanel, BorderLayout.SOUTH);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Tambah Produk");
        JButton editButton = new JButton("Edit Produk");
        JButton deleteButton = new JButton("Hapus Produk");
        
        addProductButton.addActionListener(e -> {
            new ProductForm().setVisible(true);
        });

        JPanel inputPanel = new JPanel(new FlowLayout());
        codeField = new JTextField(5);
        nameField = new JTextField(15);
        categoryField = new JComboBox<>();
        priceField = new JTextField(10);
        stockField = new JTextField(5);
        inputPanel.add(new JLabel("Kode: "));
        inputPanel.add(codeField);
        inputPanel.add(new JLabel("Nama: "));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Kategori: "));
        inputPanel.add(categoryField);
        inputPanel.add(new JLabel("Harga: "));
        inputPanel.add(priceField );
        inputPanel.add(new JLabel("Stok: "));
        inputPanel.add(stockField);

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

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
            g.drawString(text, x, getHeight() / 2);
        }
    }

    @Override
    public void run() {
        width = getWidth();
        while (true) {
            x += 5;
            if (x > width) {
                x = -getFontMetrics(new Font("Arial", Font.BOLD, 18)).stringWidth(text);
            }
            bannerPanel.repaint();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        new Mavenproject3("Menu yang tersedia: Americano | Pandan Latte | Aren Latte | Matcha Frappucino | Jus Apel");
    }
}

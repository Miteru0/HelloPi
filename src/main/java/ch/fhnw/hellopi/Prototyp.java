package ch.fhnw.hellopi;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.Objects;

class Prototyp {
    public static void main(String[] args) {
        Data clothes = Data.INSTANCE;
        clothes.addClothing("1", new Clothing("hui", "bolschoi"));

        JFrame frame = new JFrame("Barcode Scanner");
        JTextField barcodeField = new JTextField(20);
        JLabel label = new JLabel("Scan a barcode:");

        // Add an ActionListener to process the input
        barcodeField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String scannedData = barcodeField.getText();
                if(Objects.equals(scannedData, "1")) {
                    barcodeField.setText(clothes.getClothing("1").toString());
                }
            }
        });

        barcodeField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                String scannedData = barcodeField.getText();
                if(Objects.equals(scannedData, "1")) {
                    barcodeField.setText(clothes.getClothing("1").toString());
                }
            }
            @Override
            public void keyPressed(KeyEvent e) {
                
            }

            @Override
            public void keyReleased(KeyEvent e) {
                
            }
        });

        // Set up the frame
        frame.setLayout(new java.awt.FlowLayout());
        frame.add(label);
        frame.add(barcodeField);
        frame.setSize(300, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

package ch.fhnw.hellopi;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

class Prototyp {
    public static void main(String[] args) throws Exception {
        Data clothes = Data.INSTANCE;
        clothes.addClothing("1", new Clothing("T-shirt", "xxl", "src/images/tshirt.png"));
        clothes.addClothing("2", new Clothing("Hose", "5xl", "src/images/hose.png"));
        clothes.addClothing("3", new Clothing("Coca Cola", "5l", "src/images/cola.png"));

        JFrame frame = new JFrame("Barcode Scanner");
        JTextField barcodeField = new JTextField(20);
        JLabel label = new JLabel("Scan a barcode:");
        BufferedImage myPicture = ImageIO.read(new File("src/images/notFound.png"));
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));


        // Add an ActionListener to process the input
        barcodeField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String scannedData = barcodeField.getText();
                //barcodeField.setText(scannedData);
                barcodeField.setText(clothes.getClothing(scannedData).toString());

            }
        });

        barcodeField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {


            }
            @Override
            public void keyPressed(KeyEvent e) {
                barcodeField.setText("");
            }

            @Override
            public void keyReleased(KeyEvent e) {

                String scannedData = barcodeField.getText();
                barcodeField.setText(clothes.getClothing(scannedData).toString());
                BufferedImage picture = null;
                try {
                    picture = ImageIO.read(new File(clothes.getClothing(scannedData).getImagePath()));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                picLabel.setIcon(new ImageIcon(picture));

            }
        });

        // Set up the frame
        frame.setLayout(new java.awt.FlowLayout());
        frame.add(label);
        label.setBounds(0, 0, 500, 300);
        frame.add(barcodeField);
        frame.add(picLabel);
        frame.setSize(1000, 1000);
        picLabel.setBounds(200, 200, 200, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

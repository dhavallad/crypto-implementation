package cryptography;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import javax.swing.*;

public class ImageHiding extends JFrame implements ActionListener {
    BufferedImage hostImage;
    BufferedImage secretImage;

    JPanel controlPanel;
    JPanel imagePanel;

    JTextField encodeBitsText, mValueBitsTextBox;
    JButton encodeBitsPlus;
    JButton encodeBitsMinus;

    JTextField nBitsText;
    JButton nBitsPlus;
    JButton nBitsMinus;

    ImageCanvas hostCanvas;
    ImageCanvas secretCanvas;

    Steganography s;

    public BufferedImage getHostImage() {
        BufferedImage img = null;

        try {
            img = ImageIO.read(new File("resource/hostimage.jpg"));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return img;
    }

    public int getBits() {
        return Integer.parseInt(encodeBitsText.getText());
    }

    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();

        if (source == encodeBitsPlus) {
            int bits = this.getBits() + 1;

            if (bits > 8) {
                bits = 8;
            }

            encodeBitsText.setText(Integer.toString(bits));

            s = new Steganography(this.getHostImage());

            // Store return value of function.
            int temp = s.encode(bits);
            mValueBitsTextBox.setText(Integer.toString(temp));

            hostCanvas.setImage(s.getImage());
            hostCanvas.repaint();

        } else if (source == encodeBitsMinus) {
            int bits = this.getBits() - 1;

            if (bits < 0) {
                bits = 0;
            }

            encodeBitsText.setText(Integer.toString(bits));

            s = new Steganography(this.getHostImage());

            // Store return value of function.
            int temp = s.encode(bits);
            mValueBitsTextBox.setText(Integer.toString(temp));

            hostCanvas.setImage(s.getImage());
            hostCanvas.repaint();
        }
    }

    public ImageHiding() {
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        this.setTitle("Hiding secret text in image file.");
        Container container = this.getContentPane();

        this.setLayout(layout);

        this.add(new JLabel("Bits to encode into host image:"));
        encodeBitsText = new JTextField("0", 5);
        encodeBitsText.setEditable(false);

        gbc.weightx = -1.0;
        layout.setConstraints(encodeBitsText, gbc);
        this.add(encodeBitsText);

        encodeBitsPlus = new JButton("Increase bits");
        encodeBitsPlus.addActionListener(this);

        encodeBitsMinus = new JButton("Decrease bits");
        encodeBitsMinus.addActionListener(this);

        // Add Text box for M value.
        this.add(new JLabel("Max value of M(bits):"));
        mValueBitsTextBox = new JTextField("", 6);
        mValueBitsTextBox.setEditable(false);
        this.add(mValueBitsTextBox);

        gbc.weightx = 1.0;
        layout.setConstraints(encodeBitsPlus, gbc);
        this.add(encodeBitsPlus);

        gbc.gridwidth = GridBagConstraints.REMAINDER;
        layout.setConstraints(encodeBitsMinus, gbc);
        this.add(encodeBitsMinus);

        GridBagLayout imageGridbag = new GridBagLayout();
        GridBagConstraints imageGBC = new GridBagConstraints();

        imagePanel = new JPanel();
        imagePanel.setLayout(imageGridbag);

        JLabel hostImageLabel = new JLabel("Host Image :");
        // JLabel secretImageLabel = new JLabel("Secret image:");

        imagePanel.add(hostImageLabel);

        // imageGBC.gridwidth = GridBagConstraints.REMAINDER;
        // imageGridbag.setConstraints(secretImageLabel, imageGBC);
        // imagePanel.add(secretImageLabel);

        hostCanvas = new ImageCanvas(this.getHostImage());
        // secretCanvas = new ImageCanvas(this.getSecretImage());

        imagePanel.add(hostCanvas);
        // imagePanel.add(secretCanvas);

        gbc.gridwidth = GridBagConstraints.REMAINDER;
        layout.setConstraints(imagePanel, gbc);
        this.add(imagePanel);

        Steganography host = new Steganography(this.getHostImage());
        host.encode(this.getBits());
        hostCanvas.setImage(host.getImage());

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        this.setVisible(true);
    }

    public static void main(String[] args) {
        ImageHiding frame = new ImageHiding();
        frame.setVisible(true);
    }

    public class ImageCanvas extends JPanel {
        Image img;

        public void paintComponent(Graphics g) {
            g.drawImage(img, 0, 0, this);
        }

        public void setImage(Image img) {
            this.img = img;
        }

        public ImageCanvas(Image img) {
            this.img = img;

            this.setPreferredSize(new Dimension(img.getWidth(this), img.getHeight(this)));
        }
    }
}

class Steganography {
    BufferedImage image;
    byte[] textfileData = null;

    public void getMaskedImage(int bits) {
        int[] imageRGB = image.getRGB(0, 0, image.getWidth(null), image.getHeight(null), null, 0, image.getWidth(null));

        int maskBits = (int) (Math.pow(2, bits)) - 1 << (8 - bits);
        int mask = (maskBits << 24) | (maskBits << 16) | (maskBits << 8) | maskBits;

        for (int i = 0; i < imageRGB.length; i++) {
            imageRGB[i] = imageRGB[i] & mask;
        }

        image.setRGB(0, 0, image.getWidth(null), image.getHeight(null), imageRGB, 0, image.getWidth(null));
    }

    // Encode Image bits - append text bits to image bits according to RGB.
    public int encode(int encodeBits) {

        int[] imageRGB = image.getRGB(0, 0, image.getWidth(null), image.getHeight(null), null, 0, image.getWidth(null));
        int encodeByteMask = (int) (Math.pow(2, encodeBits)) - 1 << (8 - encodeBits);
        int encodeMask = (encodeByteMask << 24) | (encodeByteMask << 16) | (encodeByteMask << 8) | encodeByteMask;

        int decodeByteMask = ~(encodeByteMask >>> (8 - encodeBits)) & 0xFF;
        int hostMask = (decodeByteMask << 24) | (decodeByteMask << 16) | (decodeByteMask << 8) | decodeByteMask;
        int count = 0;
        // Appending text bits to all image pixel.
        for (int i = 0; i < imageRGB.length; i++) {
            // if (i == imageRGB.length-1){
            // System.out.println(i);
            // }
            int initBits, b, temp, endBits = 0;
            // If count is less than text file data
            if (count < textfileData.length) {
                // Left shift bits.
                initBits = textfileData[count];
                initBits = initBits << 16;

                if (count + 1 < textfileData.length) {
                    b = textfileData[count + 1];
                    b = b << 8;
                } else {
                    b = 0;
                }

                if (count + 2 < textfileData.length) {
                    endBits = textfileData[count + 2];
                } else {
                    endBits = 0;
                }

                temp = initBits | b | endBits; // Bitwise OR of three int
                // values.
                count++;
                // Bitwise AND with encodeMask and shift value and fill with
                // zero to 8 - encodebBits.
                int encodeData = (temp & encodeMask) >>> (8 - encodeBits);
                // Setting up new RGB value for each pixels in image array
                imageRGB[i] = (imageRGB[i] & hostMask) | (encodeData & ~hostMask);
            } // else when text file is too small then.
        }
        // Setting up new RGB for image
        image.setRGB(0, 0, image.getWidth(null), image.getHeight(null), imageRGB, 0, image.getWidth(null));
        // Return value of m to display on Panel.
        return (imageRGB.length * 3 * encodeBits) / 8; // m =
        // (imageRGB.length*3*encodeBits)/8

    }

    public Image getImage() {
        return image;
    }

    public Steganography(BufferedImage image) {
        this.image = image;
        try {
            textfileData = Files.readAllBytes(Paths.get("resource/secretText.txt"));
        } catch (IOException e) {
            System.out.println("Error in reading text file. Please check the text file.");
        }
    }
}
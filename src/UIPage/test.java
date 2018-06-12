package UIPage;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import UIPanel.Constants;

import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;


public class test {
	public static void main(String [] s) {
		JFrame j = new JFrame("sad");
		j.setSize(1000, 1000);
		j.getContentPane().add(new ImagePanel());
		j.setVisible(true);
	}
}


class ImagePanel extends JPanel {
    public ImagePanel() {
        try {
            File f = new File(Constants.MAINPAGEPATH);
            img = ImageIO.read(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Image img;

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (img != null) {
            g2.drawImage(img, 0, 0, getWidth(), getHeight(),
                         0, 0, img.getWidth(null), img.getHeight(null), null);
        }
    }
} 
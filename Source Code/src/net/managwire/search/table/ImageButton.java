package net.managwire.search.table;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;

/**
 *
 * @author Pride
 */
public class ImageButton extends JButton {

    private BufferedImage image;

    public ImageButton(String url) {
        try {
            this.image = ImageIO.read(ImageButton.class.getResourceAsStream(url));
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        int x = (getWidth() - image.getWidth()) / 2;
        int y = (getHeight() - image.getHeight()) / 2;
        g.drawImage(image, x, y, null);
    }
}

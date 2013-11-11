package net.managwire.search.table;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.swingx.graphics.GraphicsUtilities;
import org.jdesktop.swingx.graphics.ShadowRenderer;

/**
 *
 * @author Pride
 */
public class AdvertisementWrapper extends JPanel {

    private static final int SHADOW_SIZE = 10;
    private static final Color BORDER_COLOR = Color.BLACK;
    private static final Color SELECTED_BOTTOM_TAB_BACKGROUND_COLOR = new Color(0x252d30);

    private BufferedImage shadow;

    public AdvertisementWrapper() {
        super(new MigLayout("insets 0, fill"));
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);

        shadow = GraphicsUtilities.createCompatibleTranslucentImage(getWidth() - SHADOW_SIZE * 2, getHeight() - SHADOW_SIZE * 2);
        Graphics2D g2d = shadow.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRoundRect(0, 0, getWidth() - SHADOW_SIZE * 2, getHeight() - SHADOW_SIZE * 2, 30, 30);
        ShadowRenderer shadowRenderer = new ShadowRenderer(SHADOW_SIZE, 0.95f, BORDER_COLOR);
        shadow = shadowRenderer.createShadow(shadow);
        g2d.dispose();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        Shape clip;
        if (shadow != null) {
            clip=g.getClip();
            g.setClip(SHADOW_SIZE, SHADOW_SIZE, getWidth() - SHADOW_SIZE * 2, getHeight() - SHADOW_SIZE * 2);
            g2d.drawImage(shadow, 0, 0, null);
            //g.setClip(clip);
        }
        g2d.setColor(SELECTED_BOTTOM_TAB_BACKGROUND_COLOR);
        g2d.fillRoundRect(SHADOW_SIZE, SHADOW_SIZE, getWidth() - SHADOW_SIZE * 2, getHeight() - SHADOW_SIZE * 2, 30, 30);

        g2d.setStroke(new BasicStroke(3.0f));
        g2d.setColor(Color.WHITE);
        g2d.drawRoundRect(SHADOW_SIZE, SHADOW_SIZE, getWidth() - SHADOW_SIZE * 2, getHeight() - SHADOW_SIZE * 2, 30, 30);

        g2d.dispose();
    }
}

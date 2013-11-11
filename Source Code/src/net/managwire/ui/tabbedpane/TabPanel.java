package net.managwire.ui.tabbedpane;

import net.managwire.common.ThemeConstants;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.LinearGradientPaint;
import javax.swing.JPanel;

/**
 *
 * @author Thievery
 */
public class TabPanel extends JPanel {

    public TabPanel() {
    }

    public TabPanel(LayoutManager layout) {
        super(layout);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        LinearGradientPaint paint = new LinearGradientPaint(0, 0, 0, getHeight(),
                new float[]{0.0f, 1.0f}, new Color[]{ThemeConstants.TOP_GRADIENT_COLOR,
                    ThemeConstants.BOTTOM_GRADIENT_COLOR});
        g2d.setPaint(paint);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.dispose();
    }
}

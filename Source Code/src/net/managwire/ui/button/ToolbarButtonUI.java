package net.managwire.ui.button;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.LinearGradientPaint;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicButtonUI;

/**
 *
 * @author Thievery
 */
public class ToolbarButtonUI extends BasicButtonUI {

    private static final Color SHADOW = new Color(255, 255, 255, 110);
    private static final Color FOREGROUND = new Color(0xffffff);
    private static final Color NO_MASK_COLOR = new Color(0, 0, 0, 0);
    private static final Color PRESSED_MASK_COLOR = new Color(0, 0, 0, 116);
    private static final Color CENTER_FILL = new Color(0xa9b7c9);
    private static final Color INNER_BORDER_COLOR = new Color(0, 0, 0, 80);
    private static final Color OUTER_BORDER_COLOR = new Color(0, 0, 0, 130);
    private static final int OFFSET = 7;
    private static int BORDER_WIDTH = 2;

    @Override
    protected void installDefaults(AbstractButton b) {
        super.installDefaults(b);
        b.setHorizontalTextPosition(AbstractButton.CENTER);
        b.setVerticalTextPosition(AbstractButton.BOTTOM);
        b.setIconTextGap(0);
        b.setMargin(new Insets(0, 0, 0, 0));
        b.setFont(UIManager.getFont("Button.font").deriveFont(11.0f));
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setOpaque(false);
    }

    @Override
    protected void paintIcon(Graphics g, JComponent c, Rectangle iconRect) {
        AbstractButton b = (AbstractButton) c;
        BufferedImage icon = new BufferedImage(iconRect.width, iconRect.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) icon.getGraphics();
        b.getIcon().paintIcon(c, g2d, 0, 0);
        g2d.setComposite(AlphaComposite.SrcAtop);
        if (b.getModel().isArmed()) {
            g2d.setColor(PRESSED_MASK_COLOR);
        } else {
            g2d.setColor(NO_MASK_COLOR);
        }
        g2d.fillRect(0, 0, iconRect.width, iconRect.height);
        // g2d represents the graphics object for the buffred image
        // g rpresents the graphics for the component
        g.drawImage(icon, iconRect.x, iconRect.y, null);
        g2d.dispose();
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        AbstractButton b = (AbstractButton) c;
        if (b.getModel().isPressed()) {
            Graphics2D graphics = (Graphics2D) g.create();
            paintBackground(graphics, b);
            graphics.dispose();
        }
        super.paint(g, c);
    }

    protected void paintBackground(Graphics2D g2d, AbstractButton b) {
        // Fill int he center color
        LinearGradientPaint paint = new LinearGradientPaint(0, 0, 1f, b.getHeight(),
                new float[]{0.0f, 0.499f, 0.5f, 1.0f},
                new Color[]{NO_MASK_COLOR, CENTER_FILL, CENTER_FILL, NO_MASK_COLOR});
        g2d.setPaint(paint);
        g2d.fillRect(BORDER_WIDTH, 0, b.getWidth() - BORDER_WIDTH * 2, b.getHeight());

        // Draws the outer border

        LinearGradientPaint outerBorder = new LinearGradientPaint(0, 0, 1f, b.getHeight(),
                new float[]{0.0f, 0.499f, 0.5f, 1.0f},
                new Color[]{NO_MASK_COLOR, OUTER_BORDER_COLOR, OUTER_BORDER_COLOR, NO_MASK_COLOR});
        g2d.setPaint(outerBorder);
        g2d.drawLine(0, 0, 0, b.getHeight());
        g2d.drawLine(b.getWidth() - 1, 0, b.getWidth() - 1, b.getHeight());

        // Draws the inner border
        LinearGradientPaint innerBorder = new LinearGradientPaint(0, 0, 1f, b.getHeight(),
                new float[]{0.0f, 0.499f, 0.5f, 1.0f},
                new Color[]{NO_MASK_COLOR, INNER_BORDER_COLOR, INNER_BORDER_COLOR, NO_MASK_COLOR});
        g2d.setPaint(innerBorder);
        g2d.drawLine(1, 0, 1, b.getHeight());
        g2d.drawLine(b.getWidth() - 2, 0, b.getWidth() - 2, b.getHeight());

    }

    @Override
    protected void paintText(Graphics g, AbstractButton b, Rectangle textRect, String text) {
        if (b.getModel().isPressed()) {
            // Do Nothing
        } else {
            g.setColor(SHADOW);
            g.drawString(text, textRect.x, textRect.y + OFFSET + 1);
        }

        g.setColor(FOREGROUND);
        g.drawString(text, textRect.x, textRect.y + OFFSET);
    }
}

package net.managwire.components.field;

import net.managwire.common.ThemeConstants;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicTextFieldUI;

/**
 *
 * @author Thievery
 */
public class RoundTextField extends JTextField {

    private static final Color BORDER_FOREGROUND_COLOR = new Color(0x023c3b);
    private static final Color BACKGROUND_COLOR = new Color(0xfafade);
    private static final Color SHADOW = Color.BLACK;
    private static final Color NO_COLOR = new Color(0, 0, 0, 0);
    private static final int BORDER_SIZE = 7;

    public RoundTextField(int i) {
        super(i);
        setUI(new RoundToolbarTextFieldUI());
        setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2d = (Graphics2D) g.create();

        //Filling the back ground
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(BACKGROUND_COLOR);
        g2d.fillRoundRect(1, 1, getWidth() - 2, getHeight() - 2, ThemeConstants.ROUNDEDGE, ThemeConstants.ROUNDEDGE);

        //Filling the shadow
        LinearGradientPaint paint = new LinearGradientPaint(0, 0, 0, getHeight(),
                new float[]{0.0f, ((float) BORDER_SIZE / getHeight())},
                new Color[]{SHADOW, NO_COLOR});
        g2d.setPaint(paint);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        g2d.fillRoundRect(1, 1, getWidth() - 2, getHeight() - 2, ThemeConstants.ROUNDEDGE, ThemeConstants.ROUNDEDGE);

        //Drawing the border
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1f));
        g2d.setColor(BORDER_FOREGROUND_COLOR);
        g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, ThemeConstants.ROUNDEDGE, ThemeConstants.ROUNDEDGE);
        g2d.dispose();
        super.paintComponent(g);
    }

    class RoundToolbarTextFieldUI extends BasicTextFieldUI {

        @Override
        protected void paintBackground(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();

            //Filling the back ground
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(BACKGROUND_COLOR);
            g2d.fillRoundRect(1, 1, getWidth() - 2, getHeight() - 2, ThemeConstants.ROUNDEDGE, ThemeConstants.ROUNDEDGE);

            //Filling the shadow
            LinearGradientPaint paint = new LinearGradientPaint(0, 0, 0, getHeight(),
                    new float[]{0.0f, ((float) BORDER_SIZE / getHeight())},
                    new Color[]{SHADOW, NO_COLOR});
            g2d.setPaint(paint);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.15f));
            g2d.fillRoundRect(1, 1, getWidth() - 2, getHeight() - 2, ThemeConstants.ROUNDEDGE, ThemeConstants.ROUNDEDGE);

            //Drawing the border
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1f));
            g2d.setColor(BORDER_FOREGROUND_COLOR);
            g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, ThemeConstants.ROUNDEDGE, ThemeConstants.ROUNDEDGE);
            g2d.dispose();


        }
    }
}

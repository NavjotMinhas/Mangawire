package net.managwire.components.button;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Rectangle;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicButtonUI;
import net.managwire.common.ThemeConstants;

/**
 *
 * @author Pride
 */
public class NavigationButton extends JButton {

    private boolean useForeground;
    private static final Color LEFT_BORDER = new Color(255, 255, 255, 20);
    private static final Color RIGHT_BORDER = new Color(0, 0, 0, 125);
    private static final Color TOP_BACKGROUND_COLOR = new Color(0x141414);
    private static final Color BOTTOM_BACKGROUND_COLOR = new Color(0, 0, 0, 0);
    private static Color SELECTED_TOP_BORDER = new Color(0x030303);
    //The hex values are needed
    private static final Color SELECTED_INNER_SHADOW_COLOR_2 = new Color(0x252d30).darker().darker();
    private static final Color SELECTED_INNER_SHADOW_COLOR_1 = SELECTED_INNER_SHADOW_COLOR_2.darker();

    public NavigationButton(String text) {
        super(text);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setForeground(Color.WHITE);
        setFont(ThemeConstants.TAB_FONT.deriveFont(15.0f));
        setHorizontalAlignment(SwingConstants.CENTER);
        setUI(new ButtonUI());
    }

    @Override
    public Color getForeground() {
        if (useForeground) {
            return Color.WHITE;
        } else {
            return Color.BLACK;
        }
    }

    class ButtonUI extends BasicButtonUI {

        private LinearGradientPaint paint;

        @Override
        protected void installDefaults(AbstractButton button) {
            super.installDefaults(button);
            button.setBackground(new Color(0, 0, 0, 0));
            button.setOpaque(false);
        }

        @Override
        public void paint(Graphics g, JComponent c) {
            AbstractButton button = (AbstractButton) c;
            if (button.getModel().isPressed()) {
                paintButtonFocused(g, button);
            } else {
                g.setColor(LEFT_BORDER);
                g.drawLine(0, 1, 0, getHeight() - 2);
                g.setColor(RIGHT_BORDER);
                g.drawLine(getWidth() - 1, 1, getWidth() - 1, getHeight() - 2);
            }
            super.paint(g, c);
        }

        @Override
        protected void paintText(Graphics g, AbstractButton b, Rectangle textRect, String text) {
            if (b.getModel().isPressed()) {
                useForeground = false;
                g.translate(-1, -1);
                super.paintText(g, b, textRect, text);
                g.translate(1, 1);
            } else {
                useForeground = false;
                g.translate(1, 1);
                super.paintText(g, b, textRect, text);
                g.translate(-1, -1);

            }
            useForeground = true;
            super.paintText(g, b, textRect, text);
        }

        @Override
        protected void paintButtonPressed(Graphics g, AbstractButton b) {
            paintButtonFocused(g, b);
        }

        private void paintButtonFocused(Graphics g, AbstractButton b) {
            Graphics2D g2d = (Graphics2D) g.create();
            paint = new LinearGradientPaint(0, 0, 0, getHeight(),
                    new float[]{0.0f, 1.0f}, new Color[]{TOP_BACKGROUND_COLOR, BOTTOM_BACKGROUND_COLOR});
            g2d.setPaint(paint);
            g2d.fillRect(0, 0, b.getWidth(), b.getHeight());

            g2d.setColor(SELECTED_TOP_BORDER);
            g2d.drawLine(0, 0, getWidth(), 0);

            g2d.setColor(SELECTED_INNER_SHADOW_COLOR_1);
            g2d.drawLine(0, 1, getWidth(), 1);
            paint = new LinearGradientPaint(0, 0, 0, getHeight(),
                    new float[]{0.0f, 1.0f}, new Color[]{SELECTED_INNER_SHADOW_COLOR_1, BOTTOM_BACKGROUND_COLOR});
            g2d.setPaint(paint);
            g2d.drawLine(0, 1, 0, getHeight() - 2);
            g2d.drawLine(getWidth() - 1, 1, getWidth() - 1, getHeight() - 2);

            g2d.setColor(SELECTED_INNER_SHADOW_COLOR_2);
            g2d.drawLine(0, 2, getWidth(), 2);
            paint = new LinearGradientPaint(0, 0, 0, getHeight(),
                    new float[]{0.0f, 1.0f}, new Color[]{SELECTED_INNER_SHADOW_COLOR_2, BOTTOM_BACKGROUND_COLOR});
            g2d.setPaint(paint);
            g2d.drawLine(1, 1, 1, getHeight() - 2);
            g2d.drawLine(getWidth() - 2, 1, getWidth() - 2, getHeight() - 2);




            g2d.dispose();
        }
    }
}

package net.managwire.components.button;

import net.managwire.common.ThemeConstants;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicButtonUI;
import org.jdesktop.swingx.JXButton;
import org.pushingpixels.trident.Timeline;
import org.pushingpixels.trident.ease.Spline;

/**
 *
 * @author Thievery
 */
public class OceanButton extends JXButton {

    private Color topFillGradient = ThemeConstants.BUTTON_TOP_GRADIENT_COLOR;
    private Color bottomFillGradient = ThemeConstants.BUTTON_BOTTOM_GRADIENT_COLOR;
    private Timeline rolloverTimeline;
    private boolean useForeground;

    /* Border constants. */
    private static final Color BORDER_COLOR = new Color(0x0acecc);

    /* Margin constants. */
    private static final int TOP_AND_BOTTOM_MARGIN = 2;
    private static final int LEFT_AND_RIGHT_MARGIN = 16;

    public OceanButton(String str) {
        super(str);
        setUI(new ButtonUI());
        setFont(ThemeConstants.TAB_FONT.deriveFont(12.0f));
    }

    public OceanButton(String str,Icon icon) {
        super(str,icon);
        setUI(new ButtonUI());
        setFont(ThemeConstants.TAB_FONT.deriveFont(12.0f));
    }

    public void setTopFillGradient(Color c) {
        this.topFillGradient = c;
        repaint();

    }

    public void setBottomFillGradient(Color c) {
        this.bottomFillGradient = c;
        repaint();
    }

    @Override
    public Color getForeground() {
        if (useForeground) {
            return Color.WHITE;
        } else {
            return Color.GRAY;
        }
    }

    class ButtonUI extends BasicButtonUI {

        @Override
        protected void installDefaults(AbstractButton b) {
            super.installDefaults(b);
            b.setContentAreaFilled(false);
            b.setFocusPainted(false);
            b.setBorder(BorderFactory.createEmptyBorder(TOP_AND_BOTTOM_MARGIN,
                    LEFT_AND_RIGHT_MARGIN, TOP_AND_BOTTOM_MARGIN, LEFT_AND_RIGHT_MARGIN));
            b.setFont(UIManager.getFont("Button.font").deriveFont(12.0f));
            b.setOpaque(false);
            b.setForeground(Color.WHITE);
            b.setHorizontalAlignment(SwingConstants.CENTER);
            b.setVerticalAlignment(SwingConstants.CENTER);
            rolloverTimeline = new Timeline(b);
            rolloverTimeline.setDuration(500);
            rolloverTimeline.setEase(new Spline(0.8f));
            rolloverTimeline.addPropertyToInterpolate("topFillGradient", ThemeConstants.BUTTON_TOP_GRADIENT_COLOR,
                    ThemeConstants.BUTTON_TOP_GRADIENT_COLOR.darker());
            rolloverTimeline.addPropertyToInterpolate("bottomFillGradient", ThemeConstants.BUTTON_BOTTOM_GRADIENT_COLOR,
                    ThemeConstants.BUTTON_BOTTOM_GRADIENT_COLOR.darker());
        }

        @Override
        protected void installListeners(AbstractButton b) {
            super.installListeners(b);
            b.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseEntered(MouseEvent e) {
                    rolloverTimeline.play();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    rolloverTimeline.playReverse();
                }
            });
        }

        @Override
        public void paint(Graphics g, JComponent c) {
            paintBackground(g);
            super.paint(g, c);
        }

        public void paintBackground(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            RadialGradientPaint graident = new RadialGradientPaint(new Point(getWidth() / 2, getHeight() / 2 - getWidth()), getWidth(),
                    new float[]{0.98f, 1.0f}, new Color[]{topFillGradient, bottomFillGradient});
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setPaint(graident);
            g2d.fillRoundRect(1, 1, getWidth() - 2, getHeight() - 2, ThemeConstants.ROUNDEDGE, ThemeConstants.ROUNDEDGE);
            g2d.setColor(BORDER_COLOR);
            g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, ThemeConstants.ROUNDEDGE, ThemeConstants.ROUNDEDGE);
            g2d.dispose();
        }

        @Override
        protected void paintText(Graphics g, AbstractButton b, Rectangle textRect, String text) {
            if (b.getModel().isPressed()) {
                // Do nothing
            } else {
                useForeground = false;
                g.translate(0, 1);
                super.paintText(g, b, textRect, text);
                g.translate(0, -1);

            }
            useForeground = true;
            super.paintText(g, b, textRect, text);
        }
    }
}

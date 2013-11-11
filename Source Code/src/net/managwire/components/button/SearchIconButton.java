package net.managwire.components.button;

/**
 *
 * @author Thievery
 */
import net.managwire.common.ThemeConstants;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicButtonUI;
import org.jdesktop.swingx.JXButton;
import org.pushingpixels.trident.Timeline;
import org.pushingpixels.trident.ease.Spline;
import org.pushingpixels.trident.swing.SwingRepaintCallback;

/**
 *
 * @author Thievery
 */
public class SearchIconButton extends JXButton {

    private Color topFillGradient = new Color(0xf8f8f8);
    private Color bottomFillGradient = new Color(0x656565);
    private Timeline rolloverTimeline;


    /* Border constants. */
    private static final Color BORDER_COLOR = new Color(0x232323);

    public SearchIconButton(Icon icon) {
        super(icon);
        setUI(new ButtonUI());
    }

    public void setTopFillGradient(Color c) {
        this.topFillGradient = c;
    }

    public void setBottomFillGradient(Color c) {
        this.bottomFillGradient = c;
    }


    class ButtonUI extends BasicButtonUI {

        @Override
        protected void installDefaults(AbstractButton b) {
            super.installDefaults(b);
            b.setContentAreaFilled(false);
            b.setFocusPainted(false);
            b.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
            b.setOpaque(false);
            b.setHorizontalAlignment(SwingConstants.CENTER);
            b.setVerticalAlignment(SwingConstants.CENTER);
            rolloverTimeline = new Timeline(b);
            rolloverTimeline.setDuration(500);
            rolloverTimeline.setEase(new Spline(0.2f));
            rolloverTimeline.addPropertyToInterpolate("topFillGradient", new Color(0xf8f8f8),
                    new Color(0xd4d4d4));
            rolloverTimeline.addPropertyToInterpolate("bottomFillGradient", new Color(0x656565),
                    new Color(0x7a7a7a));
            rolloverTimeline.addCallback(new SwingRepaintCallback(b));
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
            LinearGradientPaint graident = new LinearGradientPaint(0, 0, 0, getHeight(),
                    new float[]{0.0f, 1.0f}, new Color[]{topFillGradient, bottomFillGradient});
            g2d.setPaint(graident);
            g2d.fillRoundRect(1, 1, getWidth()-2, getHeight()-2, ThemeConstants.ROUNDEDGE, ThemeConstants.ROUNDEDGE);
            g2d.setColor(BORDER_COLOR);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, ThemeConstants.ROUNDEDGE, ThemeConstants.ROUNDEDGE);
            g2d.dispose();
        }

    }
}


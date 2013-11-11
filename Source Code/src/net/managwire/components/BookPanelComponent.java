package net.managwire.components;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.geom.RoundRectangle2D;
import org.jdesktop.swingx.JXComponent;
import org.pushingpixels.trident.Timeline;
import org.pushingpixels.trident.swing.SwingRepaintCallback;

/**
 *
 * @author Thievery
 */
public class BookPanelComponent extends JXComponent {

    float alpha;

    public BookPanelComponent() {
        setOpaque(false);
        this.addHierarchyListener(new HierarchyListener() {

            public void hierarchyChanged(HierarchyEvent e) {
                Timeline timeline = new Timeline(BookPanelComponent.this);
                timeline.addPropertyToInterpolate("alpha", 0.0f, 0.85f);
                //CallsBack on every pulse to the swing repaint manager
                timeline.addCallback(new SwingRepaintCallback(BookPanelComponent.this));
                timeline.setDuration(500);
                timeline.play();
            }
        });
    }

    public void setAlpha(float f) {
        this.alpha = f;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setStroke(new BasicStroke(1.0f));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.DARK_GRAY);
        g2d.setComposite(AlphaComposite.SrcOver.derive(alpha));
        g2d.fillRoundRect(0, 0, getWidth() - 1,
                getHeight() - 1, 32, 32);
        g2d.setColor(Color.gray);
        g2d.drawRoundRect(0, 0, getWidth() - 1,
                getHeight() - 1, 32, 32);

        g2d.dispose();

    }
}

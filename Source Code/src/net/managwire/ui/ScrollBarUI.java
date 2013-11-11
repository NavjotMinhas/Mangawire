package net.managwire.ui;

import net.managwire.ui.button.ArrowButton;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import javax.swing.JButton;

import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class ScrollBarUI extends BasicScrollBarUI {

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(new Color(0x35332f));

        if (((JScrollBar) c).getOrientation() == SwingConstants.HORIZONTAL) {
            g2d.fillRect(trackBounds.x, trackBounds.y, trackBounds.width,
                   trackBounds.height);
            g2d.setColor(new Color(0x292724));
            g2d.drawRect(trackBounds.x, trackBounds.y, trackBounds.width-1,
                    trackBounds.height-1);
        } else {
            g2d.fillRect(trackBounds.x, trackBounds.y, trackBounds.width,
                   trackBounds.height);
            g2d.setColor(new Color(0x292724));
            g2d.drawRect(trackBounds.x, trackBounds.y, trackBounds.width-1,
                    trackBounds.height-1);

        }
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        Graphics2D g2d = (Graphics2D) g;
        System.out.println(c.getX());
        System.out.println(c.getY());

        LinearGradientPaint paint=new LinearGradientPaint(0,0,thumbBounds.width,0,
                new float[]{0.0f,1.0f},new Color[]{new Color(0xaaa393),new Color(0x7d786c)});
        g2d.setPaint(paint);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        if (((JScrollBar) c).getOrientation() == SwingConstants.HORIZONTAL) {
            g2d.fillRect(thumbBounds.x + 1, thumbBounds.y + 1,
                    thumbBounds.width - 2, thumbBounds.height - 2);
        } else {
            g2d.fillRect(thumbBounds.x + 1, thumbBounds.y + 1,
                    thumbBounds.width - 2, thumbBounds.height - 2);
        }
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return new ArrowButton(orientation);
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return new ArrowButton(orientation);
    }

}

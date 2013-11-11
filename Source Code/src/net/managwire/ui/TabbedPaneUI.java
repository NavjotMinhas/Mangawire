/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.managwire.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.LinearGradientPaint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

/**
 *
 * @author user
 */
public class TabbedPaneUI extends BasicTabbedPaneUI {

    private int Roundedge = 5;
    private static final int HEIGHT = 32;
    private static final float OFFSET = 2f;
    private static final Color shadowColor = new Color(255, 255, 255, 110);
    private static final Color foreground = new Color(0xFFFFFF);

    private Shape createTabShape(int x, int y, int w, int h) {
        GeneralPath shape = new GeneralPath();
        shape.moveTo(x, y + h);
        shape.lineTo(x, y + OFFSET);
        shape.quadTo(x, y + OFFSET, x + OFFSET, y);
        shape.lineTo(x + w - OFFSET, y);
        shape.quadTo(x + w - OFFSET, y, x + w, y + OFFSET);
        shape.lineTo(x + w, y + h);
        shape.closePath();
        return shape;

    }

    @Override
    protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
    }

    @Override
    protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) {
        // Do nothing
    }

    @Override
    protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
        paintBorder(g);
        if (isSelected) {
            Graphics2D g2d = (Graphics2D) g;
            LinearGradientPaint paint = new LinearGradientPaint(0, 0, 0,
                    h, new float[]{0.0f, 0.49f, 0.5f, 1.0f}, new Color[]{
                        new Color(0x05b2e9), new Color(0x4271e7),
                        new Color(0x0841ce), new Color(0x18c7ff)});
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setPaint(paint);
            g2d.fill(createTabShape(x, y, w, h));
            g2d.setColor(Color.BLACK);
            g2d.draw(createTabShape(x, y, w, h));
        }
    }

    @Override
    protected void paintText(Graphics g, int tabPlacement, Font font, FontMetrics metrics, int tabIndex, String title, Rectangle textRect, boolean isSelected) {
        g.setFont(font);
        g.setColor(shadowColor);
        g.drawString(title, textRect.x, textRect.y + 3*textRect.height/4);
        g.setColor(foreground);
        g.drawString(title, textRect.x, textRect.y + 3*textRect.height/4-1);

    }

    @Override
    protected int calculateTabAreaHeight(int tabPlacement, int horizRunCount, int maxTabHeight) {
        return HEIGHT;
    }

    @Override
    protected void paintTabArea(Graphics g, int tabPlacement, int selectedIndex) {
        Graphics2D g2d = (Graphics2D) g;
        LinearGradientPaint paint = new LinearGradientPaint(0, 0, 0,
                HEIGHT, new float[]{0.0f, 0.49f, 0.5f, 1.0f}, new Color[]{
                    new Color(0x787b7d),
                    new Color(0x35393d),
                    new Color(0x000000),
                    new Color(0x0c0c0c)});
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setPaint(paint);
        g2d.fillRect(0, 0, tabPane.getWidth(), HEIGHT);
        super.paintTabArea(g, tabPlacement, selectedIndex);
    }

    protected void paintBorder(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(0x0c0c0c));
        g2d.fillRect(0, HEIGHT + 2, tabPane.getWidth(), tabPane.getHeight() - HEIGHT);
        g2d.setColor(new Color(0x9fa2a4));
        g2d.fillRect(0, HEIGHT, tabPane.getWidth(), 2);
        g2d.setColor(new Color(0x484b4d));
        g2d.fillRect(0, HEIGHT + 2, tabPane.getWidth(), 2);
    }

    @Override
    protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
        // Do Nothing
    }

    @Override
    protected void installDefaults() {
        super.installDefaults();
        //TO DO needs code to compenstae for applications name
        tabAreaInsets.left = 140;
        tabInsets = new Insets(10, 20, 0, 20);
        tabAreaInsets.top = 5;
    }
}

package net.managwire.ui.tabbedpane;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

/**
 *
 * @author Pride
 */
public class SearchResultUI extends BasicTabbedPaneUI {

    private static final int TAB_HEIGHT = 30;
    private static final Color BORDER_COLOR = new Color(0x074b4b);
    private static final Color SELECTED_TOP_TAB_BACKGROUND_COLOR = new Color(0x333d40);
    private static final Color SELECTED_BOTTOM_TAB_BACKGROUND_COLOR = new Color(0x252d30);
    private static final Color SELECTED_TAB_BORDER_COLOR = new Color(0x074b4b);
    private static final Color SELECTED_PANE_TOP_COLOR = new Color(0x242d30);
    private int selectedX = 0;
    private int selectedW = 0;

    @Override
    protected void installDefaults() {
        super.installDefaults();
        tabPane.setForeground(Color.WHITE);
        selectedTabPadInsets = new Insets(0, 0, 0, 0);
        tabInsets = selectedTabPadInsets;
        tabAreaInsets.right = 0;
        tabAreaInsets.top = 0;
        tabAreaInsets.bottom = 0;
     }

    @Override
    protected int getTabLabelShiftY(int tabPlacement, int tabIndex, boolean isSelected) {
        return 0;
    }

    @Override
    protected Insets getContentBorderInsets(int tabPlacement) {
        return new Insets(0, 0, 0, 0);
    }

    @Override
    protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight) {
        return TAB_HEIGHT;
    }

    @Override
    protected int calculateTabWidth(int tabPlacement, int tabIndex, FontMetrics metrics) {
        return super.calculateTabWidth(tabPlacement, tabIndex, metrics) + 5;
    }

    @Override
    protected void paintTabArea(Graphics g, int tabPlacement, int selectedIndex) {
        if (tabPane.getTabCount() > 0) {
            Graphics graphics = (Graphics) g.create();
            graphics.setColor(BORDER_COLOR);
            graphics.drawLine(0, TAB_HEIGHT - 1, tabPane.getWidth() - 2, TAB_HEIGHT - 1);
            graphics.dispose();
            super.paintTabArea(g, tabPlacement, selectedIndex);
        }
    }

    @Override
    protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        x += 4;
        w -= 4;
        //Creates shape for the tab*********************
        GeneralPath shape = new GeneralPath();
        shape.moveTo(x, y + 100 / 8);
        shape.quadTo(x, y, x + 100 / 8, y);
        shape.lineTo(x + w - 100 / 8, y);
        shape.quadTo(x + w - 1, y, x + w - 1, y + 100 / 8);
        shape.lineTo(x + w - 1, y + h);
        shape.lineTo(x, y + h);
        shape.closePath();
        //*********************************************
        if (isSelected) {
            RadialGradientPaint p =
                    new RadialGradientPaint(new Point2D.Float(x + w / 2, y - w + w / 7), w, new float[]{0.95f, 1.0f},
                    new Color[]{SELECTED_TOP_TAB_BACKGROUND_COLOR, SELECTED_BOTTOM_TAB_BACKGROUND_COLOR});
            g2d.setPaint(p);
            g2d.fill(shape);
        } else {
            RadialGradientPaint p =
                    new RadialGradientPaint(new Point2D.Float(x + w / 2, y - w + w / 7), w, new float[]{0.95f, 1.0f},
                    new Color[]{SELECTED_TOP_TAB_BACKGROUND_COLOR.darker(), SELECTED_BOTTOM_TAB_BACKGROUND_COLOR.darker()});
            g2d.setPaint(p);
            g2d.fill(shape);
            g2d.setColor(BORDER_COLOR);
            g2d.drawLine(0, TAB_HEIGHT - 1, tabPane.getWidth() - 2, TAB_HEIGHT - 1);
        }

        //Draws border
        if (isSelected) {
            g2d.setColor(SELECTED_TAB_BORDER_COLOR);
            g2d.draw(shape);
        }
        g2d.dispose();
    }

    @Override
    protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
        //Do nothing
    }

    @Override
    protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) {
        //Do nothing
    }
}

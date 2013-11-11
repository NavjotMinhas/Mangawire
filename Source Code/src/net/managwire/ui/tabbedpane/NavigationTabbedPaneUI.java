package net.managwire.ui.tabbedpane;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import javax.swing.Icon;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

/**
 *
 * @author Thievery
 */
public class NavigationTabbedPaneUI extends BasicTabbedPaneUI {

    private static final Color SEPARATOR_BORDER_COLOR = new Color(0x13181c);
    private static final Color OUTSIDE_BORDER_COLOR = new Color(0x1f1f1f);
    private static final Color INSIDE_BORDER_COLOR = new Color(0x4e4e4e);
    private static final Color SHADOW_COLOR = new Color(0x3c3c3c);
    private static final Color FOREGROUND_COLOR = new Color(0xeff7e2);
    private static final Color TAB_AREA_COLOR = new Color(0x363f42);
    private static final Color SELECTED_TAB_COLOR = new Color(0x2f2f2f);
    private static final Color SELETCED_TAB_BORDER_COLOR = new Color(0x333333);
    private static final int ICON_LEFT_OFFSET = 30;
    private static final int TAB_AREA_WIDTH = 200;
    private static final int TAB_X_OFFSET = 2;
    private static final int TAB_Y_OFFSET = 5;
    private static final int SEPARATOR_BORDER_WIDTH = 20;
    private static final int BORDER_WIDTH = 2;
    private static final int SELECTED_TAB_X_OFFSET = 5;
    private static final int SELECTED_TAB_Y_OFFSET = 2;

    @Override
    protected void installDefaults() {
        super.installDefaults();
        tabPane.setTabPlacement(JTabbedPane.LEFT);
        tabAreaInsets = new Insets(TAB_X_OFFSET, 10, TAB_X_OFFSET, 0);
        selectedTabPadInsets = new Insets(0, 0, 0, 0);
        tabInsets = selectedTabPadInsets;
        tabPane.setFont(UIManager.getFont("Button.font").deriveFont(11.0f));
    }

    @Override
    protected int getTabLabelShiftX(int tabPlacement, int tabIndex, boolean isSelected) {
        return 0;
    }

    @Override
    protected int calculateTabWidth(int tabPlacement, int tabIndex, FontMetrics metrics) {
        return TAB_AREA_WIDTH - TAB_X_OFFSET * 2;
    }

    @Override
    protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight) {
        return super.calculateTabHeight(tabPlacement, tabIndex, fontHeight) + TAB_Y_OFFSET * 2;
    }

    @Override
    protected int calculateTabAreaWidth(int tabPlacement, int vertRunCount, int maxTabWidth) {
        return TAB_AREA_WIDTH;
    }

    @Override
    protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
        paintBorder(g);
    }

    @Override
    protected Insets getContentBorderInsets(int tabPlacement) {
        return new Insets(0, SEPARATOR_BORDER_WIDTH, 0, 0);
    }

    @Override
    protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) {
        //We Do not want the focus indicator painted
    }

    protected void paintBorder(Graphics g) {
        Graphics graphics = g.create();
        graphics.setColor(SEPARATOR_BORDER_COLOR);
        graphics.fillRect(TAB_AREA_WIDTH, 0, tabPane.getWidth() - TAB_AREA_WIDTH, tabPane.getHeight());
        graphics.dispose();
    }

    @Override
    protected void paintText(Graphics g, int tabPlacement, Font font, FontMetrics metrics, int tabIndex, String title, Rectangle textRect, boolean isSelected) {
        Graphics graphics = (Graphics) g.create();
        graphics.setColor(SHADOW_COLOR);
        graphics.drawString(title, ICON_LEFT_OFFSET * 2, textRect.y + 3 * textRect.height / 4);
        graphics.setColor(FOREGROUND_COLOR);
        graphics.drawString(title, ICON_LEFT_OFFSET * 2, textRect.y + 3 * textRect.height / 4 - 1);
        graphics.dispose();
    }

    @Override
    protected void paintIcon(Graphics g, int tabPlacement, int tabIndex, Icon icon, Rectangle iconRect, boolean isSelected) {
        iconRect.x = ICON_LEFT_OFFSET;
        super.paintIcon(g, tabPlacement, tabIndex, icon, iconRect, isSelected);
    }

    @Override
    protected void paintTabArea(Graphics g, int tabPlacement, int selectedIndex) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(TAB_AREA_COLOR);
        g2d.fillRect(0, 0, TAB_AREA_WIDTH, tabPane.getHeight());
        g2d.setStroke(new BasicStroke(BORDER_WIDTH));
        g2d.setColor(OUTSIDE_BORDER_COLOR);
        g2d.drawRect(0, 0, TAB_AREA_WIDTH, tabPane.getHeight());
        g2d.setColor(INSIDE_BORDER_COLOR);
        g2d.drawRect(BORDER_WIDTH, BORDER_WIDTH, TAB_AREA_WIDTH - BORDER_WIDTH * 2, tabPane.getHeight() - BORDER_WIDTH * 2);
        g2d.dispose();
        super.paintTabArea(g, tabPlacement, selectedIndex);

    }

    @Override
    protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
        if (isSelected) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(SELECTED_TAB_COLOR);
            g2d.fillRoundRect(x + SELECTED_TAB_X_OFFSET, y + SELECTED_TAB_Y_OFFSET, w - SELECTED_TAB_X_OFFSET * 2, h - SELECTED_TAB_Y_OFFSET * 2, h - 15, h - 15);
            g2d.setColor(SELETCED_TAB_BORDER_COLOR);
            g2d.drawRoundRect(x + SELECTED_TAB_X_OFFSET, y + SELECTED_TAB_Y_OFFSET, w - SELECTED_TAB_X_OFFSET * 2, h - SELECTED_TAB_Y_OFFSET * 2, h - 15, h - 15);
        }
    }

    @Override
    protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
        // Do not paint the tab border it will be handled in the tab background method
    }


}

package net.managwire.ui.tabbedpane;

import net.managwire.common.ThemeConstants;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import net.managwire.library.LibraryPanel;
import net.managwire.search.table.SearchPanel;
import net.mangawire.p2p.content.BootStrapping;

/**
 *
 * @author Thievery
 */
public class NavigatorTabbedPaneUI extends BasicTabbedPaneUI {

    private static final int TAB_HEIGHT = 45;
    private static final int TAB_WIDTH = 170;
    private static final int BORDER_WIDTH = 2;
    private static final Color BORDER_COLOR = new Color(0x074b4b);
    private static final Color TOP_TAB_BACKGROUND_COLOR = new Color(0xade65a);
    private static final Color BOTTOM_TAB_BACKGROUND_COLOR = new Color(0x85d80e);
    private static final Color SELECTED_TOP_TAB_BACKGROUND_COLOR = new Color(0x333d40);
    private static final Color SELECTED_BOTTOM_TAB_BACKGROUND_COLOR = new Color(0x252d30);
    private static final Color TAB_BORDER_COLOR = new Color(0x4a7c03);
    private static final Color SELECTED_TAB_BORDER_COLOR = new Color(0x074b4b);
    private static final Color SELECTED_PANE_TOP_COLOR = new Color(0x242d30);
    private static final int BEZIER_CONTROL_FACTOR = 30;
    private int selectedX = 0;
    private int selectedW = 0;

    @Override
    protected void installDefaults() {
        super.installDefaults();
        tabPane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        tabPane.setForeground(Color.WHITE);
        tabPane.setFont(ThemeConstants.TAB_FONT);
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
        return new Insets(1, BORDER_WIDTH, BORDER_WIDTH, BORDER_WIDTH);
    }

    @Override
    protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight) {
        return TAB_HEIGHT;
    }

    @Override
    protected int calculateTabWidth(int tabPlacement, int tabIndex, FontMetrics metrics) {
        return TAB_WIDTH;
    }

    @Override
    protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(BORDER_COLOR);
        g2d.drawRect(1, TAB_HEIGHT + 1, tabPane.getWidth() - 3, tabPane.getHeight() - TAB_HEIGHT - 3);
        g2d.dispose();
    }

    @Override
    protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) {
        // Do not paint the focus indicator
    }

    @Override
    protected void paintTabArea(Graphics g, int tabPlacement, int selectedIndex) {
        super.paintTabArea(g, tabPlacement, selectedIndex);

        // Draws the top border
        Graphics graphics = (Graphics) g.create();
        graphics.setColor(BORDER_COLOR);
        graphics.drawLine(1, TAB_HEIGHT, selectedX, TAB_HEIGHT);
        graphics.setColor(SELECTED_PANE_TOP_COLOR);
        graphics.drawLine(selectedX + 1, TAB_HEIGHT, selectedX + selectedW - 2, TAB_HEIGHT);
        graphics.setColor(BORDER_COLOR);
        graphics.drawLine(selectedX + selectedW - 1, TAB_HEIGHT, tabPane.getWidth() - 2, TAB_HEIGHT);
        graphics.dispose();
    }

    @Override
    protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
        Graphics2D g2d = (Graphics2D) g.create();
        if (isSelected) {
            if (tabIndex == tabPane.getTabCount() - 1) {
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GeneralPath shape = new GeneralPath();
                shape.moveTo(x, y + h - 1);
                shape.curveTo(x + BEZIER_CONTROL_FACTOR, y + h - 1, x + w / 4 - BEZIER_CONTROL_FACTOR, y + 1, x + w / 4, y);
                shape.lineTo(x + w - 1, y);
                shape.lineTo(x + w - 1, y + h - 1);
                shape.closePath();
                RadialGradientPaint p =
                        new RadialGradientPaint(new Point2D.Float(x + w / 2, y - w + w / 7), w, new float[]{0.95f, 1.0f},
                        new Color[]{SELECTED_TOP_TAB_BACKGROUND_COLOR, SELECTED_BOTTOM_TAB_BACKGROUND_COLOR});
                g2d.setPaint(p);
                g2d.fill(shape);
                g2d.setColor(SELECTED_TAB_BORDER_COLOR);
                g2d.draw(shape);

                //Draws over the bottom border, making the bottom border transparent
                g2d.setColor(SELECTED_BOTTOM_TAB_BACKGROUND_COLOR);
                g2d.drawLine(x + 6, y + h - 1, x + w - 2, y + h - 1);
                //***************************************************************

                selectedX = x;
                selectedW = w;
            } else if (tabIndex == 0) {
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                //Creates shape for the tab*********************
                GeneralPath shape = new GeneralPath();
                shape.moveTo(x, y);
                shape.lineTo(x + 7 * w / 8, y);
                shape.quadTo(x + w - 1, y, x + w - 1, y + w / 8);
                shape.lineTo(x + w - 1, y + h - 1);
                shape.lineTo(x, y + h - 1);
                shape.closePath();
                //*********************************************

                //Draws the tab********************************
                RadialGradientPaint p =
                        new RadialGradientPaint(new Point2D.Float(x + w / 2, y - w + w / 7), w, new float[]{0.95f, 1.0f},
                        new Color[]{SELECTED_TOP_TAB_BACKGROUND_COLOR, SELECTED_BOTTOM_TAB_BACKGROUND_COLOR});
                g2d.setPaint(p);
                g2d.fill(shape);
                //*********************************************

                //Draws the border****************************
                g2d.setColor(SELECTED_TAB_BORDER_COLOR);
                g2d.draw(shape);
                //*********************************************

                //Draws over the bottom border, making the bottom border transparent
                g2d.setColor(SELECTED_BOTTOM_TAB_BACKGROUND_COLOR);
                g2d.drawLine(x + 1, y + h - 1, x + w - 2, y + h - 1);
                //***************************************************************

                selectedX = x;
                selectedW = w;
            } else {
                RadialGradientPaint p =
                        new RadialGradientPaint(new Point2D.Float(x + w / 2, y - w + w / 7), w, new float[]{0.95f, 1.0f},
                        new Color[]{SELECTED_TOP_TAB_BACKGROUND_COLOR, SELECTED_BOTTOM_TAB_BACKGROUND_COLOR});
                g2d.setPaint(p);
                g2d.fillRect(x + 1, y + 1, w - 2, h - 1);

                // Draws border*****************************
                g2d.setColor(SELECTED_TAB_BORDER_COLOR);
                g2d.drawLine(x + 1, y, x + w - 2, y);
                g2d.drawLine(x, y, x, y + h);
                g2d.drawLine(x + w - 1, y, x + w - 1, y + h);

                //********************************************
                selectedX = x;
                selectedW = w;
            }
        } else {
            if (tabIndex == tabPane.getTabCount() - 1) {
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GeneralPath shape = new GeneralPath();
                shape.moveTo(x, y + h - 1);
                shape.curveTo(x + BEZIER_CONTROL_FACTOR, y + h - 1, x + w / 4 - BEZIER_CONTROL_FACTOR, y + 1, x + w / 4, y);
                shape.lineTo(x + w - 1, y);
                shape.lineTo(x + w - 1, y + h - 1);
                shape.closePath();
                RadialGradientPaint p =
                        new RadialGradientPaint(new Point2D.Float(x + w / 2, y - w + w / 7), w, new float[]{0.95f, 1.0f},
                        new Color[]{TOP_TAB_BACKGROUND_COLOR, BOTTOM_TAB_BACKGROUND_COLOR});
                g2d.setPaint(p);
                g2d.fill(shape);
                g2d.setColor(TAB_BORDER_COLOR);
                g2d.draw(shape);
            } else if (tabIndex == 0) {
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GeneralPath shape = new GeneralPath();
                shape.moveTo(x, y);
                shape.lineTo(x + 7 * w / 8, y);
                shape.quadTo(x + w - 1, y, x + w - 1, y + w / 8);
                shape.lineTo(x + w - 1, y + h - 1);
                shape.lineTo(x, y + h - 1);
                shape.closePath();
                RadialGradientPaint p =
                        new RadialGradientPaint(new Point2D.Float(x + w / 2, y - w + w / 7), w, new float[]{0.95f, 1.0f},
                        new Color[]{TOP_TAB_BACKGROUND_COLOR, BOTTOM_TAB_BACKGROUND_COLOR});
                g2d.setPaint(p);
                g2d.fill(shape);
                g2d.setColor(TAB_BORDER_COLOR);
                g2d.draw(shape);
            } else {
                RadialGradientPaint p =
                        new RadialGradientPaint(new Point2D.Float(x + w / 2, y - w + w / 7), w, new float[]{0.95f, 1.0f},
                        new Color[]{TOP_TAB_BACKGROUND_COLOR, BOTTOM_TAB_BACKGROUND_COLOR});
                g2d.setPaint(p);
                g2d.fillRect(x + 1, y + 1, w - 2, h - 2);
                g2d.setColor(TAB_BORDER_COLOR);
                g2d.drawRect(x, y, w - 1, h - 1);
            }
        }
        g2d.dispose();
    }

    @Override
    protected void paintText(Graphics g, int tabPlacement, Font font, FontMetrics metrics, int tabIndex, String title, Rectangle textRect, boolean isSelected) {
        if (tabIndex == tabPane.getTabCount() - 1) {
            textRect.x = textRect.x + 15;
            super.paintText(g, tabPlacement, font, metrics, tabIndex, title, textRect, isSelected);
        } else {
            super.paintText(g, tabPlacement, font, metrics, tabIndex, title, textRect, isSelected);
        }
    }

    @Override
    protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
        //Do not paint the tab border
    }

    public static void main(String[] args) {
        BootStrapping.BootStrap_Network();
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                JFrame frame = new JFrame();
                frame.getContentPane().setBackground(new Color(0x111619));
                JTabbedPane tabbedPane = new JTabbedPane();
                tabbedPane.addTab("Forums", new SearchPanel());
                tabbedPane.addTab("Library", new LibraryPanel());
                tabbedPane.addTab("Downloads", new TabPanel());
                tabbedPane.addTab("Home", new TabPanel());
                tabbedPane.setUI(new NavigatorTabbedPaneUI());
                frame.add(tabbedPane);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 600);
                frame.setVisible(true);
            }
        });
    }
}

package net.managwire.ui;

import net.managwire.common.ThemeConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicTableUI;

/**
 *
 * @author Thievery
 */
public class SearchTableUI extends BasicTableUI {

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        table.setOpaque(false);
        // TODO save defaults.
        table.setShowVerticalLines(true);
        table.setShowHorizontalLines(false);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        Graphics2D g2d = (Graphics2D) g.create();
        LinearGradientPaint paint = new LinearGradientPaint(0, 0, 0, table.getHeight(),
                new float[]{0.0f, 1.0f}, new Color[]{ThemeConstants.TOP_GRADIENT_COLOR,
                    ThemeConstants.BOTTOM_GRADIENT_COLOR});
        g2d.setPaint(paint);
        g2d.fillRect(0, 0, table.getWidth(), table.getHeight());
        g2d.dispose();
        super.paint(g, c);
    }
}

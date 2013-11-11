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
public class TableUI extends BasicTableUI {


    @Override
    public void installUI(JComponent c) {
        super.installUI(c);

        // TODO save defaults.
        table.setForeground(Color.LIGHT_GRAY);
        table.setShowVerticalLines(true);

        //Quick hack we need to fix this in order for p2p to come out
        table.setRowSelectionAllowed(false);
        
        table.setShowHorizontalLines(false);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        Graphics2D g2d = (Graphics2D) g;
        LinearGradientPaint paint = new LinearGradientPaint(0, 0, 0, table.getHeight(),
                new float[]{0.0f, 1.0f}, new Color[]{ThemeConstants.TOP_GRADIENT_COLOR,
                    ThemeConstants.BOTTOM_GRADIENT_COLOR});
        g2d.setPaint(paint);
        g2d.fillRect(g.getClipBounds().x, 0, g.getClipBounds().width, g.getClipBounds().height);
        super.paint(g2d, c);
    }
}

package net.managwire.downloadmanager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicTableUI;

/**
 *
 * @author Thievery
 */
public class DownloadTableUI extends BasicTableUI {

    private static final Color EVEN_ROW_COLOR = new Color(241, 245, 250);


    @Override
    public void installUI(JComponent c) {
        super.installUI(c);

        // TODO save defaults.
        table.setOpaque(false);
        table.setShowVerticalLines(true);
        table.setShowHorizontalLines(false);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));

        
    }



    @Override
    public void paint(Graphics g, JComponent c) {

            g.setColor(Color.BLACK);
            g.fillRect(g.getClipBounds().x, 0, g.getClipBounds().width, g.getClipBounds().height);


        

        super.paint(g, c);
    }

    private Color getRowColor(int row) {
        return row % 2 == 0 ? EVEN_ROW_COLOR : table.getBackground();
    }
}


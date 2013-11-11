package net.managwire.components.label;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.UIManager;
import org.jdesktop.swingx.JXComponent;

/**
 *
 * @author Thievery
 */
public class UnderlinedLabel extends JXComponent {

    private boolean useForeground;
    public static final Color shadow = new Color(255, 255, 255, 110);
    public static final Color foreground = Color.WHITE;
    private String text;
    public UnderlinedLabel() {
        super();
        setFont(UIManager.getFont("Label.font").deriveFont(Font.BOLD, 20));
    }

    public UnderlinedLabel(String text) {
        this.text=text;
        setFont(UIManager.getFont("Label.font").deriveFont(Font.BOLD, 20));
    }

    @Override
    public Color getForeground() {
        if (useForeground) {
            return foreground;
        } else {
            return shadow;
        }
    }

    @Override
    public Dimension getPreferredSize() {
        super.getPreferredSize().height += 1;
        return new Dimension(super.getPreferredSize().width, super.getPreferredSize().height + 20);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        useForeground = false;
        g2d.translate(0, 1);
        super.paintComponent(g2d);
        //useForeground = true;
        g2d.translate(0, -1);
        super.paintComponent(g2d);
        g2d.setColor(Color.WHITE);
        g2d.setStroke(new BasicStroke(4.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.drawLine(10, 10, getWidth()-10, 10);
    }
}

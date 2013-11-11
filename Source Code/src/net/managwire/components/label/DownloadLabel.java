package net.managwire.components.label;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import org.jdesktop.swingx.JXLabel;

/**
 *
 * @author Thievery
 */
public class DownloadLabel extends JXLabel {
    private boolean useForeground;
    public static final Color shadow = new Color(255, 255, 255, 110 );
    public static final Color foreground  = new Color(0xffffff);

    public DownloadLabel(String text) {
        super(text);
        setFont(new Font("Calibri", Font.PLAIN, 14));
    }

    @Override
    public Color getForeground() {
       if(useForeground){
           return foreground;
       }else{
           return shadow;
       }
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension d=super.getPreferredSize();
        d.height+=1;
        return d;
    }


    @Override
    protected void paintComponent(Graphics g) {
        useForeground=false;
        g.translate(1, 0);
        super.paintComponent(g);
        useForeground=true;
        g.translate(-1, 0);
        super.paintComponent(g);
    }
}

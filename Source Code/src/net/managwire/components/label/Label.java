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
public class Label extends JXLabel {
    private boolean useForeground;
    public static final Color shadow = new Color(0x868073);
    public static final Color foreground  = new Color(0x2e2c29);

    public Label(){
        setFont(new Font("Arial Narrow", Font.ROMAN_BASELINE, 14));
    }
    public Label(String text) {
        super(text);
        setFont(new Font("Calibri", Font.BOLD, 10));
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
        g.translate(0, 1);
        super.paintComponent(g);
        useForeground=true;
        g.translate(0, -1);
        super.paintComponent(g);
    }
}

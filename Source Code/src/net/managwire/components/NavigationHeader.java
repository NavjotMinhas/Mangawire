package net.managwire.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.LinearGradientPaint;
import javax.swing.JComponent;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Pride
 */
public class NavigationHeader extends JComponent {

    private static final Color BACKGROUND_COLOR = new Color(0x252d30);
    private static final Color BOTTOM_SHADOW_COLOR_1=new Color(0x2c2c2c);
    private static final Color BOTTOM_SHADOW_COLOR_2=new Color(0x363636);

    private LinearGradientPaint paint;

    public NavigationHeader() {
        super.setLayout(new MigLayout("Insets 5, fill, flowx, nogrid"));
    }

    @Override
    public void setLayout(LayoutManager mgr) {
        //Do nothing
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d=(Graphics2D)g.create();
        paint = new LinearGradientPaint(0, 0, 0, getHeight(), new float[]{0.0f,1.0f}, new Color[]{BACKGROUND_COLOR,BACKGROUND_COLOR.darker()});
        g2d.setPaint(paint);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.setColor(BOTTOM_SHADOW_COLOR_1);
        g2d.drawLine(0, getHeight()-2, getWidth(), getHeight()-2);
        g2d.setColor(BOTTOM_SHADOW_COLOR_2);
        g2d.drawLine(0, getHeight()-1, getWidth(), getHeight()-1);
        g2d.dispose();
    }
}

package net.managwire.ui;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.LinearGradientPaint;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicProgressBarUI;

/**
 *
 * @author Thievery
 */
public class AnimeProgressBarUI extends BasicProgressBarUI {

    public static final Color FOREGROUND_TOP_FILL = new Color(0xa1d4f6);
    public static final Color FOREGROUND_BOTTOM_FILL = new Color(0x75bae5);
    public static final Color BORDER_COLOR = new Color(0xabc1de);
    public static final Color SHADOW = new Color(255, 255, 255, 110);
    private final int OFFSET=1;
    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        c.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        c.setOpaque(false);
        c.setFont(UIManager.getFont("Table.font").deriveFont(10f));
    }

    @Override
    protected void paintDeterminate(Graphics g, JComponent c) {
        double progress = ((double) progressBar.getValue() / progressBar.getMaximum());
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        LinearGradientPaint paint = new LinearGradientPaint(0, OFFSET, 0,
                progressBar.getHeight() - 1-OFFSET, new float[]{0.0f, 1.0f}, new Color[]{
                    FOREGROUND_TOP_FILL, FOREGROUND_BOTTOM_FILL});
        g2d.setPaint(paint);
        RoundRectangle2D.Double rec2d_progress = new RoundRectangle2D.Double(0, 1, progressBar.getWidth() - 1, progressBar.getHeight() - 1-OFFSET,
                progressBar.getHeight(), progressBar.getHeight());
        g2d.setClip(0, 0, (int) (progressBar.getWidth() * progress), progressBar.getHeight());
        g2d.fill(rec2d_progress);
        g2d.dispose();

        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setColor(BORDER_COLOR);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.drawRoundRect(0, 1, progressBar.getWidth() - 1,
                progressBar.getHeight() - 1-OFFSET, progressBar.getHeight(),
                progressBar.getHeight());
        paintString(g, progressBar.getX(), progressBar.getY(), progressBar.getWidth(),
                progressBar.getHeight(), progressBar.getValue(), progressBar.getInsets());
    }

    @Override
    protected void paintString(Graphics g, int x, int y, int width, int height, int amountFull, Insets b) {
        FontMetrics metrics = g.getFontMetrics();
        int fontWidth = metrics.stringWidth(amountFull + "%");
        g.setColor(Color.BLACK);
        g.drawString(amountFull + "%", progressBar.getWidth() / 2 - fontWidth / 2, metrics.getHeight()/2+5);
        g.setColor(SHADOW);
        g.drawString(amountFull + "%", progressBar.getWidth() / 2 - fontWidth / 2, metrics.getHeight()/2+6);
    }
}

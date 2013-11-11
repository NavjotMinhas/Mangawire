package net.managwire.components.splashscreen;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import net.managwire.mainframe.AnimeFrame;
import org.pushingpixels.trident.Timeline;
import org.pushingpixels.trident.Timeline.RepeatBehavior;
import org.pushingpixels.trident.callback.TimelineCallbackAdapter;
import org.pushingpixels.trident.ease.Spline;

/**
 *
 * @author Credits to the great Romain Guy, modification by Navjot Minhas
 */
public class CurvesPanel extends JPanel {

    private RenderingHints hints;
    private int counter = 0;
    private BufferedImage splashScreen;

    public CurvesPanel() {
        hints = new RenderingHints(RenderingHints.KEY_ALPHA_INTERPOLATION,
                RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        hints.put(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        hints.put(RenderingHints.KEY_COLOR_RENDERING,
                RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        hints.put(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        hints.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        init();
    }

    private void init() {
        try {
            setOpaque(false);
            splashScreen = ImageIO.read(AnimeFrame.class.getResourceAsStream("/net/managwire/icon/splashScreen.png"));
            Timeline timeLine=new Timeline();
            timeLine.setDuration(5000);
            timeLine.addCallback(new TimelineCallbackAdapter(){

                @Override
                public void onTimelinePulse(float durationFraction, float timelinePosition) {
                   repaint();
                }

            });
            timeLine.setEase(new Spline(0.7f));
            timeLine.play();
            timeLine.playLoop(RepeatBehavior.LOOP);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        counter++;
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHints(hints);
        super.paintComponent(g2);

        float width = getWidth();
        float height = getHeight();



        g2.translate(0, height - 60);
        drawCurve(g2,
                30.0f, -15.0f, 50.0f, 15.0f,
                width / 2.0f - 40.0f, 1.0f,
                15.0f, -25.0f,
                width / 2.0f, 1.0f / 2.0f,
                0.0f, 25.0f,
                15.0f, 6.0f, false);
        g2.translate(0, -height + 60);

        drawCurve(g2,
                height - 35.0f, -5.0f, height - 50.0f, 10.0f,
                width / 2.0f - 40.0f, 1.0f,
                height - 35.0f, -25.0f,
                width / 2.0f, 1.0f / 2.0f,
                height - 20.0f, 25.0f,
                25.0f, 4.0f, true);
        g2.drawImage(splashScreen, (getWidth() - splashScreen.getWidth()) / 2, 0, null);
    }

    private void drawCurve(Graphics2D g2,
            float y1, float y1_offset,
            float y2, float y2_offset,
            float cx1, float cx1_offset,
            float cy1, float cy1_offset,
            float cx2, float cx2_offset,
            float cy2, float cy2_offset,
            float thickness,
            float speed,
            boolean invert) {

        float width = getWidth();
        float height = getHeight();

        double offset = Math.sin(counter / (speed * Math.PI));
        float start_x = 0.0f;
        float start_y = y1 + (float) (offset * y1_offset);
        float end_x = width;
        float end_y = y2 + (float) (offset * y2_offset);
        float ctrl1_x = (float) offset * cx1_offset + cx1;
        float ctrl1_y = cy1 + (float) (offset * cy1_offset);
        float ctrl2_x = (float) (offset * cx2_offset) + cx2;
        float ctrl2_y = (float) (offset * cy2_offset) + cy2;

        CubicCurve2D curve = new CubicCurve2D.Double(start_x, start_y, ctrl1_x, ctrl1_y, ctrl2_x, ctrl2_y, end_x, end_y);

        GeneralPath path = new GeneralPath(curve);
        path.lineTo(width, height);
        path.lineTo(0, height);
        path.closePath();

        Area thickCurve = new Area((Shape) path.clone());
        AffineTransform translation = AffineTransform.getTranslateInstance(0, thickness);
        path.transform(translation);
        thickCurve.subtract(new Area(path));

        Color start = new Color(100, 100, 100, 220);
        Color end = new Color(100, 100, 100, 0);

        Rectangle bounds = thickCurve.getBounds();
        GradientPaint painter = new GradientPaint(0, curve.getBounds().y,
                invert ? end : start,
                0, bounds.y + bounds.height,
                invert ? start : end);
        Paint oldPainter = g2.getPaint();
        g2.setPaint(painter);

        g2.fill(thickCurve);

        g2.setPaint(oldPainter);
    }
}

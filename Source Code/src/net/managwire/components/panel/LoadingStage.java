package net.managwire.components.panel;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import net.managwire.ui.tabbedpane.TabPanel;
import org.pushingpixels.trident.Timeline;
import org.pushingpixels.trident.Timeline.RepeatBehavior;
import org.pushingpixels.trident.Timeline.TimelineState;
import org.pushingpixels.trident.callback.TimelineCallbackAdapter;

/**
 *
 * @author Thievery
 */
public class LoadingStage extends TabPanel {

    public final static int PROGRESS_WIDTH = 300;
    public final static int PROGRESS_HEIGHT = 30;
    Timeline loopTimeline;
    Timeline fadeTimeline;
    float loadingBarAlpha;
    float loopPosition;

    public LoadingStage() {
        super();
        setOpaque(false);
        fadeTimeline = new Timeline(this);
        loopTimeline = new Timeline(this);
        fadeTimeline.addPropertyToInterpolate("loadingBarAlpha", 0.0f, 1.0f);
        fadeTimeline.addCallback(new TimelineCallbackAdapter() {

            @Override
            public void onTimelineStateChanged(TimelineState oldState, TimelineState newState, float durationFraction, float timelinePosition) {
                if (oldState == TimelineState.PLAYING_REVERSE && newState == TimelineState.DONE) {
                    loopTimeline.cancel();
                }
            }
        });
        loopTimeline.addPropertyToInterpolate("loopPosition", 0.0f, 1.0f);
        loopTimeline.addCallback(new TimelineCallbackAdapter() {

            @Override
            public void onTimelinePulse(float durationFraction, float timelinePosition) {
                repaint();
            }
        });
        fadeTimeline.setDuration(500);
        loopTimeline.setDuration(500);

    }

    public void setLoopPosition(float f) {
        loopPosition = f;
    }

    public void setLoadingBarAlpha(float f) {
        loadingBarAlpha = f;
    }

    @Override
    protected void paintChildren(Graphics g) {
        super.paintChildren(g);
        if (loadingBarAlpha>0.0 ) {
            // to put the loading bar in the center
            int x = (getWidth() - PROGRESS_WIDTH) / 2;
            int y = (getHeight() - PROGRESS_HEIGHT) / 2;


            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setClip(new RoundRectangle2D.Double(x, y, PROGRESS_WIDTH, PROGRESS_HEIGHT, 8, 8));
            g2d.setPaint(new LinearGradientPaint(x, y, x, y + PROGRESS_HEIGHT,
                    new float[]{0.0f, 0.49999f, 0.5f, 1.0f}, new Color[]{
                        new Color(0xf6f6f6), new Color(0xececed),
                        new Color(0xe1e1e1), new Color(0xfefeff)
                    }));

            //fades in the loading bar
            g2d.setComposite(AlphaComposite.SrcOver.derive(loadingBarAlpha));
            g2d.fillRect(x, y, PROGRESS_WIDTH, PROGRESS_HEIGHT);

            g2d.setPaint(new LinearGradientPaint(x, y, x, y + PROGRESS_HEIGHT,
                    new float[]{0.0f, 0.49999f, 0.5f, 1.0f}, new Color[]{
                        new Color(0xf6f6f6).darker(), new Color(0xececed).darker(),
                        new Color(0xe1e1e1).darker(), new Color(0xfefeff).darker()
                    }));
            g2d.setStroke(new BasicStroke(9.0f));
            //draws and animates the stripes
            // Width of the stripe cell basically the width of that shape
            /* ___________
             * |    /    /| 
             * |   /    / |
             * |  /    /  |
             * | /    /   |
             * |/____/____|
            */
            int stripeWidth = 25;
            for (int stripeX = (int)(x- stripeWidth+( stripeWidth*loopPosition)); stripeX < x + PROGRESS_WIDTH+stripeWidth; stripeX += stripeWidth) {
                g2d.drawLine(stripeX, y, stripeX + stripeWidth, y + PROGRESS_HEIGHT);
            }
            //clip should be placed here or the ends of all the stripes will be seen 
            g2d.setClip(0, 0, getWidth(), getHeight());
            g2d.setStroke(new BasicStroke(1.5f));
            g2d.setColor(new Color(0xb1b1b1));
            g2d.drawRoundRect(x, y, PROGRESS_WIDTH, PROGRESS_HEIGHT, 8, 8);
            g2d.dispose();
        }
    }

    public void startLoad(boolean startLoading) {
        if (startLoading) {
            fadeTimeline.play();
            loopTimeline.playLoop(RepeatBehavior.LOOP);
        }else{
            fadeTimeline.playReverse();
        }
    }
}

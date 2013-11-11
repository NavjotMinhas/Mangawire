package net.managwire.search.table;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicButtonUI;
import net.managwire.common.ThemeConstants;
import net.managwire.ui.tabbedpane.TabPanel;
import net.miginfocom.swing.MigLayout;
import org.pushingpixels.trident.Timeline;
import org.pushingpixels.trident.ease.Spline;

/**
 *
 * @author Pride
 */
public class MangawireDefaultBanner extends TabPanel {

    private BufferedImage logo;
    private BufferedImage mangawire;
    private BufferedImage communicate;
    private BufferedImage collaborate;
    private BufferedImage share;
    private float percentage;
    private int position;
    private Timeline positionTimeLine;
    private Timeline transitionTimeLine;
    private Slider slider;
    private ArrayList<String> descriptions;
    private JPanel advButtons;
    private static final int CLIP_WIDTH = 450;
    private AdvertisementButton previousButton;
    private AdvertisementButton currentButton;
    private static final Color SELECTED_BOTTOM_TAB_BACKGROUND_COLOR = new Color(0x252d30);
    private static final Color SELECTED_TOP_TAB_BACKGROUND_COLOR = new Color(0x333d40);

    public MangawireDefaultBanner(Slider slider) {
        try {
            this.slider = slider;
            logo = ImageIO.read(MangawireDefaultBanner.class.getResourceAsStream("images/logo.png"));
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void init() {
        setLayout(new MigLayout("insets 0, fill"));

        //Sets up the timeline
        positionTimeLine = new Timeline(MangawireDefaultBanner.this);
        positionTimeLine.setDuration(60000);
        positionTimeLine.addPropertyToInterpolate("position", 1, 5);

        transitionTimeLine = new Timeline(MangawireDefaultBanner.this);
        transitionTimeLine.setDuration(500);
        transitionTimeLine.setEase(new Spline(0.8f));
        transitionTimeLine.addPropertyToInterpolate("percentage", 0.0f, 1.0f);
        //----------------------

        //Constucts a custom wrapper for just the images in the advertisement window
        //the wrapper paints a black border around the sliding images
        JPanel wrapper = new JPanel(null) {

            @Override
            protected void paintChildren(Graphics g) {
                super.paintChildren(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(Color.WHITE);
                g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
                g2d.setColor(new Color(255, 255, 255, 125));
                g2d.setStroke(new BasicStroke(5.0f));
                g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
                g2d.dispose();
            }
        };
        wrapper.setOpaque(false);
        wrapper.add(slider);
        slider.setBounds(0, 0, 2250, 250);

        AdvertisementWrapper advWrapper = new AdvertisementWrapper();
        advWrapper.add(wrapper, "width 400, height 250, pos 0.9al 0.5al");

        advButtons = new JPanel(new MigLayout("width 150, insets 10 0 10 0,fill, flowy"));
        advButtons.setOpaque(false);

        descriptions = slider.getDescriptions();
        for (int i = 0; i < descriptions.size(); i++) {
            advButtons.add(new AdvertisementButton(descriptions.get(i)), "growx");
        }

        advWrapper.add(advButtons, "height 200, pos 0.1al 0.5al");
        add(advWrapper, "width 650, height 300, pos 0.5al 0.5al");
        /* addHierarchyListener(new HierarchyListener() {

        public void hierarchyChanged(HierarchyEvent e) {
        if (!isPlaying) {
        positionTimeLine.playLoop(Timeline.RepeatBehavior.LOOP);
        isPlaying = true;
        } else {
        positionTimeLine.cancel();
        isPlaying = false;
        }
        }
        });*/
        positionTimeLine.playLoop(Timeline.RepeatBehavior.LOOP);
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
        //movement of the slider
        slider.setBounds(((position - 1) * CLIP_WIDTH + (int) (percentage * CLIP_WIDTH)) * -1, 0, 2250, 250);
        repaint();
    }

    public void setPosition(int position) {
        if (this.position != position) {
            if (currentButton != null) {
                previousButton = currentButton;
                previousButton.setPaintBackground(false);
            }
            currentButton = ((AdvertisementButton) advButtons.getComponent(position - 1));
            currentButton.setPaintBackground(true);
            this.position = position;
            transitionTimeLine.play();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.WHITE);

        if (getWidth() >= 700 && getHeight() >= 500) {
            int bitmapWidth = 90;
            int bitmapHeight = 90;
            int x = 200;
            int y = getHeight() - bitmapHeight - 20;
            int width = (getWidth() - x * 2);
            g2d.fillRect(x, y, bitmapWidth, bitmapHeight);
            g2d.fillRect((getWidth() - bitmapWidth) / 2, y, bitmapWidth, bitmapHeight);
            g2d.fillRect(x + width - bitmapWidth, y, bitmapWidth, bitmapHeight);
            g2d.drawImage(logo, (getWidth() - logo.getWidth()) / 2, (getHeight() - 300) / 2 - 2 * logo.getHeight() / 3, null);
        }
        g2d.dispose();
    }

    private class AdvertisementButton extends JButton {

        private boolean paintBackground = false;
        private boolean useForeground;

        public AdvertisementButton(String text) {
            super(text);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setForeground(Color.WHITE);
            setFont(ThemeConstants.TAB_FONT.deriveFont(15.0f));
            setHorizontalAlignment(SwingConstants.LEFT);
            setUI(new ButtonUI());
        }

        public void setPaintBackground(boolean paintBackground) {
            this.paintBackground = paintBackground;
            repaint();
        }

        @Override
        public Color getForeground() {
            if (useForeground) {
                return Color.WHITE;
            } else {
                return Color.BLACK;
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            if (paintBackground) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                LinearGradientPaint paint = new LinearGradientPaint(0, 0, 0, getHeight(),
                        new float[]{0.0f, 1.0f}, new Color[]{SELECTED_TOP_TAB_BACKGROUND_COLOR.darker(), SELECTED_BOTTOM_TAB_BACKGROUND_COLOR.darker()});
                g2d.setPaint(paint);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 7, 7);
                g2d.setColor(Color.WHITE);
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 7, 7);
                g2d.dispose();
            }
            super.paintComponent(g);
        }

        class ButtonUI extends BasicButtonUI {

            @Override
            protected void paintText(Graphics g, AbstractButton b, Rectangle textRect, String text) {
                if (b.getModel().isPressed()) {
                    // Do nothing
                } else {
                    useForeground = false;
                    g.translate(1, 1);
                    super.paintText(g, b, textRect, text);
                    g.translate(-1, -1);

                }
                useForeground = true;
                super.paintText(g, b, textRect, text);
            }
        }
    }
}

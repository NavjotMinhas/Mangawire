package net.managwire.components;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import org.jdesktop.swingx.JXComponent;
import org.pushingpixels.trident.Timeline;
import org.pushingpixels.trident.TimelineScenario;
import org.pushingpixels.trident.ease.Spline;
import org.pushingpixels.trident.swing.SwingRepaintCallback;
import org.pushingpixels.trident.swing.TimelineSwingWorker;

/**
 *
 * @author Thievery
 */
public class ChapterOverviewComponent extends JXComponent {

    private static int CHAPTER_ALBUM_WIDTH = 200;
    private static int CHAPTER_ALBUM_HEIGHT = 200;
    private Image image;
    private float imageAlpha;
    private float alpha;
    private float borderAlpha;
    private String title;
    private URL imagePath;
    private Color textColor;
    private boolean imageLoaded = false;

    public ChapterOverviewComponent(String title, URL imagePath) {
        this.title = title;
        this.imagePath = imagePath;
        this.setOpaque(false);
        this.addHierarchyListener(new HierarchyListener() {

            public void hierarchyChanged(HierarchyEvent e) {
                Timeline fadeIn = new Timeline(ChapterOverviewComponent.this);
                fadeIn.setDuration(500);
                fadeIn.addPropertyToInterpolate("alpha", 0.0f, 1.0f);
                fadeIn.addCallback(new SwingRepaintCallback(ChapterOverviewComponent.this));
                fadeIn.play();
            }
        });
        // Both timelines need to be in sync
        final Timeline borderFade = new Timeline(this);
        borderFade.setDuration(700);
        borderFade.setEase(new Spline(0.8f));
        borderFade.addPropertyToInterpolate("borderAlpha", 0.0f, 1.0f);
        borderFade.addPropertyToInterpolate("textColor", Color.WHITE, new Color(64, 140, 255, 150));
        borderFade.addCallback(new SwingRepaintCallback(ChapterOverviewComponent.this));

        this.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                borderFade.play();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                borderFade.playReverse();
            }
        });
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                createScenario().play();
            }
        });
    }

    public void setImageAlpha(float f) {
        imageAlpha = f;
    }

    public void setAlpha(float f) {
        alpha = f;
    }

    public void setBorderAlpha(float f) {
        borderAlpha = f;
    }

    public void setTextColor(Color c) {
        textColor = c;
    }

    private TimelineScenario createScenario() {
        TimelineScenario scenario = new TimelineScenario.Sequence();
        TimelineSwingWorker imageLoadWorker = new TimelineSwingWorker() {

            @Override
            protected Object doInBackground() throws Exception {
                image = ImageIO.read(imagePath);
                return null;
            }
        };
        scenario.addScenarioActor(imageLoadWorker);
        TimelineSwingWorker scaleWorker = new TimelineSwingWorker() {

            @Override
            protected Object doInBackground() throws Exception {
                image = image.getScaledInstance(CHAPTER_ALBUM_WIDTH, CHAPTER_ALBUM_HEIGHT, Image.SCALE_SMOOTH);
                imageLoaded = true;
                return null;
            }
        };
        scenario.addScenarioActor(scaleWorker);
        Timeline imageFade = new Timeline(this);
        imageFade.setDuration(850);
        imageFade.addPropertyToInterpolate("imageAlpha", 0.0f, 1.0f);
        imageFade.addCallback(new SwingRepaintCallback(ChapterOverviewComponent.this));
        scenario.addScenarioActor(imageFade);
        return scenario;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(160, 180);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setComposite(AlphaComposite.SrcOver.derive(alpha));
        g2d.setPaint(new GradientPaint(0, 0, new Color(0, 0, 0, 196), 0, getHeight(), new Color(0, 0, 0, 0)));
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 32, 32);

        //To make the border abit darker
        g2d.drawRoundRect(0, 0, getWidth(), getHeight(), 32, 32);

        if (imageLoaded) {
            Graphics2D graphicsImage = (Graphics2D) g.create();
            graphicsImage.setComposite(AlphaComposite.SrcOver.derive(imageAlpha));
            graphicsImage.drawImage(image, (getWidth() - CHAPTER_ALBUM_WIDTH) / 2, 15, null);
            graphicsImage.dispose();
        }
        if (borderAlpha > 0.0f) {
            g2d.setPaint(new GradientPaint(0, 0, new Color(64, 140, 255, 196),
                    0, getHeight(), new Color(64, 140, 255, 0)));
            g2d.setComposite(AlphaComposite.SrcOver.derive(borderAlpha * alpha));
            g2d.setStroke(new BasicStroke(2.0f));
            g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 32, 32);
            //TO DO: Needs Multi-line paint code
            /*g2d.setComposite(AlphaComposite.SrcOver.derive(alpha));
            g2d.setColor(textColor);
            g2d.setFont(UIManager.getFont("Label.font").deriveFont(11.0f));
            int textWidth = g2d.getFontMetrics().charsWidth(title.toCharArray(), 0, title.length());
            int textHeight = g2d.getFontMetrics().getHeight();
            g2d.drawString(title, (getWidth() - textWidth) / 2, 15 + CHAPTER_ALBUM_HEIGHT + textHeight);
            */
        }
        /*else {
            //TO DO: Needs Multi-line paint code
            g2d.setComposite(AlphaComposite.SrcOver.derive(alpha));
            g2d.setColor(Color.WHITE);
            g2d.setFont(UIManager.getFont("Label.font").deriveFont(11.0f));
            int textWidth = g2d.getFontMetrics().charsWidth(title.toCharArray(), 0, title.length());
            int textHeight = g2d.getFontMetrics().getHeight();
            g2d.drawString(title, (getWidth() - textWidth) / 2, 15 + CHAPTER_ALBUM_HEIGHT + textHeight);
        }*/
        g2d.dispose();
    }

    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ChapterOverviewComponent("Bleach",new URL("http://www.instantz.net/newlayout/uploads/bleach_logo.jpg")));
        frame.setSize(200, 200);
        frame.setVisible(true);
    }
}

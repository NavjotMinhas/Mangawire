package net.managwire.components;

import net.managwire.components.tablecellrender.TableHeaderCellRender;
import net.managwire.common.ThemeConstants;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.net.URL;
import javax.swing.JFrame;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.geom.Morphing2D;
import org.pushingpixels.trident.Timeline;
import org.pushingpixels.trident.TimelineScenario;
import org.pushingpixels.trident.ease.Spline;
import org.pushingpixels.trident.swing.SwingRepaintCallback;

/**
 *
 * @author Thievery
 */
public class MangaOverviewComponent extends JPanel {

    private Morphing2D mangaInfoShape;
    private Morphing2D chapterInfoShape;
    private Morphing2D arrow;
    private float fraction;
    private float movingPos;
    private float arrowFraction;
    private float arrowMorphingFraction;
    private float borderAlpha;
    private float componentAlpha;
    private TimelineScenario scenario;
    private final int arrowWidth = getWidth() - mangaInfoShape_WIDTH - chapterInfoShape_WIDTH;
    private final int arrowHeight = 100;
    private final static int mangaInfoShape_WIDTH = 300;
    private final static int mangaInfoShape_HEIGHT = 350;
    private final static int chapterInfoShape_WIDTH = 300;
    private final static int chapterInfoShape_HEIGHT = 500;

    public MangaOverviewComponent() {
        setLayout(new MigLayout());
        setOpaque(false);
        scenario = new TimelineScenario.Sequence();
        createFirstScene();
        createSecondScene();
        createThirdScene();
        createFourthScene();
        createFifthScene();
        createSixthScene();
        scenario.play();
        try {
            ChapterOverviewComponent comp = new ChapterOverviewComponent("Bleach", new URL("http://www.instantz.net/newlayout/uploads/bleach_logo.jpg"));
            add(comp, "wrap,width 250, height 250,x 25");
            JXLabel title = new JXLabel("Title: Bleach");
            title.setForeground(Color.WHITE);
            title.setFont(ThemeConstants.TAB_FONT.deriveFont(18.0f));
            JXLabel lastUpdated = new JXLabel("Last Updated: Apr 22, 2010");
            lastUpdated.setForeground(Color.WHITE);
            lastUpdated.setFont(ThemeConstants.TAB_FONT.deriveFont(14.0f));
            JXLabel from = new JXLabel("From: www.mangafox.com");
            from.setForeground(Color.WHITE);
            from.setFont(ThemeConstants.TAB_FONT.deriveFont(14.0f));
            JXLabel chapter = new JXLabel("Chapters:");
            chapter.setForeground(Color.WHITE);
            chapter.setFont(ThemeConstants.TAB_FONT.deriveFont(18.0f));
            add(title, "wrap,x 25");
            add(lastUpdated, "wrap,x 25");
            add(from, "wrap,x 25");
            add(chapter, "wrap,x 520, y 110");


            String data[][] = {{"John", "Sutherland", "Student"},
                {"George", "Davies", "Student"},
                {"Melissa", "Anderson", "Associate"},
                {"Stergios", "Maglaras", "Developer"},};

            String fields[] = {"Name", "Surname", "Status"};
            JXTable table = new JXTable(data,fields);
            table.getTableHeader().setDefaultRenderer(new TableHeaderCellRender());
            
            add(new ScrollPane(table), "wrap,pos 520 140,width 260");
        } catch (IOException e) {
        }
    }

    public void setFraction(float fraction) {
        this.fraction = fraction;
    }

    public void setMovingPos(float movingPos) {
        this.movingPos = movingPos;
    }

    public void setArrowFraction(float arrowFraction) {
        this.arrowFraction = arrowFraction;
    }

    public void setArrowMorphingFraction(float arrowMorphingFraction) {
        this.arrowMorphingFraction = arrowMorphingFraction;
    }

    public void setBorderAlpha(float borderAlpha) {
        this.borderAlpha = borderAlpha;
    }

    public void setComponentAlpha(float componentAlpha) {
        this.componentAlpha = componentAlpha;
    }


    @Override
    public int getWidth() {
        return 800;
    }

    @Override
    public int getHeight() {
        return 600;
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(800, 600);
    }

    @Override
    public void setSize(Dimension d) {
        super.setSize(new Dimension(800, 600));
    }

    @Override
    public void setPreferredSize(Dimension preferredSize) {
        super.setPreferredSize(new Dimension(800, 600));
    }

    private Morphing2D mangaInfoShape() {
        Ellipse2D.Double shape1 = new Ellipse2D.Double(0, 0, 100, 100);
        RoundRectangle2D.Double shape2 = new RoundRectangle2D.Double(0, 0,
                mangaInfoShape_WIDTH, mangaInfoShape_HEIGHT, 50, 50);
        return new Morphing2D(shape1, shape2);
    }

    private Morphing2D chapterInfoShape() {
        Ellipse2D.Double shape1 = new Ellipse2D.Double(getWidth() - 100, getHeight() - 100, 100, 100);
        RoundRectangle2D.Double shape2 = new RoundRectangle2D.Double(getWidth() - chapterInfoShape_WIDTH, getHeight() - chapterInfoShape_HEIGHT,
                chapterInfoShape_WIDTH, chapterInfoShape_HEIGHT, 50, 50);
        return new Morphing2D(shape1, shape2);
    }

    private Morphing2D createArrow() {
        int y = getHeight() - chapterInfoShape_HEIGHT + 40;
        Rectangle shape1 = new Rectangle(mangaInfoShape_WIDTH,
                y, arrowWidth, 100);
        GeneralPath.Double shape2 = new GeneralPath.Double();
        shape2.moveTo(mangaInfoShape_WIDTH + (arrowWidth), y + (arrowHeight - 1) / 6);
        shape2.lineTo(mangaInfoShape_WIDTH + (arrowWidth) / 4, y + (arrowHeight - 1) / 6);
        shape2.lineTo(mangaInfoShape_WIDTH + (arrowWidth) / 4, y);
        shape2.lineTo(mangaInfoShape_WIDTH, y + (arrowHeight - 1) / 2);
        shape2.lineTo(mangaInfoShape_WIDTH + (arrowWidth) / 4, y + (arrowHeight - 1));
        shape2.lineTo(mangaInfoShape_WIDTH + (arrowWidth) / 4, y + 5 * (arrowHeight - 1) / 6);
        shape2.lineTo(mangaInfoShape_WIDTH + (arrowWidth), y + 5 * (arrowHeight - 1) / 6);
        shape2.closePath();
        return new Morphing2D(shape1, shape2);
    }

    private void createFirstScene() {
        Timeline move = new Timeline(MangaOverviewComponent.this);
        move.setDuration(1000);
        move.addPropertyToInterpolate("movingPos", 0.0f, 1.0f);
        move.setEase(new Spline(0.6f));
        move.addCallback(new SwingRepaintCallback(MangaOverviewComponent.this));
        scenario.addScenarioActor(move);
    }

    private void createSecondScene() {
        Timeline morph = new Timeline(MangaOverviewComponent.this);
        morph.setDuration(1000);
        morph.addPropertyToInterpolate("fraction", 0.0f, 1.0f);
        morph.addCallback(new SwingRepaintCallback(MangaOverviewComponent.this));
        morph.setEase(new Spline(0.8f));
        scenario.addScenarioActor(morph);
    }

    private void createThirdScene() {
        Timeline arrowAnimator = new Timeline(MangaOverviewComponent.this);
        arrowAnimator.setDuration(1000);
        arrowAnimator.addPropertyToInterpolate("arrowFraction", 0.0f, 1.0f);
        arrowAnimator.addCallback(new SwingRepaintCallback(MangaOverviewComponent.this));
        arrowAnimator.setEase(new Spline(0.8f));
        scenario.addScenarioActor(arrowAnimator);
    }

    private void createFourthScene() {
        Timeline arrowMorph = new Timeline(MangaOverviewComponent.this);
        arrowMorph.setDuration(600);
        arrowMorph.addPropertyToInterpolate("arrowMorphingFraction", 0.0f, 1.0f);
        arrowMorph.addCallback(new SwingRepaintCallback(MangaOverviewComponent.this));
        arrowMorph.setEase(new Spline(0.9f));
        scenario.addScenarioActor(arrowMorph);
    }

    private void createFifthScene() {
        Timeline fadeIn = new Timeline(MangaOverviewComponent.this);
        fadeIn.setDuration(700);
        fadeIn.addPropertyToInterpolate("borderAlpha", 0.0f, 1.0f);
        fadeIn.addCallback(new SwingRepaintCallback(MangaOverviewComponent.this));
        fadeIn.setEase(new Spline(0.2f));
        scenario.addScenarioActor(fadeIn);
    }
    private void createSixthScene() {
        Timeline fadeIn = new Timeline(MangaOverviewComponent.this);
        fadeIn.setDuration(1000);
        fadeIn.addPropertyToInterpolate("componentAlpha", 0.0f, 1.0f);
        fadeIn.addCallback(new SwingRepaintCallback(MangaOverviewComponent.this));
        fadeIn.setEase(new Spline(0.8f));
        scenario.addScenarioActor(fadeIn);
    }
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.BLACK);
        g2d.setComposite(AlphaComposite.SrcOver.derive(0.90f));
        if (movingPos > 0.0f && 1.0f > movingPos) {
            Ellipse2D.Double shape1 = new Ellipse2D.Double((getWidth() - 100) / 2 - ((getWidth() - 100) / 2) * movingPos, (getHeight() - 100) / 2 - ((getHeight() - 100) / 2) * movingPos, 100, 100);
            g2d.fill(shape1);
            Ellipse2D.Double shape2 = new Ellipse2D.Double((getWidth() - 100) / 2 + ((getWidth() - 100) / 2) * movingPos, (getHeight() - 100) / 2 + ((getHeight() - 100) / 2) * movingPos, 100, 100);
            g2d.fill(shape2);
        } else {
            mangaInfoShape = mangaInfoShape();
            mangaInfoShape.setMorphing(fraction);
            g2d.fill(mangaInfoShape);
            chapterInfoShape = chapterInfoShape();
            chapterInfoShape.setMorphing(fraction);
            g2d.fill(chapterInfoShape);
        }
        if (1.0f > arrowFraction && arrowFraction > 0.0f) {
            g2d.fillRect(mangaInfoShape_WIDTH, getHeight() - chapterInfoShape_HEIGHT + 40, (int) (arrowWidth * arrowFraction), 100);
        } else if (arrowFraction >= 1.0f) {
            g2d.scale(-1.0, 1.0);
            g2d.translate(-getWidth(), 0.0);
            arrow = createArrow();
            arrow.setMorphing(arrowMorphingFraction);
            g2d.fill(arrow);
            g2d.translate(getWidth(), 0.0);
            g2d.scale(-1.0, 1.0);
        }
        if (borderAlpha > 0.0f) {
            g2d.setComposite(AlphaComposite.SrcOver.derive(borderAlpha));
            g2d.setColor(Color.WHITE);
            g2d.setStroke(new BasicStroke(2.0f));
            g2d.draw(mangaInfoShape);
            g2d.draw(chapterInfoShape);
            g2d.scale(-1.0, 1.0);
            g2d.translate(-getWidth(), 0.0);
            g2d.setColor(Color.WHITE);
            g2d.draw(arrow);
            g2d.translate(getWidth(), 0.0);
            g2d.scale(-1.0, 1.0);
        }
        g2d.dispose();

    }

    @Override
    protected void paintChildren(Graphics g) {
        if (borderAlpha == 1.0f) {
            Graphics2D g2d=(Graphics2D)g.create();
            g2d.setComposite(AlphaComposite.SrcOver.derive(componentAlpha));
            super.paintChildren(g2d);
            g2d.dispose();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new MangaOverviewComponent());
        frame.setSize(805,650);
        frame.setVisible(true);
    }
}

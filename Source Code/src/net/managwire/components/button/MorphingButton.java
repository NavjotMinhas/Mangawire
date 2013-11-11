package net.managwire.components.button;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.triggers.MouseTrigger;
import org.jdesktop.animation.timing.triggers.MouseTriggerEvent;
import org.jdesktop.swingx.geom.Morphing2D;

public class MorphingButton extends JButton implements TimingTarget, SwingConstants {

    private Morphing2D m2d;
    private float Fraction;
    private Animator animator;
    private int Direction;

    public MorphingButton(String Text, int Direction) {
        super(Text);
        this.Direction = Direction;
        this.setContentAreaFilled(false);
        this.setFocusPainted(false);
        this.setOpaque(false);
        animator = new Animator(350, this);
        animator.setAcceleration(0.3f);
        animator.setDeceleration(0.3f);
        MouseTrigger.addTrigger(this, animator, MouseTriggerEvent.ENTER, true);
    }

    @Override
    public void setForeground(Color fg) {
        super.setForeground(Color.WHITE);
    }



    @Override
    protected void paintBorder(Graphics g) {
        //Do Nothing
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        LinearGradientPaint paint = new LinearGradientPaint(0, 0, 0,
                getHeight(), new float[]{0.0f, 0.49f, 0.5f, 1.0f}, new Color[]{
                    new Color(0x05b2e9), new Color(0x4271e7),
                    new Color(0x0841ce), new Color(0x18c7ff)});
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        m2d.setMorphing(Fraction);
        g2d.setPaint(paint);
        if (Direction == SwingConstants.EAST) {
            g2d.scale(-1.0, 1.0);
            g2d.translate(-getWidth(), 0.0);
        }
        g2d.fill(m2d);
        if (Direction == SwingConstants.EAST) {
            g2d.translate(getWidth(), 0.0);
            g2d.scale(-1.0, 1.0);
        }
        super.paintComponent(g);

    }

    private Morphing2D createMorph() {
        RoundRectangle2D.Double shape1 = new RoundRectangle2D.Double(0, 0,
                (getWidth() - 1), (getHeight() - 1), 10, 10);
        GeneralPath.Double shape2 = new GeneralPath.Double();
        shape2.moveTo((getWidth() - 1), (getHeight() - 1) / 6);
        shape2.lineTo((getWidth() - 1) / 4, (getHeight() - 1) / 6);
        shape2.lineTo((getWidth() - 1) / 4, 0);
        shape2.lineTo(0, (getHeight() - 1) / 2);
        shape2.lineTo((getWidth() - 1) / 4, (getHeight() - 1));
        shape2.lineTo((getWidth() - 1) / 4, 5 * (getHeight() - 1) / 6);
        shape2.lineTo((getWidth() - 1), 5 * (getHeight() - 1) / 6);
        shape2.closePath();
        return new Morphing2D(shape1, shape2);
    }

    @Override
    public void paint(Graphics g) {
        m2d = createMorph();
        super.paint(g);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Morph Button Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel pane = new JPanel();
        pane.add(new MorphingButton("Back", SwingConstants.WEST));
        pane.add(new MorphingButton("Forward", SwingConstants.EAST));
        frame.add(pane);
        frame.setSize(500, 500);
        frame.setVisible(true);
    }

    @Override
    public void begin() {
    }

    @Override
    public void end() {
    }

    @Override
    public void repeat() {
    }

    @Override
    public void timingEvent(float Frac) {
        Fraction = Frac;
        repaint();
    }
}

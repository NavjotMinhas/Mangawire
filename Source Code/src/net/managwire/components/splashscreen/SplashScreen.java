package net.managwire.components.splashscreen;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JWindow;

/**
 *
 * @author Pride
 */
public class SplashScreen extends JWindow {

    private CurvesPanel contentPane = new CurvesPanel();
    private static final int WINDOW_WIDTH = 500;
    private static final int WINDOW_HEIGHT = 125;

    public SplashScreen() {
        init();
    }

    private void init() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLayout(new BorderLayout());
        add(contentPane, BorderLayout.CENTER);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocation((int) (dim.getWidth() - getWidth()) / 2, (int) (dim.getHeight() - getHeight()) / 2);
    }
}

package net.managwire.mainframe;

import net.managwire.common.MangawireInitializer;
import javax.swing.SwingUtilities;
import net.managwire.components.splashscreen.SplashScreen;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 *
 * @author Thievery
 */
public class AnimeFrame extends SingleFrameApplication {

    private MainPanel mainTabsPanel = new MainPanel();
    private static SplashScreen splashScreen = new SplashScreen();

    private static void createSplashScreen() {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                com.sun.awt.AWTUtilities.setWindowOpaque(splashScreen, false);
                splashScreen.setVisible(true);
            }
        });
    }

    private static void closeSplashScreen() {
        // Use invokelater, thus allowing the frame data to load before in the EDT
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                splashScreen.dispose();
            }
        });
        MangawireInitializer.postSetup();
    }

    @Override
    protected void initialize(String[] args) {
        MangawireInitializer.initialize();
        mainTabsPanel = new MainPanel();
        AnimeMainFrame.frame = getMainFrame();
    }

    @Override
    protected void startup() {
        show(mainTabsPanel);
        closeSplashScreen();
    }

    public static void main(String[] args) {
        if (System.getProperty("sun.arch.data.model").indexOf("32") != -1) {
            try {
                AnimeFrame.createSplashScreen();
                MangawireInitializer.preSetup().join();
                Application.launch(AnimeFrame.class, args);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // WE need some code here to download 32 bit java
            System.out.println("you need a 32bit VM");
        }
    }
}

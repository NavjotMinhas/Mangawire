package net.managwire.mainframe;

import net.managwire.common.MangawireInitializer;
import net.managwire.common.ThemeConstants;
import net.managwire.ui.tabbedpane.NavigatorTabbedPaneUI;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Thievery
 */
public class MainPanel extends JPanel {

    JPanel pane = new JPanel();

    public MainPanel() {
        super(new MigLayout("fill,flowx,center,gap 0,ins 0"));
        pane.setLayout(new MigLayout("fill,flowx,center,gap 0,ins 0"));
        pane.setBackground(ThemeConstants.BACKGROUND_COLOR);
        buildTabs();
        add(pane, "Dock Center");
    }

    private void buildTabs() {
        MangawireInitializer.initialize();
        MangawireInitializer.mainTabs.addTab("Forums", MangawireInitializer.browserWindow);
        MangawireInitializer.mainTabs.addTab("Library", MangawireInitializer.libraryPanel);
        MangawireInitializer.mainTabs.addTab("Search", MangawireInitializer.searchPanel);
        MangawireInitializer.mainTabs.addTab("Home", MangawireInitializer.homePanel);
        MangawireInitializer.mainTabs.setUI(new NavigatorTabbedPaneUI());
        pane.add(MangawireInitializer.mainTabs, "grow");
    }
}

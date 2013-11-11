package net.managwire.common;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JTabbedPane;
import net.jxta.exception.PeerGroupException;
import net.jxta.share.CMS;
import net.managwire.browser.BrowserInitializer;
import net.managwire.browser.BrowserUtils;
import net.managwire.browser.BrowserWindow;
import net.managwire.downloadmanager.DownloadPanel;
import net.managwire.homepanel.HomePanel;
import net.managwire.errorlog.ErrorConsole;
import net.managwire.library.LibraryPanel;
import net.managwire.search.table.SearchPanel;
import net.mangawire.p2p.content.BootStrapping;
import net.mangawire.p2p.content.MediaContentManager;
import net.mangawire.parser.OneManga;

/**
 *
 * @author Thievery
 * The Core class that intailizes everything
 */
public class MangawireInitializer {

    //Initializes the Error Console
    public static ErrorConsole errorConsole;
    // Initializes the Download Manager
    // initializes the mozilla browser, however Browser.initializeXULRunner should be
    // invoked before reaching this intialization stage
    public static BrowserWindow browserWindow;
    public static HomePanel homePanel;
    public static SearchPanel searchPanel;
    public static DownloadPanel downloadPanel;
    public static LibraryPanel libraryPanel;
    public static MediaContentManager contentManager;
    public static JTabbedPane mainTabs;

    public static void initialize() {
        BrowserInitializer.initializeXULRunner();
        MangawireInitializer.mainTabs=new JTabbedPane();
        MangawireInitializer.homePanel = new HomePanel();
        //Searchpanel is dependent on download panel being initialized first.
        MangawireInitializer.downloadPanel = new DownloadPanel();
        MangawireInitializer.searchPanel = new SearchPanel();
        MangawireInitializer.libraryPanel = new LibraryPanel();
        MangawireInitializer.browserWindow = new BrowserWindow();
        MangawireInitializer.errorConsole = new ErrorConsole();
    }

    public static Thread preSetup() {
        Thread workerThread = new Thread() {

            @Override
            public void run() {

                BootStrapping.BootStrap_Network();
                Data.MANGAS = OneManga.getAllManagas();
                int numOfRetrys = 0;
                while (true) {

                    try {
                        BufferedReader reader = new BufferedReader(new FileReader("dependencies/index.html"));
                        StringBuilder htmlSource = new StringBuilder();
                        String tempLine = null;
                        while ((tempLine = reader.readLine()) != null) {
                            htmlSource.append(tempLine);
                        }
                        Data.HTML_HOME_SOURCE = htmlSource.toString();
                        reader = new BufferedReader(new FileReader("dependencies/temp.html"));
                        htmlSource = new StringBuilder();
                        while ((tempLine = reader.readLine()) != null) {
                            htmlSource.append(tempLine);
                        }
                        Data.HTML_POPUP_SOURCE=htmlSource.toString();
                        break;
                    } catch (IOException e) {
                        if (numOfRetrys == 5) {
                            System.exit(0);
                        } else {
                            numOfRetrys++;
                            e.printStackTrace();
                            continue;
                        }
                    }
                }
            }
        };
        workerThread.start();
        return workerThread;
    }

    public static void postSetup() {
        Thread workerThread = new Thread() {

            @Override
            public void run() {
                browserWindow.load("http://forum.onemanga.com/");
                BrowserUtils.installDOMListener(homePanel.getBrowser());
                homePanel.init();
                CMS cms = new CMS();
                while (true) {
                    try {
                        cms.init(Data.PEER_GROUP, null, null);
                        contentManager = new MediaContentManager(Data.downloadDirectory, cms);
                        contentManager.startSharing();
                        break;
                    } catch (PeerGroupException e) {
                        continue;
                    } catch (Exception e) {
                    }
                }
            }
        };
        workerThread.start();
    }
}

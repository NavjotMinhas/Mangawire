package net.managwire.homepanel;

import net.managwire.ui.tabbedpane.TabPanel;
import java.awt.BorderLayout;
import java.io.File;
import java.nio.CharBuffer;
import java.util.Iterator;
import java.util.Set;
import net.managwire.common.Data;
import net.mangawire.parser.OneManga;
import org.mozilla.browser.MozillaPanel;
import org.mozilla.browser.MozillaPanel.VisibilityMode;

/**
 *
 * @author Thievery
 */
public class HomePanel extends TabPanel {

    private MozillaPanel browser = new MozillaPanel(VisibilityMode.FORCED_HIDDEN, VisibilityMode.FORCED_HIDDEN);

    public HomePanel() {
        super(new BorderLayout());
        add(browser, BorderLayout.CENTER);
    }

    public MozillaPanel getBrowser() {
        return browser;
    }

    public void init() {
        Data.HTML_HOME_SOURCE = Data.HTML_HOME_SOURCE.replace("{InfoGrid.js}", new File("dependencies/assets/js/infogrid.js").getAbsolutePath());
        Data.HTML_HOME_SOURCE = Data.HTML_HOME_SOURCE.replace("{shadowbox.js}", new File("dependencies/assets/js/shadowbox.js").getAbsolutePath());
        Data.HTML_HOME_SOURCE = Data.HTML_HOME_SOURCE.replace("{main.css}", new File("dependencies/assets/css/main.css").getAbsolutePath());
        Data.HTML_HOME_SOURCE = Data.HTML_HOME_SOURCE.replace("{shadowbox.css}", new File("dependencies/assets/css/shadowbox.css").getAbsolutePath());
        String[] array = OneManga.sortedlist(Data.MANGAS);
        StringBuilder s = new StringBuilder();
        for (int i = 'A'; i < 'A' + 26; i++) {
            s.append("<dt>Mangas " + (char) i + "</dt>\n");
            s.append("<dd><div>\n");
            String[] range = OneManga.getRangeSelection((char) i, array);
            for (int x = 0; x < range.length; x++) {
                s.append("<a href=\"file://" + new File("dependencies/temp_popup.html").getAbsolutePath() + "\" rel=\"shadowbox;width=800;height=500\">" + range[x] + "</a><br/>\n");
            }
            s.append("</dd></div>\n");
        }
        Data.HTML_HOME_SOURCE = Data.HTML_HOME_SOURCE.replace("{series};", s.toString());
        Data.HTML_POPUP_SOURCE = Data.HTML_POPUP_SOURCE.replace("{infogrid.js}", new File("dependencies/assets/js/infogrid.js").getAbsolutePath());
        Data.HTML_POPUP_SOURCE = Data.HTML_POPUP_SOURCE.replace("{main_2.css}", new File("dependencies/assets/css/main_2.css").getAbsolutePath());
        browser.loadHTML(Data.HTML_HOME_SOURCE, "file://");
    }

    private String loadServlet() {
        StringBuilder servletCode = new StringBuilder();
        servletCode.append("<html><head><title>Home</title></head><body>\n");
        Set mangaNames = Data.MANGAS.keySet();

        String tempMangaName;

        Iterator<String> iterator = mangaNames.iterator();
        while (iterator.hasNext()) {
            tempMangaName = iterator.next();
            servletCode.append("<a href=\"" + tempMangaName + "\">" + tempMangaName + "</a>\n");
        }
        servletCode.append("</body></html>");
        return servletCode.toString();
    }
}

package net.managwire.components;

import java.net.URL;
import javax.swing.JPanel;

/**
 *
 * @author Thievery
 */
public class MangaComponent extends JPanel {
    private String Title;
    private URL urlPicture;
    private String lastUpdated;

    public void loadData(String Title, URL urlPicture, String lastUpdated) {
        this.Title = Title;
        this.urlPicture = urlPicture;
        this.lastUpdated = lastUpdated;
    }


}

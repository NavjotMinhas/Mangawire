package net.mangawire.p2p.content;

import java.io.File;

/**
 *
 * @author Pride
 */
public class SharedFile extends File {

    private String series;
    private String language;
    private String type;

    public SharedFile(File file) {
        super(file.getAbsolutePath());
        init();
    }

    private void init() {
        series = getParentFile().getName();
        language=getParentFile().getParentFile().getName();
        type=getParentFile().getParentFile().getParentFile().getName();
    }

    public String getSeries() {
        return series;
    }

    public String getLanguage() {
        return language;
    }

    public String getType() {
        return type;
    }
   
}

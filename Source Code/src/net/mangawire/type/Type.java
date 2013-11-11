package net.mangawire.type;

/**
 *
 * @author Pride
 */
public class Type {

    private String title;
    private long size = 0;
    private int mediaType;
    private int length;
    private int numOfPeers = 0;
    private String language;
    private String series;
    private String type;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setMediaType(int mediaType) {
        this.mediaType = mediaType;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public long getSize() {
        return size;
    }

    public int getMediaType() {
        return mediaType;
    }

    public int getLength() {
        return length;
    }

    public int getNumOfPeers() {
        return numOfPeers;
    }

    public String getLanguage() {
        return language;
    }

    public String getSeries() {
        return series;
    }

    public String getType() {
        return type;
    }
}

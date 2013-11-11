package net.mangawire.parser.spider;

import java.util.ArrayList;

/**
 *
 * @author Pride
 */
public class SeriesInfo {

    private String imgSrc;
    private String summary;
    private ArrayList<String> chapters=new ArrayList<String>();

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSummary() {
        return summary;
    }

    public void setChapters(ArrayList<String> chapters) {
        this.chapters = chapters;
    }

    public ArrayList<String> getChapters() {
        return chapters;
    }

}

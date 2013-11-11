package net.mangawire.parser.spider;

import java.util.ArrayList;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTML.Tag;
import javax.swing.text.html.HTMLEditorKit.ParserCallback;

/**
 *
 * @author Pride
 */
public class OneMangaChapterSpiderCallback extends ParserCallback {

    private boolean isContentTable = false;
    private boolean extractText = false;
    private boolean seriesLogo = false;
    private boolean seriesInfo = false;
    private boolean summary = false;
    private ArrayList<String> list = new ArrayList<String>();
    private SeriesInfo series=new SeriesInfo();

    @Override
    public void handleStartTag(Tag t, MutableAttributeSet a, int pos) {
        if (t.equals(Tag.TABLE)) {
            String attribute = (String) a.getAttribute(HTML.Attribute.CLASS);
            if (attribute != null && attribute.equals("ch-table")) {
                isContentTable = true;
            }
        }
        if (isContentTable && t.equals(Tag.A)) {
            extractText = true;
        }
        if (t.equals(Tag.DIV)) {
            String attribute = (String) a.getAttribute(HTML.Attribute.ID);
            if (attribute != null && attribute.equals("id_logo")) {
                seriesLogo = true;
            }
        }
        if (t.equals(Tag.DIV)) {
            String attribute = (String) a.getAttribute(HTML.Attribute.CLASS);
            if (attribute != null && attribute.equals("series-info-right")) {
                seriesInfo = true;
            }
        }
        if (t.equals(Tag.P) && seriesInfo) {
            summary = true;
        }
    }

    @Override
    public void handleText(char[] data, int pos) {
        if (extractText) {
            list.add(new String(data));
        }
        if(summary){
            series.setSummary(new String(data));
        }
    }

    @Override
    public void handleSimpleTag(Tag t, MutableAttributeSet a, int pos) {
        if (t.equals(Tag.IMG) && seriesLogo) {
           series.setImgSrc((String) a.getAttribute(HTML.Attribute.SRC));
        }
    }

    @Override
    public void handleEndTag(Tag t, int pos) {
        if (extractText && t.equals(Tag.A)) {
            extractText = false;
        }
        if (isContentTable && t.equals(Tag.TABLE)) {
            isContentTable = false;
            series.setChapters(list);
        }
        if (t.equals(Tag.DIV) && seriesLogo) {
            seriesLogo = false;
        }
        if (t.equals(Tag.DIV) && seriesInfo) {
            seriesInfo = false;
        }
        if (t.equals(Tag.P) && seriesInfo) {
            summary = false;
        }
    }

    public SeriesInfo getSeriesInfo() {
        return series;
    }
}

package net.mangawire.parser.spider;

import java.util.HashMap;
import java.util.Map;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTML.Tag;
import javax.swing.text.html.HTMLEditorKit.ParserCallback;

/**
 *
 * @author Pride
 */
public class OneMangaSpiderCallback extends ParserCallback {

    private boolean extractText = false;
    private boolean isContentTable = false;
    private boolean isTableData = false;
    private Map map = new HashMap();
    private String tempURL;

    @Override
    public void handleStartTag(Tag t, MutableAttributeSet a, int pos) {
        if (t.equals(Tag.TABLE)) {
            String attribute = (String) a.getAttribute(HTML.Attribute.CLASS);
            if (attribute != null && attribute.equals("ch-table")) {
                isContentTable = true;
            }
        }
        if (isTableData && t.equals(Tag.A)) {
            String attribute = (String) a.getAttribute(HTML.Attribute.HREF);
            if (attribute != null && attribute.matches("(?!.*(directory))/[0-9A-Za-z]*/")) {
                tempURL=attribute;
                extractText = true;
            }
        }
        if (isContentTable && t.equals(Tag.TD)) {
            isTableData = true;
        }
    }

    @Override
    public void handleText(char[] data, int pos) {
        if (extractText) {
            map.put(new String(data), tempURL);
        }
    }

    @Override
    public void handleEndTag(Tag t, int pos) {
        if (extractText && t.equals(Tag.A)) {
            extractText = false;
        }
        if (isContentTable && t.equals(Tag.DIV)) {
            isContentTable = false;
        }
        if (isTableData && t.equals(Tag.TD)) {
            isTableData = false;
        }
    }

    /**
     * @return the map with all the results
     */
    public Map getMap() {
        return map;
    }
}

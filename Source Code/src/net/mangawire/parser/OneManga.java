package net.mangawire.parser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import javax.swing.text.html.parser.ParserDelegator;
import net.mangawire.parser.spider.OneMangaChapterSpiderCallback;
import net.mangawire.parser.spider.OneMangaSpiderCallback;
import net.mangawire.parser.spider.SeriesInfo;

/**
 * The Class OneManga.
 */
public class OneManga {

    /**
     * Instantiates a new one manga.
     */
    public OneManga() {
    }

    /**
     * Gets the all managas.
     *
     * @return the all managas
     */
    public static Map<String, String> getAllManagas() {
        try {
            URL url = new URL("http://www.onemanga.com/directory/");
            InputStreamReader reader = new InputStreamReader(url.openStream());
            ParserDelegator parser = new ParserDelegator();
            OneMangaSpiderCallback spider = new OneMangaSpiderCallback();
            parser.parse(reader, spider, true);
            return spider.getMap();
        } catch (MalformedURLException e) {
            return null;
        } catch (IOException e) {
            return null;
        }

    }

    public static SeriesInfo getAllChapters(String htmlLink) {
        try {
            URL url = new URL("http://www.onemanga.com"+htmlLink);
            InputStreamReader reader = new InputStreamReader(url.openStream());
            ParserDelegator parser = new ParserDelegator();
            OneMangaChapterSpiderCallback spider = new OneMangaChapterSpiderCallback();
            parser.parse(reader, spider, true);
            return spider.getSeriesInfo();
        } catch (MalformedURLException e) {
            return null;
        } catch (IOException e) {
            return null;
        }

    }

    public static String[] sortedlist(Map map) {
        String[] series = (String[]) map.keySet().toArray(new String[0]);
        Arrays.sort(series, new MangaComparator());
        return series;
    }

    public static String[] getRangeSelection(char start, char end, String[] series) {
        if (start > end) {
            throw new IllegalArgumentException();
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        for (int i = 0; i < series.length; i++) {
            char c = Character.toUpperCase(series[i].charAt(0));
            if (start <= c && c <= end) {
                arrayList.add(series[i]);
            } else if (end < c) {
                break;
            }
        }
        return arrayList.toArray(new String[0]);
    }

    public static String[] getRangeSelection(char start, String[] series) {
        return getRangeSelection(start, start, series);
    }

    public static void main(String[] args) {
        OneManga.getAllManagas();
    }
}

package net.mangawire.parser;

import java.util.Comparator;

/**
 *
 * @author Pride
 */
public class MangaComparator implements Comparator<String> {

    public int compare(String word1, String word2) {
        return word1.toLowerCase().compareTo(word2.toLowerCase());
    }


}

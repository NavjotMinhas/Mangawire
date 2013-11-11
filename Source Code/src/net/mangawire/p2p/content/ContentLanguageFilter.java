/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mangawire.p2p.content;

import java.io.File;
import java.io.FileFilter;

/**
 *
 * @author Pride
 */
/*
 * Keeps the integrity of the shared folder structure by specifying the currently
 * supported languages
 */
public class ContentLanguageFilter implements FileFilter {

    // Currently Supported Langauges
    public static final String[] LANGUAGES = {"english", "japanese", "spanish"};

    public boolean accept(File pathname) {
        for (int i = 0; i < LANGUAGES.length; i++) {
            if (pathname.getName().toLowerCase().equals(LANGUAGES[i])) {
                return true;
            }
        }
        return false;
    }
}

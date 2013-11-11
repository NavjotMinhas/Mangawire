package net.mangawire.p2p.content;

import java.io.File;
import java.io.FileFilter;

/**
 *
 * @author Pride
 */
/*This clas is responsible for filtering out any content we do not want
 * to be shared on the network. This class more or less should minimize
 * and eliminate viruses from being on the network
 */
public class ContentFilter implements FileFilter {

    //A list of the accpeted multimedia formats allowed
    public static final String[] ACCEPTED_FILE_FORMATS = {".avi", ".mkv", ".mp3", ".wav", ".wmv"};

    public boolean accept(File pathname) {
        for (int i = 0; i < ACCEPTED_FILE_FORMATS.length; i++) {
            if (pathname.getName().endsWith(ACCEPTED_FILE_FORMATS[i])) {
                return true;
            }
        }
        return false;
    }
}

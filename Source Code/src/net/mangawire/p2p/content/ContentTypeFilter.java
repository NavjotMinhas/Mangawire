package net.mangawire.p2p.content;

import java.io.File;
import java.io.FileFilter;

/**
 *
 * @author Pride
 */
/*
 * Keeps the integrity of the shared folder structure by specifying the only types
 * allowed to be in the folder.
 */
public class ContentTypeFilter implements FileFilter {

    /*
     * The only two acceptable folder names that are allowed to be in the parent
     * directory folder.
     */
    public static final String []FILE_NAMES={"anime","manga"};

    public boolean accept(File pathname) {
        for(int i=0;i<FILE_NAMES.length;i++){
            if(pathname.getName().toLowerCase().equals(FILE_NAMES[i])){
                return true;
            }
        }
        return false;
    }

}

package net.mangawire.p2p.content;

import java.io.File;

/**
 *
 * @author Pride
 */
public class SharedFolder {

    /**
     * Creates the shared folder for the first time.
     * @param hostPath the hot path of the parent directory
     * @return true if the files were successfully created.
     */
    public static boolean createSharedFile(String hostPath) {
        try {
            File parentFile = new File(hostPath + "/MangaWire");
            if(!parentFile.exists()){
                parentFile.mkdir();
            }
            for (int i = 0; i < ContentTypeFilter.FILE_NAMES.length; i++) {
                for (int y = 0; y < ContentLanguageFilter.LANGUAGES.length; y++) {
                    File file = new File(parentFile.getAbsolutePath() + "/" + ContentTypeFilter.FILE_NAMES[i] + "/" + ContentLanguageFilter.LANGUAGES[y]);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

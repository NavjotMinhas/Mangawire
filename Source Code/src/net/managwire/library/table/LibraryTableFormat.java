package net.managwire.library.table;

import ca.odell.glazedlists.gui.TableFormat;
import java.io.File;
import net.managwire.common.Data;

/**
 *
 * @author Pride
 */
public class LibraryTableFormat implements TableFormat<File> {

    private String fileName;

    public int getColumnCount() {
        return 4;
    }

    public String getColumnName(int i) {
        if (i == 0) {
            return "File Name";
        } else if (i == 1) {
            return "Series";
        } else if (i == 2) {
            return "Size";
        } else if (i == 3) {
            return "Type";
        }
        throw new IllegalStateException();
    }
    /**
     * The integrity of the shared folder is checked by library loader;
     * hence allowing the application to determine the type of the content, Anime
     * or Manga
     * @param e an entry in the table
     * @param i column index
     * @return
     */
    public Object getColumnValue(File e, int i) {
        fileName = e.getName();
        if (i == 0) {
            return fileName;
        } else if (i==1) {
            return e.getParent().substring(e.getParent().lastIndexOf("\\")+1);
        } else if (i == 2) {
            return e.length() / 1024 + " KB";
        } else if (i == 3) {
            if(e.getParent().toLowerCase().contains(Data.downloadDirectory.getName().toLowerCase()+"\\anime")){
                return "Anime";
            }else{
                return "Manga";
            }
        }
        throw new IllegalStateException();
    }
}

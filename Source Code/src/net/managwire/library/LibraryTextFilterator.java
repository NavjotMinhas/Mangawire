package net.managwire.library;

import ca.odell.glazedlists.TextFilterator;
import java.io.File;
import java.util.List;

/**
 *
 * @author Pride
 */
public class LibraryTextFilterator implements TextFilterator<File> {

    private String fileName;

    public void getFilterStrings(List<String> list, File e) {
        fileName = e.getName();
        list.add(fileName);
        list.add(e.getParent().substring(e.getParent().lastIndexOf("\\") + 1));
        list.add(e.length() / 1024 + " KB");

    }
}

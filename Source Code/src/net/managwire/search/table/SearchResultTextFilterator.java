package net.managwire.search.table;

import ca.odell.glazedlists.TextFilterator;
import java.util.List;
import net.mangawire.type.SharedFileType;

/**
 *
 * @author Pride
 */
public class SearchResultTextFilterator implements TextFilterator<SharedFileType> {

    public void getFilterStrings(List<String> list, SharedFileType e) {
        list.add(e.getTitle());
        list.add(Integer.toString(e.getNumOfPeers()));
    }



}

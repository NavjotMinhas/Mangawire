package net.managwire.search.table;

import ca.odell.glazedlists.TextFilterator;
import java.util.List;
import net.mangawire.type.Type;


/**
 *
 * @author Pride
 */
public class SearchTextFilterator implements TextFilterator<Type> {

    public void getFilterStrings(List<String> list, Type e) {
        list.add(Integer.toString(e.getMediaType()));
        list.add(e.getTitle());
        list.add(Long.toString(e.getSize()));
        list.add(Integer.toString(e.getLength()));
        list.add(Integer.toString(e.getNumOfPeers()));
    }



}

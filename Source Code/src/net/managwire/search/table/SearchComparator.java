package net.managwire.search.table;

import java.util.Comparator;
import net.mangawire.type.Type;

/**
 *
 * @author Pride
 */
public class SearchComparator implements Comparator<Type>{

    public int compare(Type type1, Type type2) {
        return (type1.getNumOfPeers()-type2.getNumOfPeers())*-1;
    }



}

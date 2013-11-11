package net.managwire.search.table;

import ca.odell.glazedlists.gui.TableFormat;
import net.mangawire.type.Type;

/**
 *
 * @author Pride
 */
public class SearchTableFormat implements TableFormat<Type> {

    public int getColumnCount() {
        return 7;
    }

    public String getColumnName(int i) {
        if (i == 0) {
            return "Title";
        } else if (i == 1) {
            return "Size";
        } else if (i == 2) {
            return "Length";
        } else if (i == 3) {
            return "# Peers";
        } else if (i == 4) {
            return "Type";
        } else if (i == 5) {
            return "Series";
        }else if(i==6){
            return "Language";
        }
        throw new IllegalStateException();
    }

    public Object getColumnValue(Type e, int i) {
        if (i == 0) {
            return e.getTitle();
        } else if (i == 1) {
            return e.getSize() / 1024 + " kb";
        } else if (i == 2) {
            return e.getLength();
        } else if (i == 3) {
            return e.getNumOfPeers();
        } else if (i==4) {
            return e.getType();
        }else if(i==5){
            return e.getSeries();
        }else if(i==6){
            return e.getLanguage();
        }
        throw new IllegalStateException();
    }
}

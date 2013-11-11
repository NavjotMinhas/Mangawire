package net.managwire.library;

import ca.odell.glazedlists.matchers.Matcher;
import java.io.File;

/**
 *
 * @author Pride
 */
public class TypeMatcher implements Matcher<File> {

    private String fileName;
    private String criteria;

    public TypeMatcher(String criteria) {
        this.criteria = criteria;
    }

    public boolean matches(File e) {
        if(criteria==null){
            return true;
        }
       fileName=e.getParent().toLowerCase();
       return fileName.contains(criteria);
    }

}

package net.managwire.library;

import ca.odell.glazedlists.matchers.AbstractMatcherEditor;
import java.io.File;

/**
 *
 * @author Pride
 */
public class TypeMatchEditor extends AbstractMatcherEditor<File>{
    public void setMatcher(String criteria){
        fireChanged(new TypeMatcher(criteria));
    }
}

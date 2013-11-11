package net.managwire.search.table;

import java.util.ArrayList;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Pride
 */
public class Slider extends JPanel {

    private ArrayList<String> descriptions=new ArrayList<String>();

    public Slider() {
        super(new MigLayout("insets 0, width 2250, height 250, fill"));
        setOpaque(false);
    }

    public ArrayList<String> getDescriptions() {
        return descriptions;
    }

    public void addButton(ImageButton button, String description){
        descriptions.add(description);
        add(button, "width 450, growy, pad 0");
    }
}


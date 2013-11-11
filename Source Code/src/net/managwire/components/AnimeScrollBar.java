package net.managwire.components;

import net.managwire.ui.ScrollBarUI;
import javax.swing.JScrollBar;

/**
 *
 * @author Thievery
 */
public class AnimeScrollBar extends JScrollBar {

    public AnimeScrollBar() {
        setUI(new ScrollBarUI());
    }


}

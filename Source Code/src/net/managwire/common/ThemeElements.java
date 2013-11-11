package net.managwire.common;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

/**
 *
 * @author Thievery
 */
public class ThemeElements {

    private static final Color OUTSIDE_BORDER_COLOR = new Color(0x1f1f1f);
    private static final Color INSIDE_BORDER_COLOR = new Color(0x4e4e4e);
    private static final int BORDER_WIDTH = 2;

    public static Border createTabBorder() {
        return BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(BORDER_WIDTH, BORDER_WIDTH, BORDER_WIDTH, BORDER_WIDTH, OUTSIDE_BORDER_COLOR),
                BorderFactory.createMatteBorder(BORDER_WIDTH, BORDER_WIDTH, BORDER_WIDTH, BORDER_WIDTH, INSIDE_BORDER_COLOR));
    }

}

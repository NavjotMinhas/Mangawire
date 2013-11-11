package net.managwire.components;

import net.managwire.components.tablecellrender.TableHeaderCellRender;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JScrollPane;

/**
 *
 * @author Thievery
 */
public class ScrollPane extends JScrollPane{

    public ScrollPane(JComponent c) {
        super(c);
        setBorder(BorderFactory.createEmptyBorder());
        setCorner(JScrollPane.UPPER_RIGHT_CORNER, new TableHeaderCellRender());
        setVerticalScrollBar(new AnimeScrollBar());
    }
}

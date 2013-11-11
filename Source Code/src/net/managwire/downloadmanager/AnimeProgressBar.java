package net.managwire.downloadmanager;

/**
 *
 * @author Thievery
 */
import net.managwire.ui.AnimeProgressBarUI;
import java.awt.Component;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
public class AnimeProgressBar extends JProgressBar implements TableCellRenderer {
    public AnimeProgressBar(int min, int max){
        super(min,max);
        setUI(new AnimeProgressBarUI());
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        setValue((Integer)value);
        return this;
    }
}
